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

    // Synchronized method to add tickets
    public synchronized void addTicket(int vendorId, int ticketId) {
        while (tickets.size() >= maxCapacity) {
            try {
                System.out.println("Vendor " + vendorId + ": Ticket pool is full, waiting for customers to buy tickets...");
                wait(); // Wait until there's space in the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        if (ticketsAdded < totalTickets) {
            tickets.add(ticketId);
            ticketsAdded++;
            System.out.println("Vendor " + vendorId + " added Ticket ID: " + ticketId);
            notifyAll(); // Notify customers that a new ticket is available
        }
    }

    // Synchronized method to retrieve a ticket
    public synchronized Integer retrieveTicket(int customerId) {
        while (tickets.isEmpty()) {
            try {
                System.out.println("Customer " + customerId + ": No tickets available, waiting for vendors...");
                wait(); // Wait until a ticket is available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Integer ticketId = tickets.poll(); // Retrieve and remove the ticket
        System.out.println("Customer " + customerId + " bought Ticket ID: " + ticketId);
        notifyAll(); // Notify vendors that space is available
        return ticketId;
    }

    public synchronized boolean isFull() {
        return ticketsAdded >= totalTickets;
    }
}
