package Astar;
import java.util.Scanner;
import java.util.InputMismatchException;

public class principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = -1;
        int y = -1;
        String[][] grid ={{"■","■","■","■","■","■","■","■","■","□"},
                          {"■","□","□","□","□","□","□","□","□","■"},
                          {"■","□","■","■","■","■","■","■","□","■"},
                          {"■","□","■","□","□","□","□","■","□","■"},
                          {"■","□","■","□","■","■","□","■","□","■"},
                          {"■","□","■","□","■","□","□","□","□","■"},
                          {"■","■","■","□","■","□","■","■","■","■"},
                          {"■","□","□","□","□","□","□","□","□","■"},
                          {"■","□","■","■","■","■","■","■","□","■"},
                          {"■","■","■","■","□","■","■","■","□","■"}};
        
        while (x < 0 || x > grid[0].length - 1 || y < 0 || y > grid.length - 1) {
            x = -1;
            y = -1;
            try {
                System.out.print("Enter the x coordinate (0-9): ");
                x = scanner.nextInt();
                System.out.print("Enter the y coordinate (0-9): ");
                y = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.next(); // clear the invalid input
            }
        } scanner.close();


    };
}

