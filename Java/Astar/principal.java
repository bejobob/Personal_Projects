package Astar;
import java.util.Scanner;
import java.util.InputMismatchException;

public class principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int start_x = -1;
        int stuart_y = -1;
        int target_x = -1;
        int target_y = -1;
        String[][] grid ={{"■","■","■","■","■","■","■","■","■","□"},
                          {"■","□","□","■","□","□","□","□","□","■"},
                          {"■","□","■","■","■","■","■","■","□","■"},
                          {"■","□","■","□","□","□","□","■","□","■"},
                          {"■","□","■","□","■","■","■","■","□","■"},
                          {"■","□","■","□","■","□","□","□","□","■"},
                          {"■","■","■","□","■","■","■","■","■","■"},
                          {"■","□","□","□","□","□","□","□","□","■"},
                          {"■","□","□","■","■","■","□","■","□","■"},
                          {"■","■","■","■","□","■","■","■","■","■"}};
        int[][] open_coords = new int[1][grid.length * grid[0].length];

        while (start_x < 0 || start_x > grid[0].length - 1 || stuart_y < 0 || stuart_y > grid.length - 1) {
            start_x = -1;
            stuart_y = -1;
            try {
                System.out.print("Enter the starting x coordinate: ");
                start_x = scanner.nextInt();
                System.out.print("Enter the starting y coordinate: ");
                stuart_y = scanner.nextInt();
                System.out.print("Enter the target x coordinate: ");
                target_x = scanner.nextInt();
                System.out.print("Enter the target y coordinate: ");
                target_y = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.next(); // clear the invalid input
            }
        } scanner.close();
        int current_x = start_x;
        int current_y = stuart_y;

        while (current_x != target_x || current_y != target_y) {

        }
    };
}

