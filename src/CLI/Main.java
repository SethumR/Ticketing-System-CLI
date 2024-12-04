package CLI;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config = null;

        String loadConfig;
        while (true) {
            try {
                System.out.print("Load configuration from file? (yes/no): ");
                loadConfig = scanner.next().trim().toLowerCase(); // Normalize input
                if (loadConfig.equals("yes") || loadConfig.equals("no")) {
                    break; // Valid input, exit the loop
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } catch (Exception e) {
                System.out.println("Error reading input. Please enter 'yes' or 'no'.");
                scanner.next(); // Clear invalid input
            }
        }

        if (loadConfig.equalsIgnoreCase("yes")) {
            config = Configuration.loadFromFile("config.json");
            if (config == null) {
                System.out.println("Failed to load configuration. Exiting.");
                return;
            }
        } else {
            config = new Configuration();

            // Gather configuration inputs from the user
            config.setTotalTickets(getValidPositiveNumber(scanner, "Enter total number of tickets: "));
            config.setTicketReleaseRate(getValidPositiveNumber(scanner, "Enter ticket release rate (in milliseconds): "));
            config.setCustomerRetrievalRate(getValidPositiveNumber(scanner, "Enter customer retrieval rate (in milliseconds): "));
            config.setMaxTicketCapacity(getValidPositiveNumber(scanner, "Enter maximum ticket capacity: "));

            // Save the entered configuration
            config.saveToFile("config.json");
            config.saveToTextFile("config.txt");
            System.out.println("Configuration saved to config.json and Txt file");
        }

        // Initialize the ticket pool
        int totalTickets = config.getTotalTickets();
        int maxCapacity = config.getMaxTicketCapacity();
        TicketPool ticketPool = new TicketPool(totalTickets, maxCapacity);

        // Ask the user for the number of vendors and customers
        int numVendors = getValidPositiveNumber(scanner, "Enter the number of vendors: ");
        int numCustomers = getValidPositiveNumber(scanner, "Enter the number of customers: ");

        // Create thread pools dynamically
        ExecutorService vendorExecutor = Executors.newFixedThreadPool(numVendors);
        ExecutorService customerExecutor = Executors.newFixedThreadPool(numCustomers);

        // Divide tickets equally among vendors
        int ticketsPerVendor = totalTickets / numVendors;

        // Start vendor threads
        for (int i = 1; i <= numVendors; i++) {
            vendorExecutor.submit(new Vendor(i, ticketPool, ticketsPerVendor, config.getTicketReleaseRate()));
        }

        // Start customer threads
        for (int i = 1; i <= numCustomers; i++) {
            customerExecutor.submit(new Customer(i, ticketPool, config.getCustomerRetrievalRate()));
        }


        // Wait for threads to complete
        vendorExecutor.shutdown();
        customerExecutor.shutdown();

        try {
            vendorExecutor.awaitTermination(5, TimeUnit.MINUTES);
            customerExecutor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("System terminated. All tickets have been Sold.");
        scanner.close();
    }

    // Method to validate enterd values
    private static int getValidPositiveNumber(Scanner scanner, String question) {
        while (true) {
            System.out.print(question); // Display the question
            try {
                int value = scanner.nextInt(); // Read user input as an integer
                if (value > 0) {
                    return value; // Return the value if it's positive
                } else {
                    System.out.println("Invalid input. Please enter a positive number."); // Show error message
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number."); // Show error message
                scanner.next(); // Clear the invalid input
            }
        }
    }
}
