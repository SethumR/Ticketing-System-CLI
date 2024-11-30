package CLI;

public class Vendor implements Runnable {
    private final int vendorId;
    private final TicketPool ticketPool;
    private final int ticketsToAdd;
    private final int releaseRate;
    private static int ticketCounter = 1;

    public Vendor(int vendorId, TicketPool ticketPool, int ticketsToAdd, int releaseRate) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.ticketsToAdd = ticketsToAdd;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < ticketsToAdd; i++) {
                ticketPool.addTicket(vendorId, ticketCounter++);
                Thread.sleep(releaseRate); // Simulate release rate
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor " + vendorId + " interrupted.");
        }
        System.out.println("Vendor " + vendorId + " finished adding tickets.");
    }
}
