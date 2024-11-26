package CLI;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask the user for configuration inputs
        System.out.print("Enter total number of tickets: ");
        int totalTickets = scanner.nextInt();

        System.out.print("Enter ticket release rate (in milliseconds): ");
        int ticketReleaseRate = scanner.nextInt();

        System.out.print("Enter customer retrieval rate (in milliseconds): ");
        int customerRetrievalRate = scanner.nextInt();

        System.out.print("Enter maximum ticket capacity: ");
        int maxTicketCapacity = scanner.nextInt();

        // Initialize the ticket pool with user-defined maximum capacity
        TicketPool ticketPool = new TicketPool(maxTicketCapacity);

        // Create thread pools for vendors and customers
        ExecutorService vendorExecutor = Executors.newFixedThreadPool(2); // Two vendors
        ExecutorService customerExecutor = Executors.newFixedThreadPool(2); // Two customers

        // Start vendor threads
        for (int i = 1; i <= 2; i++) { // Simulate 2 vendors
            vendorExecutor.submit(new Vendor(i, ticketPool, ticketReleaseRate));
        }

        // Start customer threads
        for (int i = 1; i <= 2; i++) { // Simulate 2 customers
            customerExecutor.submit(new Customer(i, ticketPool, customerRetrievalRate));
        }

        // Monitor tickets and display status
        int totalSold = 0;
        while (true) {
            int availableTickets = ticketPool.getAvailableTickets();
            totalSold = totalTickets - availableTickets;

            System.out.println("Current Tickets Available: " + availableTickets);
            System.out.println("Total Tickets Sold: " + totalSold);

            if (totalSold >= totalTickets) { // Check if all tickets have been sold
                System.out.println("All tickets sold. Terminating system...");
                ticketPool.terminate();
                break;
            }

            try {
                Thread.sleep(1000); // Print status every second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Shut down vendor and customer threads
        vendorExecutor.shutdownNow();
        customerExecutor.shutdownNow();

        // Wait for threads to finish execution
        try {
            vendorExecutor.awaitTermination(5, TimeUnit.SECONDS);
            customerExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Tickets Available: " + ticketPool.getAvailableTickets());
        scanner.close();
    }
}
