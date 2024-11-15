package CLI;


public class Main {
    public static void main(String[] args) {
        try {
            Configuration config = new Configuration();
            TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

            // Create Vendor and Customer threads
            Vendor vendor = new Vendor(ticketPool, config.getTotalTickets(), config.getTicketReleaseRate());
            Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate());

            Thread vendorThread = new Thread(vendor);
            Thread customerThread = new Thread(customer);

            // Start threads
            vendorThread.start();
            customerThread.start();

            // Let the system run for 30 seconds as an example
            Thread.sleep(30000);

            // Stop the threads
            vendorThread.interrupt();
            customerThread.interrupt();
            System.out.println("Ticketing system stopped.");
        } catch (InterruptedException e) {
            System.out.println("System was interrupted: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Configuration error: " + e.getMessage());
        }
    }
}
