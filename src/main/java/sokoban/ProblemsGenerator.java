package sokoban;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProblemsGenerator {

    public static void getAllProblems() {
        String testDir = "config/"; 
        String outputDir = "src/main/java/sokoban/problems/";

        for (int i = 1; i <= 10; i++) {
            File file = new File(testDir + "test" + i + ".json");
            if (!file.exists()) {
                System.out.println("Fichier test" + i + ".json introuvable, ignoré.");
                continue;
            }

            try {
                String jsonContent = new String(Files.readAllBytes(file.toPath()));
                String testIn = extractTestIn(jsonContent);

                if (testIn != null) {
                    String problemFileName = outputDir + "problem-test" + i + ".pddl";
                    parseAndGenerateProblem(testIn, problemFileName);
                    System.out.println("Problème généré : " + problemFileName);
                }
            } catch (IOException e) {
                System.err.println("Erreur de lecture pour test" + i + ".json");
                e.printStackTrace();
            }
        }
    }

    private static String extractTestIn(String json) {
        Pattern pattern = Pattern.compile("\"testIn\"\\s*:\\s*\"(.*?)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1).replace("\\n", "\n");
        }
        return null;
    }

    private static void parseAndGenerateProblem(String testIn, String problemFile) {
        String[] lines = testIn.split("\n");
        List<String> gridLines = new ArrayList<>();
        int maxWidth = 0;

        for (String line : lines) {
            gridLines.add(line);
            maxWidth = Math.max(maxWidth, line.length());
        }

        int height = gridLines.size();
        int width = maxWidth;

        char[][] map = new char[height][width];
        for (int y = 0; y < height; y++) {
            String line = gridLines.get(y);
            for (int x = 0; x < width; x++) {
                map[y][x] = (x < line.length()) ? line.charAt(x) : ' ';
            }
        }

        int playerX = -1, playerY = -1;
        List<int[]> boxes = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = map[y][x];
                if (c == '@' || c == '+') {
                    playerX = x;
                    playerY = y;
                } else if (c == '$' || c == '*') {
                    boxes.add(new int[]{x, y});
                }
            }
        }

        // Réutilise votre méthode existante dans Agent.java pour créer le fichier PDDL
        Agent.createPDDLProblemFile(
            problemFile, width, height, boxes.size(),
            map, playerX, playerY, boxes.toArray(new int[0][0])
        );
    }
}