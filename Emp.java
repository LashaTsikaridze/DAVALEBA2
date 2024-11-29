package Bank;


import java.util.*;

interface Bank {
    void addClient(Client client);
    boolean issueCredit(Client client, double amount);
}

interface ATM {
    void depositToAccount(Account account, double amount);
    void withdrawFromAccount(Account account, double amount);
    double checkBalance(Account account);
}

abstract class Account {
    protected String accountNumber;
    protected double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class DebitAccount extends Account {
    public DebitAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }
}

class CreditAccount extends Account {
    public CreditAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }
}

class Client {
    private String name;
    private int age;
    private List<Account> accounts;

    public Client(String name, int age) {
        this.name = name;
        this.age = age;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public double getTotalDeposit() {
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }
}

class BankImplementation implements Bank {
    private Set<Client> clients;

    public BankImplementation() {
        this.clients = new HashSet<>();
    }

    @Override
    public void addClient(Client client) {
        if (clients.stream().anyMatch(c -> c.getName().equals(client.getName()))) {
            throw new IllegalArgumentException("Client with this name already exists.");
        }
        clients.add(client);
    }

    @Override
    public boolean issueCredit(Client client, double amount) {
        if (client.getAge() < 18) {
            System.out.println("Client does not meet the age requirement for credit.");
            return false;
        }

        if (client.getTotalDeposit() >= amount) {
            System.out.println("Credit of " + amount + " issued to " + client.getName());
            client.addAccount(new CreditAccount(UUID.randomUUID().toString(), amount));
            return true;
        } else {
            System.out.println("Insufficient deposit for credit.");
            return false;
        }
    }
}

