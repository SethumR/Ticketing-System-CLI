# Real-Time Ticket Simulation System

A robust real-time ticket simulation system built using Java, leveraging multithreading, synchronization, serialization, and file handling. The system provides a dynamic environment to simulate ticket vending and retrieval, based on user-defined parameters, with support for saving configurations and results in `.txt` and `.json` formats.

---

## 📖 Introduction

This project simulates a ticketing system where vendors and customers interact in real-time. The system:

- Accepts user inputs to define simulation parameters.
- Simulates ticket vending and customer retrieval processes with multithreading for concurrency.
- Manages ticket inventory using synchronization to ensure thread safety.
- Saves simulation data to `.txt` and `.json` files for analysis or reuse.

---

## ⚙️ Setup Instructions

### Prerequisites

Ensure the following are installed on your system:

- **Java**: JDK version 11 or higher.
- **Node.js**: *(Optional)* Only required if processing `.json` files using Node.js tools.

--

### Steps to Build and Run the Application

#### Clone the Repository

```bash
git clone https://github.com/SethumR/real-time-ticket-simulation.git  
cd real-time-ticket-simulation

```
#### Compile the Project

```bash
javac -d bin src/*.java  
```

#### Run the Simulation 

```bash
java -cp bin Main  
```


---

## 🚀 Usage Instrictions 
#### Configuring the System 
- Upon running the application, you’ll be prompted to enter:
- Total Number of Tickets: The total number of tickets available for the simulation.
- Ticket Release Rate (in milliseconds): How quickly vendors release tickets.
- Customer Retrieval Rate (in milliseconds): How quickly customers buy tickets.
- Maximum Ticket Capacity: The limit of tickets the system can handle at once.
- Number of Vendors: The number of vendors adding tickets.
- Number of Customers: The number of customers retrieving tickets.

#### Starting the Simulation 
Once all inputs are provided, the simulation begins automatically:
- Vendors add tickets to the system.
- Customers retrieve tickets concurrently.
 
Logs are displayed in the console for each operation, e.g.:
```bash
Dec 10, 2024 2:29:42 PM CLI.Configuration saveToFile
INFO: Configuration saved to config.json
Dec 10, 2024 2:29:42 PM CLI.Configuration saveToTextFile
INFO: Configuration saved to text file config.txt
Dec 10, 2024 2:29:42 PM CLI.Main main
INFO: Configuration saved to config.json and config.txt
Dec 10, 2024 2:29:42 PM CLI.TicketPool addTicket
INFO: Vendor 1 added Ticket ID: 1
Dec 10, 2024 2:29:42 PM CLI.TicketPool retrieveTicket
INFO: Customer 1 bought Ticket ID: 1
```

#### Saving the Results 
- Simulation data us saved in
- Text File (simulation.txt): Stores user inputs and simulation logs.
- JSON File (simulation.json): Stores the same data in JSON format for structured analysis.

---

## 🛠️ Key Features
- Concurrency: Utilizes Java threads to simulate real-time operations.
- Thread Safety: Synchronization ensures data integrity.
- Customizable: Flexible user inputs for dynamic simulations.
- Data Persistence: Saves results in multiple file formats for further usage.

---

## ☎️ Contact
- Name : Sethum Ruberu
- Email : Sethumgelaka6@gmail.com





