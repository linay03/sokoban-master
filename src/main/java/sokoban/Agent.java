package sokoban;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Agent {

    private static final String PROBLEM_FILE =
        "src/main/java/sokoban/domains/sokoban-problem.pddl";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        convertCurrentLevelToPDDLProblem(input);
    }

    public static void convertCurrentLevelToPDDLProblem(Scanner input) {
        int width = input.nextInt();
        int height = input.nextInt();
        int boxCount = input.nextInt();
        input.nextLine();

        char[][] map = new char[height][width];

        for (int y = 0; y < height; y++) {
            String line = input.nextLine();

            for (int x = 0; x < width; x++) {
                map[y][x] = line.charAt(x);
            }
        }

        int playerX = input.nextInt();
        int playerY = input.nextInt();

        int[][] boxes = new int[boxCount][2];

        for (int i = 0; i < boxCount; i++) {
            boxes[i][0] = input.nextInt();
            boxes[i][1] = input.nextInt();
        }

        createPDDLProblemFile(
            PROBLEM_FILE, width, height, boxCount,
            map, playerX, playerY, boxes
        );
    }

    /**
     * Cette fonction permet d'écrire un fichier problem .pddl en prenant le contenu d'un test
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
            writer.println("  (:objects");

            // Boîtes
            for (int i = 0; i < boxCount; i++) {
                writer.println("    box" + i + " - box");
            }

            // Joueur
            writer.println("    guard - guard");

            // Positions
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (map[y][x] != '#') {
                        writer.println(
                            "    pos_" + x + "_" + y + " - position"
                        );
                    }
                }
            }

            // Directions
            writer.println("    U D L R - direction");
            writer.println("  )");
            writer.println("  (:init");

            // Position du joueur
            writer.println(
                "    (at-position guard pos_" + playerX + "_" + playerY + ")"
            );

            // Positions des boîtes
            for (int i = 0; i < boxCount; i++) {
                writer.println(
                    "    (at-box box" + i + " pos_"
                    + boxes[i][0] + "_" + boxes[i][1] + ")"
                );
            }

            // Destinations
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (map[y][x] == '*') {
                        writer.println(
                            "    (destination pos_" + x + "_" + y + ")"
                        );
                    }
                }
            }

            // Cases libres : aucune boîte ne doit les occuper.
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (map[y][x] == '#') {
                        continue;
                    }

                    boolean isBoxPresent = false;

                    for (int i = 0; i < boxCount; i++) {
                        if (boxes[i][0] == x && boxes[i][1] == y) {
                            isBoxPresent = true;
                            break;
                        }
                    }

                    if (!isBoxPresent) {
                        writer.println(
                            "    (clear pos_" + x + "_" + y + ")"
                        );
                    }
                }
            }

            // Relations entre les cases voisines
            int[][] directions = {
                {0, -1},  // U : haut
                {0, 1},   // D : bas
                {-1, 0},  // L : gauche
                {1, 0}    // R : droite
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

                        String from = "pos_" + x + "_" + y;
                        String to = "pos_" + nextX + "_" + nextY;

                        writer.println(
                            "    (adjacent " + from + " " + to + ")"
                        );

                        writer.println(
                            "    (next-move " + from + " " + to
                            + " " + directionNames[i] + ")"
                        );
                    }
                }
            }

            writer.println("  )");

            // Goal
            writer.println("  (:goal");
            writer.println("    (and");

            int boxIndex = 0;

            for (int y = 0; y < height && boxIndex < boxCount; y++) {
                for (int x = 0; x < width && boxIndex < boxCount; x++) {
                    if (map[y][x] == '*' || map[y][x] =='.') {
                        writer.println(
                            "      (at-box box" + boxIndex
                            + " pos_" + x + "_" + y + ")"
                        );
                        boxIndex++;
                    }
                }
            }

            writer.println("    )");
            writer.println("  )");
            writer.println(")");
                            
            System.out.println("Problème PDDL généré : " + problemFile);

        } catch (IOException e) {
            System.err.println("Erreur lors de la génération du problème PDDL.");
            e.printStackTrace();
        }
    }
}