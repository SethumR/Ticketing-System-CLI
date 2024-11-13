package CLI;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalInterval;

    public Customer(TicketPool ticketPool, int retrievalInterval) {
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String ticket = ticketPool.purchaseTicket();
                System.out.println("Customer purchased: " + ticket);
                Thread.sleep(retrievalInterval * 1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Customer interrupted. Stopping ticket purchasing.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Error in Customer: " + e.getMessage());
        }
    }
}
