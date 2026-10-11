package sokoban;

import java.util.Scanner;

public class Agent {
 private static final String PROBLEM_FILE = "domains/sokoban-domain.pddl";

 public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    convertCurrentLevelToPDDLProblem(input);
    // ...
 }

public static void convertCurrentLevelToPDDLProblem(Scanner input) {
   
   System.out.println("=== Lecture du niveau Sokoban ===");

   // First line: dimensions and number of boxes
   System.out.print("Taille des boites à entrer : largeur, hauteur, nombre de boites : ");
   int width = input.nextInt();
   int height = input.nextInt();
   int boxCount = input.nextInt();
   input.nextLine(); // Consume the remaining newline
   System.err.printf("[INPUT] width=%d height=%d boxes=%d%n", width, height, boxCount);

   // Complete static map: '#' = wall, '*' = goal, '.' = floor
   System.out.println("Entrez les " + height + " lignes de la carte (# = mur, * = destination, . = sol) :");
   char[][] map = new char[height][];
      for (int y = 0; y < height; y++) {
      map[y] = input.nextLine().toCharArray();
      System.err.printf("[INPUT] row %02d |%s|%n", y, new String(map[y]));
   }

   // Initial player position
   System.out.print("Coordonnées du joueur (x y) : ");
   int playerX = input.nextInt();
   int playerY = input.nextInt();
   System.err.printf("[INPUT] player x=%d y=%d%n", playerX, playerY);

   // Initial box positions
   int[][] boxes = new int[boxCount][2];
   for (int i = 0; i < boxCount; i++) {
      System.out.print("Coordonnées de la boîte " + i + " (x y) : ");
      boxes[i][0] = input.nextInt(); // x
      boxes[i][1] = input.nextInt(); // y
      System.err.printf("[INPUT] box %d x=%d y=%d%n", i, boxes[i][0], boxes[i][1]);

   System.out.println("\n=== Résumé du niveau ===");
   System.out.println("Dimensions : " + width + " x " + height);
   System.out.println("Nombre de boîtes : " + boxCount);
   System.out.println("Joueur : (" + playerX + ", " + playerY + ")");

   for (int j = 0; i < boxCount; i++) {
        System.out.println(
            "Boîte " + j + " : (" + boxes[j][0] + ", " + boxes[j][1] + ")"
        );
    }
   }

   createPDDLProblemFile(PROBLEM_FILE, width, height, boxCount, map, playerX, playerY, boxes);
   return;
 }

 public static void createPDDLProblemFile(
    String problemFile, int width, int height, int boxCount, char[][] map, int playerX, int playerY, int [][] boxes
 ) {
     try (java.io.PrintWriter writer =
             new java.io.PrintWriter(problemFile)) {

        writer.println("(define (problem Sokoban_generated)");
        writer.println("  (:domain Sokoban)");

        // Objets
        writer.println("  (:objects");

        for (int i = 0; i < boxCount; i++) {
            writer.println("    box" + i + " - box");
        }

        writer.println("    guard - guard");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] != '#') {
                    writer.println("    pos_" + x + "_" + y + " - position");
                }
            }
        }

        writer.println("    U D L R - direction");
        writer.println("  )");

        // État initial
        writer.println("  (:init");

        writer.println(
            "    (at-position guard pos_" + playerX + "_" + playerY + ")"
        );

        for (int i = 0; i < boxCount; i++) {
            writer.println(
                "    (at-box box" + i + " pos_"
                + boxes[i][0] + "_" + boxes[i][1] + ")"
            );
        }

        // Destinations
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '*') {
                    writer.println(
                        "    (destination pos_" + x + "_" + y + ")"
                    );
                }
            }
        }

        writer.println("  )");
        writer.println(")");

        System.out.println("Fichier PDDL généré : " + problemFile);

    } catch (java.io.IOException e) {
        System.err.println("Erreur lors de la génération du fichier PDDL.");
        e.printStackTrace();
    }

 }
}