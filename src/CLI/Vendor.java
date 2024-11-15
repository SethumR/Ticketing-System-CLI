package CLI;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketsPerRelease;
    private final int releaseInterval;

    public Vendor(TicketPool ticketPool, int ticketsPerRelease, int releaseInterval) {
        this.ticketPool = ticketPool;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.addTickets(ticketsPerRelease);
                System.out.println("Vendor released " + ticketsPerRelease + " tickets.");
                Thread.sleep(releaseInterval * 1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor interrupted. Stopping ticket release.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Error in Vendor: " + e.getMessage());
        }
    }
}