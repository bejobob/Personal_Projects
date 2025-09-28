import java.util.Scanner;
import java.util.InputMismatchException;

public class Calculator {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String opperation;
        Float secondNum;
        String repeat = "y";
        Float firstNum;
        float result = 0;

        while (repeat.equals("y")) {
            opperation = "";
            firstNum = null;
            secondNum = null;

            while (!(firstNum instanceof Float)) {
                try {
                    System.out.print("Enter the first number: ");
                    firstNum = scan.nextFloat();
                } catch (InputMismatchException e) {
                    System.out.println("Please input a number!");
                    scan.next();
                }
            }
            while (!opperation.equals("/") && !opperation.equals("*") && !opperation.equals("-") && !opperation.equals("+")) {
                System.out.print("Enter the wanted operation (/, *, -, +): ");
                scan.next();
                opperation = scan.nextLine();
            }

            while (!(secondNum instanceof Float)) {
                try {
                    System.out.print("Enter the second number: ");
                    secondNum = scan.nextFloat();

                } catch (InputMismatchException e) {
                    System.out.println("Please input a number!");
                    scan.next();
                }
            }

            switch (opperation) {
                case "+":
                    result = firstNum + secondNum;
                    break;
                case "-":
                    result = firstNum - secondNum;
                    break;
                case "*":
                    result = firstNum * secondNum;
                    break;
                case "/":
                    if (secondNum != 0) {
                        result = firstNum / secondNum;
                    } else {
                        System.out.println("Cannot divide by zero");
                    }
                    break;
            }

            System.out.println(result);

            System.out.print("Do you want to restart? (y/n): ");
            repeat = scan.next();
        }
        scan.close();
    }
}