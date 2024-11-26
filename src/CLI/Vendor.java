package CLI;

public class Vendor implements Runnable {
    private final int vendorId;
    private final TicketPool ticketPool;
    private final int releaseRate;
    private static int ticketCounter = 1;

    public Vendor(int vendorId, TicketPool ticketPool, int releaseRate) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        try {
            while (!ticketPool.isTerminated()) {
                synchronized (Vendor.class) {
                    ticketPool.addTicket(ticketCounter++);
                }
                Thread.sleep(releaseRate); // Pause based on release rate
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor " + vendorId + " terminated.");
        }
    }
}

