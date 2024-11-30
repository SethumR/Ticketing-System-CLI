package CLI;

public class Customer implements Runnable {
    private final int customerId;
    private final TicketPool ticketPool;
    private final int retrievalRate;

    public Customer(int customerId, TicketPool ticketPool, int retrievalRate) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        try {
            while (!ticketPool.isFull()) {
                Integer ticket = ticketPool.retrieveTicket(customerId);
                if (ticket == null) {
                    break; // No more tickets available
                }
                Thread.sleep(retrievalRate); // Simulate retrieval rate
            }
        } catch (InterruptedException e) {
            System.out.println("Customer " + customerId + " interrupted.");
        }
        System.out.println("Customer " + customerId + " finished buying tickets.");
    }
}
