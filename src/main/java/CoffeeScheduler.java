package main.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class CoffeeScheduler {
    private static final String[] COFFEE_DRINKERS = {"Jim", "Bob", "Batman", "Spiderman", "Hulk", "Wonder Woman", "Black Panther"};
    private static final int COFFEE_DRINKERS_CNT = COFFEE_DRINKERS.length;

    public static void main(String[] s) {
        Scanner scanner = new Scanner(System.in);

        int planDuration = promptForPlanDuration(scanner);
        double[] coffeePrices = promptForCoffeePrices(scanner);
        boolean printToConsole = printToConsole(scanner);
        double totalBillPerVisit = getTotalBillPerVisit(coffeePrices);

        double avgPricePerDrink = totalBillPerVisit / COFFEE_DRINKERS_CNT;
        double idealVisitsPerPerson = (double) planDuration / COFFEE_DRINKERS_CNT;

        double[] costDiffs = getPercentDifferenceFromAverage(coffeePrices, avgPricePerDrink);
        int[] actualVisitCounts = getVisitCountPerChoice(costDiffs, idealVisitsPerPerson);

        if (printToConsole) {
            printScheduleToConsole(coffeePrices, actualVisitCounts, planDuration, totalBillPerVisit);
        } else {
            printScheduleToFile(coffeePrices, actualVisitCounts, planDuration, totalBillPerVisit);
        }
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
        java.util.Arrays.sort(prices);
        return prices;
    }

    /**
     * Prompts the user to choose whether to print the schedule to the console or a file.
     * If user doesn't specify, defaults to console.
     * @param scanner Scanner for user input (to enable unit testing)
     * @return true if printing to console, false if printing to file
     */
    public static boolean printToConsole(Scanner scanner) {
        String choice;
        while (true) {
            System.out.print("Do you want to print the coffee buying schedule to (1) Console or (2) File? Enter 1 or 2: ");
            choice = scanner.nextLine();
            if (choice == null || choice.trim().isEmpty()) {
                System.out.println("Using default option: Console.");
                return true; // Default to console if no input
            }
            if ("1".equals(choice) || "2".equals(choice)) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 1 for Console or 2 for File.");
            }
        }
        return "1".equals(choice);
    }

    /**
     * Calculates the total bill for one round of coffee purchases.
     * @param coffeePrices Array of coffee prices for each drinker
     * @return The total bill per visit
     */
    public static double getTotalBillPerVisit(double[] coffeePrices) {
        double total = 0;
        for (double i : coffeePrices) {
            total += i;
        }
        return total;
    }

    /**
     * Calculates the percent difference for each person between the menu price of the person's chosen
     * drink and the average price for all coffees in the order.
     * @param coffeePrices Array of coffee prices for each drinker
     * @param avgCost The average price per drink
     * @return Array of percent differences for each drinker
     */
    public static double[] getPercentDifferenceFromAverage(double[] coffeePrices, double avgCost) {
        double[] costDiffs = new double[coffeePrices.length];
        for (int i = 0; i < coffeePrices.length; i++) {
            costDiffs[i] = (double) coffeePrices[i] / avgCost;
        }
        return costDiffs;
    }

    /**
     * Calculates the number of times each coffee drinker should purchase coffee for the group based 
     * on cost differences.
     * @param costDiffs Array of percent differences for each drinker
     * @param idealVisitsPerPerson The ideal average number of visits per person
     * @return Array of visit counts for each drinker
     */
    public static int[] getVisitCountPerChoice(double[] costDiffs, double idealVisitsPerPerson) {
        int[] actualVisitCounts = new int[costDiffs.length];
        for (int i = 0; i < costDiffs.length; i++) {
            actualVisitCounts[i] = (int) Math.round(costDiffs[i] * idealVisitsPerPerson);
        }
        return actualVisitCounts;
    }

    /**
     * Writes a summary overview and coffee buying schedule to a file.
     * @param coffeePrices Array of coffee prices for each drinker
     * @param actualVisitCounts Array of visit counts for each drinker
     * @param planDuration The total number of days in the schedule
     * @param totalBillPerVisit The total bill per visit
     */
    public static void printScheduleToFile(double[] coffeePrices, int[] actualVisitCounts, int planDuration, double totalBillPerVisit) {
        String fileName = "CoffeeSchedule.txt";
        int day = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String summary = getSummary(coffeePrices, actualVisitCounts, planDuration, totalBillPerVisit);
            writer.write(summary);
            while (day <= planDuration) {
                for (int i = COFFEE_DRINKERS_CNT - 1; i >= 0; i--) {
                    day++;  // This ensures that we break out of the loop when all visits are done
                    if (actualVisitCounts[i] <= 0) continue; // Skip if the coffee drinker already paid all his visits
                    if (day > planDuration) break; // Stop if we've reached the plan duration
                    writer.write("Day " + day + ": " + COFFEE_DRINKERS[i] + " buys coffee for the group.\n");
                    if (day % 10 == 0) writer.newLine(); // Add a blank line every 10 lines for readability
                    actualVisitCounts[i] = actualVisitCounts[i] - 1; // Decrement the visit count after writing to file
                }
            }
            System.out.println("Coffee buying schedule written to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Prints a summary overview and coffee buying schedule to the console.
     * @param coffeePrices Array of coffee prices for each drinker
     * @param actualVisitCounts Array of visit counts for each drinker
     * @param planDuration The total number of days in the schedule
     * @param totalBillPerVisit The total bill per visit
     */
    public static void printScheduleToConsole(double[] coffeePrices, int[] actualVisitCounts, int planDuration, double totalBillPerVisit) {
        String summary = getSummary(coffeePrices, actualVisitCounts, planDuration, totalBillPerVisit);
        int day = 0;
        System.out.println(summary);
        while (day <= planDuration) {
            for (int i = COFFEE_DRINKERS_CNT - 1; i >= 0; i--) {
                day++;  // This ensures that we break out of the loop when all visits are done
                if (actualVisitCounts[i] <= 0) continue; // Skip if the coffee drinker already paid all his visits
                if (day > planDuration) break; // Stop if we've reached the plan duration
                System.out.println("Day " + day + ": " + COFFEE_DRINKERS[i] + " buys coffee for the group.");
                if (day % 10 == 0) System.out.println(); // Add a blank line every 10 lines for readability
                actualVisitCounts[i] = actualVisitCounts[i] - 1; // Decrement the visit count after writing to file
            }
        }
    }

    /**
     * Builds a summary string for the coffee buying schedule. The summary includes the total total number of
     * of visits, and the per person breakdown of number of times they will buy for the group and information
     * about the menu price of their preferred drink versus the average price they will pay over time.
     * @param coffeePrices Array of coffee prices for each drinker
     * @param actualVisitCounts Array of visit counts for each drinker
     * @param planDuration The total number of days in the schedule
     * @param totalBillPerVisit The total bill per visit
     * @return Summary string
     */
    public static String getSummary(double[] coffeePrices, int[] actualVisitCounts, int planDuration, double totalBillPerVisit) {
        StringBuilder summary = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.00");
        summary.append("Summary of Coffee Buying Schedule:\n");
        summary.append("Total Plan Duration (visits): ").append(planDuration).append("\n");
        for(int i = 0; i < actualVisitCounts.length; i++) {
            double actual = (actualVisitCounts[i] * totalBillPerVisit) / planDuration;
            summary.append("Employee: ").append(COFFEE_DRINKERS[i]).append("\n")
                   .append(" Visits planned: ").append(actualVisitCounts[i]).append("\n")
                   .append(" Store displayed price for preferred drink: $").append(df.format(coffeePrices[i])).append("\n")
                   .append(" Price paid averaged over time: $").append(df.format(actual)).append("\n");
        }
        summary.append("\n--------Buying Schedule--------\n");

        return summary.toString();
    }
}