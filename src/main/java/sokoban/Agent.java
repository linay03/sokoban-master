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

    private static final String DOMAIN_FILE =
            "src/main/java/sokoban/domains/sokoban-domain.pddl";

    private static final String PROBLEM_FILE =
            "src/main/java/sokoban/domains/sokoban-problem.pddl";

    private static final int PLANNER_TIMEOUT_SECONDS = 8;
    private static final double HEURISTIC_WEIGHT = 1.2;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Lire le niveau courant et générer son problème PDDL.
        int boxCount = convertCurrentLevelToPDDLProblem(input);

        // Résoudre le problème PDDL.
        String moves = solveWithPDDL4J();

        // Aucun plan trouvé.
        if (moves == null) {
            System.err.println("[ERROR] No plan found");
            System.out.println("X");
            System.out.flush();
            return;
        }

        // Afficher le plan uniquement dans les logs.
        System.err.println(
                "[PLAN] " + moves.length() + " moves: " + moves
        );

        // Envoyer les actions au jeu une par une.
        for (int i = 0; i < moves.length(); i++) {

            System.out.println(moves.charAt(i));
            System.out.flush();

            // Après chaque action, lire les nouvelles coordonnées
            // du joueur et des boîtes, sauf après la dernière action.
            if (i + 1 < moves.length()) {

                // Le joueur + les boîtes.
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

        // Consommer la fin de la ligne contenant les dimensions.
        input.nextLine();

        char[][] map = new char[height][width];

        // Lire la carte.
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
     * Génère un fichier problem PDDL à partir du niveau courant.
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

            writer.println("(define (problem Sokoban_generated)");
            writer.println("  (:domain Sokoban)");

            // Objets : positions et directions.
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

            // Initialisation.
            writer.println("  (:init");

            // Position du joueur.
            writer.println(
                    "    (guard-at pos_" + playerX + "_" + playerY + ")"
            );

            // Positions des boîtes.
            for (int i = 0; i < boxCount; i++) {
                writer.println(
                        "    (box-at pos_" + boxes[i][0] + "_" + boxes[i][1] + ")"
                );
            }

            // Destinations.
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

            // Cases libres.
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

                    // Vérifier les positions des boîtes.
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

            // Relations entre les positions voisines.
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

            // Objectif : toutes les destinations doivent être occupées.
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

            System.err.println(
                    "Problème PDDL généré : " + problemFile
            );

            System.err.println(
                    "Boîtes : " + boxCount
                            + " | Destinations : " + destinationCount
            );

            if (boxCount != destinationCount) {
                System.err.println(
                        "[WARNING] Le nombre de boîtes et de destinations diffère."
                );
            }

        } catch (IOException e) {
            System.err.println(
                    "Erreur lors de la génération du problème PDDL."
            );
            e.printStackTrace();
        }
    }


    /**
     * Résout le problème avec PDDL4J et convertit le plan en U, D, L, R.
     */
    private static String solveWithPDDL4J() {

        PrintStream realOut = System.out;

        // Rediriger les logs du planner vers stderr.
        System.setOut(System.err);

        Thread.currentThread().setContextClassLoader(
                Agent.class.getClassLoader()
        );

        try {

            // Paramètres du planner HSP.
            HSP planner = new HSP();

            planner.setLogLevel(LogLevel.OFF);
            planner.setTimeout(PLANNER_TIMEOUT_SECONDS);
            planner.setHeuristic(StateHeuristic.Name.FAST_FORWARD);
            planner.setHeuristicWeight(HEURISTIC_WEIGHT);

            // Parser le domaine et le problème.
            Parser parser = new Parser();

            DefaultParsedProblem parsedProblem =
                    parser.parse(DOMAIN_FILE, PROBLEM_FILE);

            // Vérifier les erreurs de parsing.
            ErrorManager errors = parser.getErrorManager();

            if (!errors.getMessages(Message.Type.LEXICAL_ERROR).isEmpty()
                    || !errors.getMessages(Message.Type.PARSER_ERROR).isEmpty()) {

                for (Message message : errors.getMessages()) {
                    System.err.println(message);
                }

                return null;
            }

            // Instancier le problème.
            System.err.println("[DEBUG] Parsing terminé.");

            Problem problem = planner.instantiate(parsedProblem);

            if (problem == null) {
                System.err.println("[ERROR] Instanciation impossible.");
                return null;
            }

            System.err.println("[DEBUG] Instanciation réussie.");

            Plan plan = planner.solve(problem);

            if (plan == null) {
                System.err.println("[ERROR] Aucun plan trouvé.");
                return null;
            }

            System.err.println("[DEBUG] Plan trouvé !");
            System.err.println("[DEBUG] Nombre d'actions : " + plan.size());

            // Convertir les actions en U, D, L, R.
            StringBuilder moves = new StringBuilder();

            for (Action action : plan.actions()) {

                String actionString = problem.toShortString(action);

                String[] tokens = actionString
                        .replace("(", "")
                        .replace(")", "")
                        .trim()
                        .split("\\s+");

                char dir = 0;

                // Chercher la direction parmi les arguments de l'action.
                for (int i = 1; i < tokens.length; i++) {

                    String token = tokens[i];

                    if (token.length() == 1
                            && "udlr".indexOf(
                            Character.toLowerCase(token.charAt(0))
                    ) >= 0) {

                        dir = Character.toUpperCase(token.charAt(0));
                        break;
                    }
                }

                if (dir == 0) {

                    System.err.println(
                            "[ERROR] Direction introuvable dans : "
                                    + actionString
                    );

                    return null;
                }

                moves.append(dir);
            }

            return moves.toString();

        } catch (Throwable e) {

            e.printStackTrace();
            return null;

        } finally {

            // Restaurer stdout pour que l'agent puisse communiquer avec le jeu.
            System.setOut(realOut);
        }
    }
}