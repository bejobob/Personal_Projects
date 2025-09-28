import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class Piglatin_translator {
    public static void main(String[] args) {
        ArrayList<String> word = new ArrayList<String>();
        char[] vowells = { 'a', 'e', 'i', 'o', 'u' };
        String wordtest = "this is a loong sentence";
        boolean singlecontains = false;
        boolean doublecontains = false;
        char letterOne = 'a';
        char letterTwo = 'a';

        String[] listWord = wordtest.split(" ");

        for (String w: listWord) {
            for (int j = 0; j < w.length(); j++) {
                //System.out.println(w);
                for (char c : vowells) {

                    if (w.charAt(j) == c) {
                        singlecontains = true;
                        letterOne = w.charAt(j);
                        for (char sc : vowells) {

                            if (j< w.length() - 1 && w.charAt(j+1) == sc) {
                                doublecontains = true;
                                letterTwo = w.charAt(j+1);
                                break;

                            }
                        }
                    }
                }
            }

            if (doublecontains == true) {
                System.out.println("there are two vowels: " + letterOne + " " + letterTwo);

            } else if (singlecontains == true) {
                System.out.println("there is a vowel: " + letterOne);
            } else {
                System.out.println("there is not a vowel: " + letterOne);
            }

            singlecontains = false;
            doublecontains = false;
        } 
    } 
}
