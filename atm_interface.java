import java.util.*;

// User class to represent ATM users
class User {
    private String userId;
    private String pin;
    private double balance;
    private List<Transaction> transactionHistory;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    // Method to add a transaction to the history
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    // Method to update balance after each transaction
    public void updateBalance(double amount) {
        balance += amount;
    }
}

// Transaction class to represent individual transactions
class Transaction {
    private String type;
    private double amount;
    private Date timestamp;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = new Date();
    }

    // Getter methods for transaction details
    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

// Interface for ATM operations
interface ATMOperations {
    void showTransactionHistory(User user);

    void withdraw(User user, double amount);

    void deposit(User user, double amount);

    void transfer(User user, String recipientId, double amount);
}

// ATM class implementing ATMOperations interface
class ATM implements ATMOperations {
    private Map<String, User> users; // Simulated user database

    public ATM() {
        users = new HashMap<>();
    }

    // Method to authenticate user
    public User authenticateUser(String userId, String pin) throws IllegalArgumentException {
        if (users.containsKey(userId)) {
            User user = users.get(userId);
            if (user.getPin().equals(pin)) {
                return user;
            } else {
                throw new IllegalArgumentException("Invalid PIN");
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public void showTransactionHistory(User user) {
        System.out.println("Transaction History for User: " + user.getUserId());
        for (Transaction transaction : user.getTransactionHistory()) {
            System.out.println(transaction.getType() + " - Amount: " + transaction.getAmount() + " - Timestamp: "
                    + transaction.getTimestamp());
        }
    }

    @Override
    public void withdraw(User user, double amount) {
        if (user.getBalance() >= amount) {
            user.updateBalance(-amount);
            user.addTransaction(new Transaction("Withdrawal", -amount));
            System.out.println("Withdrawal successful. Remaining balance: " + user.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    @Override
    public void deposit(User user, double amount) {
        user.updateBalance(amount);
        user.addTransaction(new Transaction("Deposit", amount));
        System.out.println("Deposit successful. Updated balance: " + user.getBalance());
    }

    @Override
    public void transfer(User user, String recipientId, double amount) {
        if (users.containsKey(recipientId)) {
            User recipient = users.get(recipientId);
            if (user.getBalance() >= amount) {
                user.updateBalance(-amount);
                recipient.updateBalance(amount);
                user.addTransaction(new Transaction("Transfer to " + recipientId, -amount));
                recipient.addTransaction(new Transaction("Transfer from " + user.getUserId(), amount));
                System.out.println("Transfer successful. Remaining balance: " + user.getBalance());
            } else {
                System.out.println("Insufficient balance for transfer.");
            }
        } else {
            System.out.println("Recipient user not found.");
        }
    }

    // Method to add a new user to the system
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }
}

// Main class to run the ATM system
public class atm_interface {
    public static void main(String[] args) {
        ATM atm = new ATM();

        // Sample users added to the system
        atm.addUser(new User("user1", "1234"));
        atm.addUser(new User("user2", "5678"));

        Scanner scanner = new Scanner(System.in);

        // User authentication
        System.out.println("Welcome to the ATM");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        try {
            User currentUser = atm.authenticateUser(userId, pin);
            System.out.println("Authentication successful. Welcome, " + currentUser.getUserId());

            // ATM operations menu
            boolean exit = false;
            while (!exit) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Show Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        atm.showTransactionHistory(currentUser);
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        atm.withdraw(currentUser, withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        atm.deposit(currentUser, depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient's User ID: ");
                        String recipientId = scanner.nextLine();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        atm.transfer(currentUser, recipientId, transferAmount);
                        break;
                    case 5:
                        exit = true;
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
