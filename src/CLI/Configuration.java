package CLI;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity; // Add this field

    // Getters and Setters
    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public void setTicketReleaseRate(int ticketReleaseRate) { this.ticketReleaseRate = ticketReleaseRate; }

    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public void setCustomerRetrievalRate(int customerRetrievalRate) { this.customerRetrievalRate = customerRetrievalRate; }

    public int getMaxTicketCapacity() { return maxTicketCapacity; } // Getter
    public void setMaxTicketCapacity(int maxTicketCapacity) { this.maxTicketCapacity = maxTicketCapacity; } // Setter

    // Save configuration to a JSON file
    public void saveToFile(String fileName) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save configuration to a text file
    public void saveToTextFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Configuration Details:\n");
            writer.write("Total Tickets: " + totalTickets + "\n");
            writer.write("Ticket Release Rate (ms): " + ticketReleaseRate + "\n");
            writer.write("Customer Retrieval Rate (ms): " + customerRetrievalRate + "\n");
            writer.write("Maximum Ticket Capacity: " + maxTicketCapacity + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load configuration from a JSON file
    public static Configuration loadFromFile(String fileName) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(fileName)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
