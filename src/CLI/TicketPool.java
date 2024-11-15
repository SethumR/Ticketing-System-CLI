package CLI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private final List<String> tickets;

    public TicketPool(int maxCapacity) {
        this.tickets = Collections.synchronizedList(new ArrayList<>(maxCapacity));
    }

    public synchronized void addTickets(int count) {
        for (int i = 0; i < count; i++) {
            tickets.add("Ticket-" + (tickets.size() + 1));
            System.out.println("Ticket added: Ticket-" + tickets.size());
        }
    }


    public synchronized String purchaseTicket() throws InterruptedException {
        if (tickets.isEmpty()) {
            throw new InterruptedException("No tickets available. Please wait.");
        }
        return tickets.remove(0);
    }

    public int getAvailableTickets() {
        return tickets.size();
    }
}