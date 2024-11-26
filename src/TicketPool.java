package CLI;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class TicketPool {
    private final List<Integer> ticketList;
    private final int maxTicketCapacity;
    private boolean terminated = false;

    public TicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketList = Collections.synchronizedList(new ArrayList<>());
    }

    // Synchronized method to add tickets
    public synchronized void addTicket(int ticketId) {
        while (ticketList.size() >= maxTicketCapacity) {
            try {
                wait(); // Wait if the pool is at maximum capacity
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        ticketList.add(ticketId);
        System.out.println("Vendor added Ticket ID: " + ticketId);
        notifyAll(); // Notify customers that a new ticket is available
    }

    // Synchronized method to retrieve a ticket
    public synchronized Integer retrieveTicket() {
        while (ticketList.isEmpty() && !terminated) {
            try {
                wait(); // Wait if no tickets are available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (!ticketList.isEmpty()) {
            int ticketId = ticketList.remove(0);
            System.out.println("Customer purchased Ticket ID: " + ticketId);
            notifyAll(); // Notify vendors that space is available
            return ticketId;
        }
        return null;
    }

    public synchronized void terminate() {
        terminated = true;
        notifyAll(); // Wake up all waiting threads to terminate
    }

    public synchronized boolean isTerminated() {
        return terminated;
    }

    public synchronized int getAvailableTickets() {
        return ticketList.size();
    }
}
