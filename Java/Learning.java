import java.util.Scanner; 
import java.util.InputMismatchException;


public class Learning {
    public static void main(String[] args) {
        String name = "John", lastName = "Doe";
        String fullName = name + lastName;
        byte age = 42; // byte is any number from -128 to 127
        int number = 100; // int is any  number
        short thousand = 5000; // short is any number from -32768 to 32767
        long veryBig = 9223372036854775807L; // long is from any number from (negative that -1) to that. Must finish with "L"
        float hover = 1.01f; // float is any decimal number. Must finish with "f". Up to 7 decimal points
        double hoverPrec = 1.000001d; // double is any decimal number. Must finish with "d". Up to 15 decimal points.
        float exponent = 20e3f; // the "e" means to the power of 10 times the number after. Will print 20000.0
        boolean isJavaFun = true; // boolean
        char letter = 'a'; // char is one character. Must have single quotes. Can use ASCII numbers.

/* The following script is called CASTING. Turning one type of variable into another.
 * If you are going from small to big, the conversion is done automatically. byte -> short -> char -> int -> long -> float -> double
 * Otherwise, you will need to do it by hand. double -> float -> long -> int -> char -> short -> byte
 */
        
        int myInt = 9;
        double myDouble = myInt; // automatic conversion

        double myOtherDouble = 9.78d;
        int myOtherInt = (int) myOtherDouble; // needed to specify the "int". Manual conversion. It is now equal to 9.
        
        System.out.println(name + lastName + fullName); // this is a comment
        System.out.println(fullName.length());
        System.out.println(fullName.toUpperCase());
        System.out.println(fullName.indexOf("Doe")); // will return the location of the first character is the specified search
        System.out.println(age);
        System.out.println(exponent);
        System.out.println("We are the so-called \"Vikings\" from the north");
        Math.max(5, 10); 

        if (name + lastName == fullName) {
            System.out.print(true);
        } else {
            System.out.println(false);
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter a letter: ");
            String s = scan.next();
            System.out.print("Enter a number: ");
            
            try {
                int integer = scan.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("Incorrect input");
            } finally {
                scan.close();
            }

        }
        
        }
    }