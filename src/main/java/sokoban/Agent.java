package sokoban;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.util.Scanner;

import fr.uga.pddl4j.heuristics.state.StateHeuristic;
import fr.uga.pddl4j.parser.DefaultParsedProblem;
import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.parser.Message;
import fr.uga.pddl4j.parser.Parser;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.planners.LogLevel;
import fr.uga.pddl4j.planners.statespace.HSP;
import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.operator.Action;

public class Agent {

    private static final String DOMAIN_FILE = "src/main/java/sokoban/domains/sokoban-domain.pddl";
    private static final String PROBLEM_FILE = "src/main/java/sokoban/domains/sokoban-problem.pddl";

    private static final int PLANNER_TIMEOUT_SECONDS = 8;
    private static final double HEURISTIC_WEIGHT = 1.2;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int boxCount = convertCurrentLevelToPDDLProblem(input);
        String moves = solveWithPDDL4J();
        if (moves == null) {
            System.err.println("[ERROR] No plan found");
            System.out.println("X");
            System.out.flush();
            return;
        }

        System.err.println(
                "[PLAN] " + moves.length() + " moves: " + moves
        );

        for (int i = 0; i < moves.length(); i++) {
            System.out.println(moves.charAt(i));
            System.out.flush();
            if (i + 1 < moves.length()) {
                for (int k = 0; k <= boxCount; k++) {
                    input.nextInt(); // x
                    input.nextInt(); // y
                }
            }
        }
    }

    /**
     * Lit le niveau courant depuis l'entrée du jeu,
     * puis génère le fichier problem PDDL correspondant.
     *
     * @return le nombre de boîtes du niveau
     */
    public static int convertCurrentLevelToPDDLProblem(Scanner input) {

        int width = input.nextInt();
        int height = input.nextInt();
        int boxCount = input.nextInt();

        input.nextLine();
        char[][] map = new char[height][width];
        for (int y = 0; y < height; y++) {

            String line = input.nextLine();

            for (int x = 0; x < width; x++) {
                map[y][x] = x < line.length() ? line.charAt(x) : '#';
            }
        }

        System.err.println("===== CARTE REÇUE PAR AGENT =====");

        for (int y = 0; y < height; y++) {
            System.err.println("[" + new String(map[y]) + "]");
        }

        System.err.println("=================================");

        // Lire la position du joueur.
        int playerX = input.nextInt();
        int playerY = input.nextInt();

        // Lire les positions des boîtes.
        int[][] boxes = new int[boxCount][2];

        for (int i = 0; i < boxCount; i++) {
            boxes[i][0] = input.nextInt();
            boxes[i][1] = input.nextInt();
        }

        // Générer le problème PDDL.
        createPDDLProblemFile(
                PROBLEM_FILE,
                width,
                height,
                boxCount,
                map,
                playerX,
                playerY,
                boxes
        );

        return boxCount;
    }

    /**
     * Génère un fichier problem PDDL à partir du niveau courant
     */

    public static void createPDDLProblemFile(
            String problemFile,
            int width,
            int height,
            int boxCount,
            char[][] map,
            int playerX,
            int playerY,
            int[][] boxes
    ) {

        try (PrintWriter writer = new PrintWriter(problemFile)) {

            writer.println("(define (problem sokoban_problem)");
            writer.println("  (:domain Sokoban)");

            writer.println("  (:objects");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    if (map[y][x] != '#') {
                        writer.println(
                                "    pos_" + x + "_" + y + " - position"
                        );
                    }
                }
            }

            writer.println("    U D L R - direction");
            writer.println("  )");

            writer.println("  (:init");

            // Player position
            writer.println(
                    "    (guard-at pos_" + playerX + "_" + playerY + ")"
            );

            // Box positions
            for (int i = 0; i < boxCount; i++) {
                writer.println(
                        "    (box-at pos_" + boxes[i][0] + "_" + boxes[i][1] + ")"
                );
            }

            // Destinations
            int destinationCount = 0;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    if (map[y][x] == '*') {

                        writer.println(
                                "    (destination pos_" + x + "_" + y + ")"
                        );

                        destinationCount++;
                    }
                }
            }

            // Free cells
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    if (map[y][x] == '#') {
                        continue;
                    }

                    boolean occupied = false;

                    // Vérifier la position du joueur.
                    if (playerX == x && playerY == y) {
                        occupied = true;
                    }

                    for (int i = 0; i < boxCount; i++) {
                        if (boxes[i][0] == x && boxes[i][1] == y) {
                            occupied = true;
                            break;
                        }
                    }

                    if (!occupied) {
                        writer.println(
                                "    (clear pos_" + x + "_" + y + ")"
                        );
                    }
                }
            }

            int[][] directions = {
                    {0, -1},  // U
                    {0, 1},   // D
                    {-1, 0},  // L
                    {1, 0}    // R
            };
            String[] directionNames = {"U", "D", "L", "R"};

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    if (map[y][x] == '#') {
                        continue;
                    }

                    for (int i = 0; i < directions.length; i++) {
                        int nextX = x + directions[i][0];
                        int nextY = y + directions[i][1];
                        if (nextX < 0 || nextX >= width
                                || nextY < 0 || nextY >= height) {
                            continue;
                        }
                        if (map[nextY][nextX] == '#') {
                            continue;
                        }

                        writer.println(
                                "    (adjacent pos_" + x + "_" + y
                                        + " pos_" + nextX + "_" + nextY
                                        + " " + directionNames[i] + ")"
                        );
                    }
                }
            }

            writer.println("  )");

            // Goal
            writer.println("  (:goal");
            writer.println("    (and");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    if (map[y][x] == '*') {

                        writer.println(
                                "      (box-at pos_" + x + "_" + y + ")"
                        );
                    }
                }
            }

            writer.println("    )");
            writer.println("  )");
            writer.println(")");

        } catch (IOException e) {
            System.err.println(
                    "Erreur lors de la génération du problème PDDL."
            );
            e.printStackTrace();
        }
    }

    private static char extractDirection(String actionString) {
        String[] tokens = actionString
                .replace("(", "")
                .replace(")", "")
                .trim()
                .split("\\s+");

        if (tokens[0].equalsIgnoreCase("move-player")) {
            return Character.toUpperCase(tokens[3].charAt(0));
        }

        if (tokens[0].equalsIgnoreCase("move-box")) {
            return Character.toUpperCase(tokens[4].charAt(0));
        }

        throw new IllegalArgumentException(
                "Action PDDL inconnue : " + actionString
        );
    }

    /**
     * Résout le problème avec PDDL4J et convertit le plan en U, D, L, R.
     */
    private static String solveWithPDDL4J() {

        PrintStream realOut = System.out;
        System.setOut(System.err);

        Thread.currentThread().setContextClassLoader(
                Agent.class.getClassLoader()
        );

        try {
            HSP planner = new HSP();

            planner.setLogLevel(LogLevel.OFF);
            planner.setTimeout(PLANNER_TIMEOUT_SECONDS);
            planner.setHeuristic(StateHeuristic.Name.FAST_FORWARD);
            planner.setHeuristicWeight(HEURISTIC_WEIGHT);

            Parser parser = new Parser();

            DefaultParsedProblem parsedProblem =
                    parser.parse(DOMAIN_FILE, PROBLEM_FILE);

            ErrorManager errors = parser.getErrorManager();

            if (!errors.getMessages(Message.Type.LEXICAL_ERROR).isEmpty()
                    || !errors.getMessages(Message.Type.PARSER_ERROR).isEmpty()) {
                for (Message message : errors.getMessages()) {
                    System.err.println(message);
                }
                return null;
            }

            System.err.println("[DEBUG] Parsing terminé.");
            Problem problem = planner.instantiate(parsedProblem);
            System.err.println("[DEBUG] Instanciation réussie.");

            Plan plan = planner.solve(problem);

            if (plan == null) {
                System.err.println("[ERROR] Aucun plan trouvé.");
                return null;
            }

            System.err.println("[DEBUG] Plan trouvé !");
            System.err.println("[DEBUG] Nombre d'actions : " + plan.size());

            StringBuilder moves = new StringBuilder();

            for (Action action : plan.actions()) {
                String actionString = problem.toShortString(action);
                char dir = extractDirection(actionString);
                moves.append(dir);
                System.err.println("[DEBUG] Action PDDL : " + actionString);
                System.err.println("[DEBUG] Direction extraite : " + dir);
            }
            return moves.toString();

        } catch (Throwable e) {
            e.printStackTrace();
            return null;

        } finally {
            System.setOut(realOut);
        }
    }
}