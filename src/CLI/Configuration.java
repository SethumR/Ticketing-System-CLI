package CLI;

import java.util.Scanner;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public Configuration() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter total number of tickets:");
            this.totalTickets = scanner.nextInt();

            System.out.println("Enter ticket release rate (tickets/sec):");
            this.ticketReleaseRate = scanner.nextInt();

            System.out.println("Enter customer retrieval rate (tickets/sec):");
            this.customerRetrievalRate = scanner.nextInt();

            System.out.println("Enter max ticket capacity:");
            this.maxTicketCapacity = scanner.nextInt();

            validateInputs();
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter numeric values only.");
            scanner.nextLine();
        } finally {
            scanner.close();
        }
    }


    private void validateInputs() throws IllegalArgumentException {
        if (totalTickets <= 0 || ticketReleaseRate <= 0 || customerRetrievalRate <= 0 || maxTicketCapacity <= 0) {
            throw new IllegalArgumentException("All values must be positive numbers.");
        }
    }

    public int getTotalTickets() { return totalTickets; }
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
}