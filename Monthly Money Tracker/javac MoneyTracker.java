
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    enum Type { INCOME, EXPENSE }
    
    private LocalDate date;
    private Type type;
    private String description;
    private double amount;
    
    public Transaction(LocalDate date, Type type, String description, double amount) {
        this.date = date;
        this.type = type;
        this.description = description;
        this.amount = amount;
    }
    
    public LocalDate getDate() { return date; }
    public Type getType() { return type; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    
    @Override
    public String toString() {
        return String.format("%s | %-7s | %-20s | $%.2f", 
            date.format(DateTimeFormatter.ISO_LOCAL_DATE),
            type, 
            description, 
            amount);
    }
}

public class MoneyTracker {
    private List<Transaction> transactions = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        MoneyTracker tracker = new MoneyTracker();
        tracker.run();
    }
    
    private void run() {
        System.out.println("=== Monthly Money Tracker ===");
        
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Monthly Summary");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    addTransaction(Transaction.Type.INCOME);
                    break;
                case 2:
                    addTransaction(Transaction.Type.EXPENSE);
                    break;
                case 3:
                    showMonthlySummary();
                    break;
                case 4:
                    showTransactionHistory();
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void addTransaction(Transaction.Type type) {
        System.out.print("Enter date (YYYY-MM-DD, today if blank): ");
        String dateInput = scanner.nextLine();
        LocalDate date;
        
        if (dateInput.isEmpty()) {
            date = LocalDate.now();
        } else {
            date = LocalDate.parse(dateInput);
        }
        
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        
        transactions.add(new Transaction(date, type, description, amount));
        System.out.println(type + " added successfully!");
    }
    
    private void showMonthlySummary() {
        System.out.print("Enter month and year (MM/YYYY, current if blank): ");
        String monthYearInput = scanner.nextLine();
        
        int month, year;
        if (monthYearInput.isEmpty()) {
            LocalDate now = LocalDate.now();
            month = now.getMonthValue();
            year = now.getYear();
        } else {
            String[] parts = monthYearInput.split("/");
            month = Integer.parseInt(parts[0]);
            year = Integer.parseInt(parts[1]);
        }
        
        double totalIncome = 0;
        double totalExpenses = 0;
        
        for (Transaction t : transactions) {
            if (t.getDate().getMonthValue() == month && t.getDate().getYear() == year) {
                if (t.getType() == Transaction.Type.INCOME) {
                    totalIncome += t.getAmount();
                } else {
                    totalExpenses += t.getAmount();
                }
            }
        }
        
        double net = totalIncome - totalExpenses;
        
        System.out.println("\n=== Monthly Summary for " + month + "/" + year + " ===");
        System.out.printf("Total Income: $%.2f%n", totalIncome);
        System.out.printf("Total Expenses: $%.2f%n", totalExpenses);
        System.out.printf("Net: $%.2f%n", net);
    }
    
    private void showTransactionHistory() {
        System.out.println("\n=== Transaction History ===");
        System.out.println("Date       | Type    | Description         | Amount");
        System.out.println("--------------------------------------------------");
        
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}