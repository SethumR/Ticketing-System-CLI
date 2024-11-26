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
            while (!ticketPool.isTerminated()) {
                Integer ticket = ticketPool.retrieveTicket();
                if (ticket != null) {
                    System.out.println("Customer " + customerId + " purchased Ticket ID: " + ticket);
                } else {
                    System.out.println("Customer " + customerId + ": No available tickets.");
                }
                Thread.sleep(retrievalRate); // Pause based on retrieval rate
            }
        } catch (InterruptedException e) {
            System.out.println("Customer " + customerId + " terminated.");
        }
    }
}