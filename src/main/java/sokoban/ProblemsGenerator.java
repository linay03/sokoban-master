package sokoban;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Generates PDDL problems for the Sokoban domain.
 */
public class ProblemsGenerator {

    private static final String TEST_DIRECTORY = "config/";
    private static final String OUTPUT_DIRECTORY = "src/main/java/sokoban/problems/";

    public static void main(String[] args) throws IOException {
        getAllProblems();
    }

    /** Generates PDDL problems for the first ten configured tests. */
    public static void getAllProblems() throws IOException {
        Files.createDirectories(Paths.get(OUTPUT_DIRECTORY));

        for (int i = 1; i <= 10; i++) {
            File testFile = new File(TEST_DIRECTORY + "test" + i + ".json");
            if (!testFile.exists()) {
                System.err.println("Test file not found, skipping: " + testFile);
                continue;
            }

            String json = new String(Files.readAllBytes(testFile.toPath()), StandardCharsets.UTF_8);
            String testIn = extractTestIn(json);
            if (testIn == null) {
                throw new IOException("Missing testIn field in " + testFile);
            }

            String problemFile = OUTPUT_DIRECTORY + "problem-test" + i + ".pddl";
            String problem = createProblem(testIn, "problem-test" + i);
            Files.write(Paths.get(problemFile), problem.getBytes(StandardCharsets.UTF_8));
            System.out.println("Generated: " + problemFile);
        }
    }
    /** Extracts the testIn field from a JSON string.
     * @param json The JSON string.
     * @return The unescaped testIn string, or null if not found.
    */
    private static String extractTestIn(String json) {
        Pattern pattern = Pattern.compile("\"testIn\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? unescapeJsonString(matcher.group(1)) : null;
    }

    /** Unescapes a JSON string.
     * @param value The JSON string to unescape.
     * @return The unescaped string.
     */
    private static String unescapeJsonString(String value) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char current = value.charAt(i);
            if (current != '\\') {
                result.append(current);
                continue;
            }
            if (++i >= value.length()) {
                throw new IllegalArgumentException("Invalid escape at end of testIn string");
            }

            char escaped = value.charAt(i);
            switch (escaped) {
                case '"':
                case '\\':
                case '/':
                    result.append(escaped);
                    break;
                case 'b':
                    result.append('\b');
                    break;
                case 'f':
                    result.append('\f');
                    break;
                case 'n':
                    result.append('\n');
                    break;
                case 'r':
                    result.append('\r');
                    break;
                case 't':
                    result.append('\t');
                    break;
                case 'u':
                    if (i + 4 >= value.length()) {
                        throw new IllegalArgumentException("Incomplete unicode escape in testIn string");
                    }
                    result.append((char) Integer.parseInt(value.substring(i + 1, i + 5), 16));
                    i += 4;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid escape in testIn string: \\" + escaped);
            }
        }
        return result.toString();
    }

    /** Creates a PDDL problem from a Sokoban grid.
     * @param testIn The Sokoban grid as a string.
     * @param problemName The name of the problem.
     * @return The PDDL problem as a string.
     */
    private static String createProblem(String testIn, String problemName) {
        String[] lines = testIn.split("\\r?\\n", -1);
        int height = lines.length;
        if (height > 0 && lines[height - 1].isEmpty()) {
            height--;
        }
        int width = 0;
        for (int y = 0; y < height; y++) {
            width = Math.max(width, lines[y].length());
        }
        if (width == 0 || height == 0) {
            throw new IllegalArgumentException("The testIn grid is empty");
        }

        char[][] map = new char[height][width];
        List<int[]> boxes = new ArrayList<>();
        List<int[]> targets = new ArrayList<>();
        int playerX = -1;
        int playerY = -1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char cell = x < lines[y].length() ? lines[y].charAt(x) : ' ';
                if (cell != '#' && cell != ' ' && cell != '@' && cell != '+'
                        && cell != '$' && cell != '*' && cell != '.') {
                    throw new IllegalArgumentException(
                        "Unexpected map character '" + cell + "' at (" + x + ", " + y + ")"
                    );
                }
                map[y][x] = cell;
                if (cell == '@' || cell == '+') {
                    if (playerX >= 0) {
                        throw new IllegalArgumentException("The grid contains more than one player");
                    }
                    playerX = x;
                    playerY = y;
                }
                if (cell == '$' || cell == '*') {
                    boxes.add(new int[]{x, y});
                }
                if (cell == '.' || cell == '*' || cell == '+') {
                    targets.add(new int[]{x, y});
                }
            }
        }

        if (playerX < 0) {
            throw new IllegalArgumentException("The grid does not contain a player");
        }
        if (boxes.size() != targets.size()) {
            throw new IllegalArgumentException(
                "The grid has " + boxes.size() + " boxes but " + targets.size() + " targets"
            );
        }

        StringBuilder pddl = new StringBuilder();
        pddl.append("(define (problem ").append(problemName).append(")\n");
        pddl.append("  (:domain Sokoban)\n");
        pddl.append("  (:objects\n");
        for (int i = 0; i < boxes.size(); i++) {
            pddl.append("    box").append(i).append(" - box\n");
        }
        pddl.append("    U D L R - direction\n");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] != '#') {
                    pddl.append("    ").append(position(x, y)).append(" - position\n");
                }
            }
        }
        pddl.append("  )\n");
        pddl.append("  (:init\n");
        pddl.append("    (guard-at ").append(position(playerX, playerY)).append(")\n");
        for (int i = 0; i < boxes.size(); i++) {
            int[] box = boxes.get(i);
            pddl.append("    (box-at ").append(position(box[0], box[1])).append(")\n");
        }

        int[][] offsets = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        String[] directions = {"U", "D", "L", "R"};
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] == '#') {
                    continue;
                }
                if (!isOccupied(x, y, playerX, playerY, boxes)) {
                    pddl.append("    (clear ").append(position(x, y)).append(")\n");
                }
                for (int i = 0; i < offsets.length; i++) {
                    int nextX = x + offsets[i][0];
                    int nextY = y + offsets[i][1];
                    if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height
                            && map[nextY][nextX] != '#') {
                        pddl.append("    (adjacent ").append(position(x, y)).append(' ')
                            .append(position(nextX, nextY)).append(' ').append(directions[i])
                            .append(")\n");
                    }
                }
            }
        }
        pddl.append("  )\n");
        pddl.append("  (:goal\n");
        pddl.append("    (and\n");
        for (int[] target : targets) {
            pddl.append("      (box-at ").append(position(target[0], target[1])).append(")\n");
        }
        pddl.append("    )\n");
        pddl.append("  )\n");
        pddl.append(")\n");
        return pddl.toString();
    }

    /** Checks if a position is occupied by the player or any box.
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @param playerX The x-coordinate of the player.
     * @param playerY The y-coordinate of the player.
     * @param boxes A list of box positions, each represented as an int array [x, y].
     * @return True if the position is occupied, false otherwise.
    */
    private static boolean isOccupied(int x, int y, int playerX, int playerY, List<int[]> boxes) {
        if (x == playerX && y == playerY) {
            return true;
        }
        for (int[] box : boxes) {
            if (box[0] == x && box[1] == y) {
                return true;
            }
        }
        return false;
    }

    private static String position(int x, int y) {
        return "pos_" + x + "_" + y;
    }
}