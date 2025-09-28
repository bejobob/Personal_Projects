import java.util.Scanner;

public class isprime{
    static Scanner scanner = new Scanner(System.in);

    public static boolean primeFinder(){
        System.out.println("Enter an integer:");
        int n = scanner.nextInt();
        
        if (n <= 1){
            return false;
        } else if (n <= 3){
            return true;
        } else if (n%2 == 0 || n%3 == 0){
            return false;
        }

        int i = 5;
        while (i*i <= n){
            if ((n%i == 0) || (n%(i+2)==0)){
                return false;
            }
            i = i+6;
        } return true;
    } 
    public static void main(String[] args) {
    System.out.println(primeFinder());
    scanner.close();
    }
}


