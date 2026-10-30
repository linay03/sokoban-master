package sokoban;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Agent {

    private static final String PROBLEM_FILE =
        "src/main/java/sokoban/domains/sokoban-problem.pddl";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ProblemsGenerator.getAllProblems();
    }

}