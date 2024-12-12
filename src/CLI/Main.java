package CLI;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * @param args command-line arguments (not used in this version)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config = null;
        int numVendors = 0;
        int numCustomers = 0;

        // Configure the logger
        logger.setLevel(Level.INFO);

        // Ask user to load configuration from file
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

        // Load configuration or gather it from the user
        if (loadConfig.equalsIgnoreCase("yes")) {
            config = Configuration.loadFromFile("config.json");
            if (config == null) {
                logger.severe("Failed to load configuration. Exiting.");
                return;
            }

            // If configuration is loaded, prompt for vendors and customers
            numVendors = getValidPositiveNumber(scanner, "Enter the number of vendors: ");
            numCustomers = getValidPositiveNumber(scanner, "Enter the number of customers: ");
        } else {
            config = new Configuration();

            // Gather configuration inputs from the user
            config.setTotalTickets(getValidPositiveNumber(scanner, "Enter total number of tickets: "));
            config.setTicketReleaseRate(getValidPositiveNumber(scanner, "Enter ticket release rate (in milliseconds): "));
            config.setCustomerRetrievalRate(getValidPositiveNumber(scanner, "Enter customer retrieval rate (in milliseconds): "));
            config.setMaxTicketCapacity(getValidPositiveNumber(scanner, "Enter maximum ticket capacity: "));

            // Ask user for the number of vendors and customers
            numVendors = getValidPositiveNumber(scanner, "Enter the number of vendors: ");
            numCustomers = getValidPositiveNumber(scanner, "Enter the number of customers: ");

            // Save the entered configuration
            config.saveToFile("config.json");
            config.saveToTextFile("config.txt");
            logger.info("Configuration saved to config.json and config.txt");
        }

        // Initialize the ticket pool
        int totalTickets = config.getTotalTickets();
        int maxCapacity = config.getMaxTicketCapacity();
        TicketPool ticketPool = new TicketPool(totalTickets, maxCapacity);

        // Create thread pools for vendors and customers
        ExecutorService vendorExecutor = Executors.newFixedThreadPool(numVendors);
        ExecutorService customerExecutor = Executors.newFixedThreadPool(numCustomers);

        // Divide tickets equally between vendors
        int ticketsPerVendor = totalTickets / numVendors;

        // Start vendor threads
        for (int i = 1; i <= numVendors; i++) {
            vendorExecutor.submit(new Vendor(i, ticketPool, ticketsPerVendor, config.getTicketReleaseRate()));
        }

        // Start customer threads
        for (int i = 1; i <= numCustomers; i++) {
            customerExecutor.submit(new Customer(i, ticketPool, config.getCustomerRetrievalRate()));
        }

        // Wait for vendors and customers to complete their tasks
        vendorExecutor.shutdown();
        customerExecutor.shutdown();

        try {
            vendorExecutor.awaitTermination(5, TimeUnit.MINUTES);
            customerExecutor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Thread execution interrupted", e);
        }

        logger.info("System terminated. All tickets have been sold.");
        scanner.close();
    }

    /**
     * Prompts the user for a valid positive number and returns it.
     * If the user enters an invalid value, it will keep asking until a valid positive number is entered.
     *
     * @param scanner the Scanner object used to read input
     * @param question the question to ask the user
     * @return a valid positive number entered by the user
     */
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
