import java.io.*;
import java.util.*;

public class Main {

    static class NotFoundException extends Exception {
        NotFoundException(String message) {
            super(message);
        }
    }

    static class Client {
        String id, name, industry, email;

        Client(String id, String name, String industry, String email) {
            this.id = id;
            this.name = name;
            this.industry = industry;
            this.email = email;
        }

        public String toString() {
            return id + " | " + name + " | " + industry + " | " + email;
        }
    }

    static class Campaign {
        String id, clientId, title, status;
        double budget;

        Campaign(String id, String clientId, String title, String status, double budget) {
            this.id = id;
            this.clientId = clientId;
            this.title = title;
            this.status = status;
            this.budget = budget;
        }

        public String toString() {
            return id + " | Client:" + clientId + " | " + title + " | " + status + " | Rs." + budget;
        }
    }

    static class Invoice {
        String id, clientId;
        double amount;
        boolean paid;

        Invoice(String id, String clientId, double amount, boolean paid) {
            this.id = id;
            this.clientId = clientId;
            this.amount = amount;
            this.paid = paid;
        }

        public String toString() {
            return id + " | Client:" + clientId + " | Rs." + amount + " | " + (paid ? "PAID" : "UNPAID");
        }
    }

    static ArrayList<Client> clients = new ArrayList<>();
    static ArrayList<Campaign> campaigns = new ArrayList<>();
    static ArrayList<Invoice> invoices = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static int clientCounter = 1, campaignCounter = 1, invoiceCounter = 1;
    static final String DATA_FILE = "agency_data.txt";

    public static void main(String[] args) {
        loadData();
        System.out.println("=== Agency Client & Campaign Tracker ===");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Client Management");
            System.out.println("2. Campaign Management");
            System.out.println("3. Invoicing");
            System.out.println("4. Reports");
            System.out.println("5. Save & Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": clientMenu(); break;
                case "2": campaignMenu(); break;
                case "3": invoiceMenu(); break;
                case "4": reportsMenu(); break;
                case "5":
                    saveData();
                    running = false;
                    System.out.println("Data saved. Goodbye!");
                    break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    static void clientMenu() {
        System.out.println("\n--- Client Management ---");
        System.out.println("1. Add Client\n2. View All Clients\n3. Back");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            System.out.print("Client name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Industry: ");
            String industry = scanner.nextLine().trim();
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            String id = "C" + clientCounter++;
            clients.add(new Client(id, name, industry, email));
            System.out.println("Client added with ID: " + id);

        } else if (choice.equals("2")) {
            if (clients.isEmpty()) {
                System.out.println("No clients yet.");
            } else {
                for (Client c : clients) {
                    System.out.println(c);
                }
            }
        }
    }

    static void campaignMenu() {
        System.out.println("\n--- Campaign Management ---");
        System.out.println("1. Create Campaign\n2. View All Campaigns\n3. Back");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            System.out.print("Client ID this campaign is for: ");
            String clientId = scanner.nextLine().trim();

            try {
                findClient(clientId);

                System.out.print("Campaign title: ");
                String title = scanner.nextLine().trim();
                System.out.print("Budget (Rs.): ");
                double budget = Double.parseDouble(scanner.nextLine().trim());

                String id = "CM" + campaignCounter++;
                campaigns.add(new Campaign(id, clientId, title, "ACTIVE", budget));
                System.out.println("Campaign created with ID: " + id);

            } catch (NotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid budget amount.");
            }

        } else if (choice.equals("2")) {
            if (campaigns.isEmpty()) {
                System.out.println("No campaigns yet.");
            } else {
                for (Campaign c : campaigns) {
                    System.out.println(c);
                }
            }
        }
    }

    static void invoiceMenu() {
        System.out.println("\n--- Invoicing ---");
        System.out.println("1. Generate Invoice\n2. Mark Invoice Paid\n3. View All Invoices\n4. Back");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            System.out.print("Client ID: ");
            String clientId = scanner.nextLine().trim();

            try {
                findClient(clientId);

                System.out.print("Invoice amount (Rs.): ");
                double amount = Double.parseDouble(scanner.nextLine().trim());

                String id = "INV" + invoiceCounter++;
                invoices.add(new Invoice(id, clientId, amount, false));
                System.out.println("Invoice generated with ID: " + id);

            } catch (NotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount.");
            }

        } else if (choice.equals("2")) {
            System.out.print("Invoice ID to mark paid: ");
            String id = scanner.nextLine().trim();
            boolean found = false;
            for (Invoice i : invoices) {
                if (i.id.equals(id)) {
                    i.paid = true;
                    found = true;
                    System.out.println("Invoice " + id + " marked as PAID.");
                    break;
                }
            }
            if (!found) {
                System.out.println("Invoice not found.");
            }

        } else if (choice.equals("3")) {
            if (invoices.isEmpty()) {
                System.out.println("No invoices yet.");
            } else {
                for (Invoice i : invoices) {
                    System.out.println(i);
                }
            }
        }
    }

    static void reportsMenu() {
        System.out.println("\n--- Reports ---");
        System.out.println("Total clients: " + clients.size());
        System.out.println("Total campaigns: " + campaigns.size());

        double totalRevenue = 0, totalOutstanding = 0;
        for (Invoice i : invoices) {
            if (i.paid) {
                totalRevenue += i.amount;
            } else {
                totalOutstanding += i.amount;
            }
        }
        System.out.println("Revenue collected: Rs. " + totalRevenue);
        System.out.println("Outstanding dues: Rs. " + totalOutstanding);
    }

    static Client findClient(String id) throws NotFoundException {
        for (Client c : clients) {
            if (c.id.equals(id)) {
                return c;
            }
        }
        throw new NotFoundException("No client found with ID " + id);
    }

    static void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Client c : clients) {
                writer.write("CLIENT," + c.id + "," + c.name + "," + c.industry + "," + c.email);
                writer.newLine();
            }
            for (Campaign c : campaigns) {
                writer.write("CAMPAIGN," + c.id + "," + c.clientId + "," + c.title + "," + c.status + "," + c.budget);
                writer.newLine();
            }
            for (Invoice i : invoices) {
                writer.write("INVOICE," + i.id + "," + i.clientId + "," + i.amount + "," + i.paid);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    static void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("CLIENT")) {
                    clients.add(new Client(parts[1], parts[2], parts[3], parts[4]));
                    clientCounter++;
                } else if (parts[0].equals("CAMPAIGN")) {
                    campaigns.add(new Campaign(parts[1], parts[2], parts[3], parts[4], Double.parseDouble(parts[5])));
                    campaignCounter++;
                } else if (parts[0].equals("INVOICE")) {
                    invoices.add(new Invoice(parts[1], parts[2], Double.parseDouble(parts[3]), Boolean.parseBoolean(parts[4])));
                    invoiceCounter++;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load saved data: " + e.getMessage());
        }
    }
}
