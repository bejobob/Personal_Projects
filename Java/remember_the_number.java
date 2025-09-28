import java.util.Scanner; // for taking in user input
import java.util.InputMismatchException; // for making sure that the try/catch loops work

public class remember_the_number {
    static void clear() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int score = 0;
        int diff = 0;
        String numberstString;
        String user = "";
        Scanner scan = new Scanner(System.in);

        while (!(diff == 1 || diff == 2 || diff == 3)) {
            try {
                System.out.print("Please choose difficulty (1, 2, 3): ");
                diff = scan.nextInt();

                if (!(diff == 1 || diff == 2 || diff == 3)) {
                    System.out.println("Please choose a valid option!");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please input either '1', '2', or '3'");
                scan.next();
            }

            while (score < 50) {
                if (diff == 1) {
                    numberstString = String.valueOf((int) (Math.random() * 1000));
                } else if (diff == 2) {
                    numberstString = String.valueOf((int) (Math.random() * 10000));
                } else {
                    numberstString = String.valueOf((int) (Math.random() * 100000));
                }
                clear();
                System.out.println("score: " + score + "\n\n" + numberstString);

                Thread.sleep(1000 - (score * 20));
                clear();
                System.out.print("score: " + score + "\n\nEnter the number you saw on the screen: ");
                user = scan.next();
                if (user.equals(numberstString)) {
                    score++;
                    clear();
                    System.out.println("score: " + score + "\n\nGood job! Press enter to continue: ");
                    scan.nextLine();
                    scan.nextLine();
                } else {
                    score -= 1;
                    clear();
                    System.out.println("score: " + score + "\n\nOops! The answer was" + numberstString
                            + "Press enter to continue: ");
                    scan.nextLine();

                    scan.nextLine();

                }

            }
        }
        scan.close();
    }
}
