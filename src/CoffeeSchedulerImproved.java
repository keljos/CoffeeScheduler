import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class CoffeeSchedulerImproved {
    private static final String[] COFFEE_DRINKERS = {"Jim", "Bob", "Batman", "Robin", "Hulk", "Electra", "Flash"};
    private static final int COFFEE_DRINKERS_CNT = COFFEE_DRINKERS.length;

    public static void main(String[] s) {
        
        Scanner scanner = new Scanner(System.in);

        int planDuration = promptForPlanDuration(scanner);
        double[] coffeePrices = promptForCoffeePrices(scanner);
        double totalCostPerVisit = getTotalCostPerVisit(coffeePrices);
        int priciestDrinkerIndex = getPriciestChoiceIndex(coffeePrices);
 
        double[] initialCredit = new double[COFFEE_DRINKERS_CNT];
 
        getNextPayer(coffeePrices, initialCredit, totalCostPerVisit, priciestDrinkerIndex, planDuration);

        scanner.close();
    }


    /**
     * Prompts the user for the plan duration (number of days).
     * Ensures the value is between 7 and 3000, or uses default if user doesn't specify.
     * @param scanner Scanner for user input (to enable unit testing)
     * @return The plan duration in days
     */
    public static int promptForPlanDuration(Scanner scanner) {
        int planDuration = 0;
        while (true) {
            System.out.print("How many days should the buying schedule cover? (Must be between 7 and 3000. Default is 68.): ");
            String input = scanner.nextLine();
            if (input == null || input.trim().isEmpty()) {
                System.out.println("Using default value of 68 days.");
                return 68; // Default to 68 days if no input
            }
            try {
                planDuration = Integer.parseInt(input);
                if (planDuration > 7 && planDuration <= 3000) {
                    break;
                } else {
                    System.out.println("Please enter a number between 7 and 3000.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer between 7 and 3000.");
            }
        }
        return planDuration;
    }


    /**
     * Prompts the user to enter a set of seven numbers between 1 and 20 to be used as coffee prices.
     * If the user provides invalid input (wrong count, out of bounds, or not numbers) or does not wish to
     * to provide a price list, the default price list of [3, 4, 5, 5, 5, 6, 6] is used.
     * The returned array is sorted in ascending order to match the order of coffee drinkers.
     * @param scanner Scanner for user input (to enable unit testing)
     * @return A sorted double array of seven coffee prices
     */
    public static double[] promptForCoffeePrices(Scanner scanner) {
        double[] defaultPrices = {3, 4, 5, 5, 5, 6, 6};
        System.out.print("Enter 7 coffee prices (numbers between 1 and 20, separated by spaces). Press Enter for default: ");
        String input = scanner.nextLine();
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Using default coffee prices: [3, 4, 5, 5, 5, 6, 6].");
            return defaultPrices;
        }
        String[] tokens = input.trim().split("\\s+");
        if (tokens.length != 7) {
            System.out.println("Invalid number of prices. Using default coffee prices: [3, 4, 5, 5, 5, 6, 6].");
            return defaultPrices;
        }
        double[] prices = new double[7];
        for (int i = 0; i < 7; i++) {
            try {
                double value = Double.parseDouble(tokens[i]);
                if (value < 1 || value > 20) {
                    System.out.println("Price out of bounds: " + value + ". Using default coffee prices: [3, 4, 5, 5, 5, 6, 6].");
                    return defaultPrices;
                }
                prices[i] = value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + tokens[i] + ". Using default coffee prices: [3, 4, 5, 5, 5, 6, 6].");
                return defaultPrices;
            }
        }
        return prices;
    }

    /**
     * Calculates the total bill for one round of coffee purchases.
     * @param coffeePrices Array of coffee prices for each drinker
     * @return The total bill per visit
     */
    public static double getTotalCostPerVisit(double[] coffeePrices) {
        double total = 0;
        for (double i : coffeePrices) {
            total += i;
        }
        return total;
    }

    /**
     * Finds the index of the priciest coffee choice.
     * @param coffeePrices Array of coffee prices for each drinker
     * @return The index of the priciest coffee choice
     */
    public static int getPriciestChoiceIndex(double[] coffeePrices) {
        double max = coffeePrices[0];
        int maxIndex = 0;
        for (int i = 1; i < coffeePrices.length; i++) {
            if (coffeePrices[i] > max) {
                max = coffeePrices[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * Calculates and prints the schedule of who pays for each coffee trip.
     * Prints the payer for each trip and a summary of total and average costs per drinker.
     * @param coffeeCost Array of coffee prices for each drinker
     * @param currentCredits Array of current credits for each drinker
     * @param totalCostPerVisit The total cost for one round of coffee purchases
     * @param initialPayer The index of the initial payer (the one with most expensive coffee)
     * @param numVisits The number of coffee trips (days)
     */
    public static void getNextPayer(double[] coffeeCost, double[] currentCredits, double totalCostPerVisit, int initialPayer, int numVisits) {
        double prevMin = 21; // Highest possible coffee price + 1
        int prevMinIndex = initialPayer;
        double[] totalsPerPayers = new double[7];
        DecimalFormat df = new DecimalFormat("#.00");

        for (int i = 0; i < numVisits; i++) {
            int nextPayer = prevMinIndex;
            prevMin = 21; // Reset to highest possible coffee price + 1
            System.out.println("Coffee Trip #" + (i + 1));
            for (int j = 0; j < currentCredits.length; j++) {
                if (j == nextPayer) {
                    // New credit is equal to previous - menu price + total bill price
                    currentCredits[j] = currentCredits[j] - coffeeCost[j] + totalCostPerVisit;
                } else {
                    currentCredits[j] = currentCredits[j] - coffeeCost[j];
                }
                if(currentCredits[j] < prevMin) {
                    prevMin = currentCredits[j];
                    prevMinIndex = j;
                }
            }
            System.out.println("Bill is paid by " + COFFEE_DRINKERS[nextPayer] + "\n");
            totalsPerPayers[nextPayer] = totalsPerPayers[nextPayer] + totalCostPerVisit;
        }
        
        System.out.println("Summary of total costs and per drink averages:\n");
        for (int i = 0; i < COFFEE_DRINKERS_CNT; i++) {
            double avgPaid = (double) totalsPerPayers[i]/numVisits;
            System.out.println(COFFEE_DRINKERS[i] +  "\t\tTotal: $" + df.format(totalsPerPayers[i]) + "\t Average: $" + df.format(avgPaid));
        }
    }
}