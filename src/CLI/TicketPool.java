package CLI;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Integer> tickets;
    private final int totalTickets;
    private final int maxCapacity;
    private int ticketsAdded = 0; // Tracks the total number of tickets added to the pool

    public TicketPool(int totalTickets, int maxCapacity) {
        this.totalTickets = totalTickets;
        this.maxCapacity = maxCapacity;
        this.tickets = new LinkedList<>();
    }



    public synchronized boolean isFull() {
        return ticketsAdded >= totalTickets;
    }
}
