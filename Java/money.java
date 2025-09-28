public class money {
    public static void main(String[] args) {
        float denominations[] = { 2.00f, 1.00f, 0.25f, 0.10f, 0.05f }; // the list of all the possible amounts of money
        double amount = Math.round(Math.random() * 1000.0) / 100.0; // generate a random number from 0 to 10$
        System.out.println("The amount is " + amount); // print the original amount

        for (float d : denominations) { // for every denomination possible
            double needed = Math.floor(amount / d); // we find the highest number of possible units can fit in the amount left
            amount %= d; // we ajust the amount left with modulo
            System.out.println(needed); // we print how much of the denomination is needed
        }
        System.out.println(Math.floor(amount)); // we print the leftover amount at the end
    }
}