package Bank;


public class Main {
    public static void main(String[] args) {
        // Create the bank
        Bank bank = new BankImplementation();

        // Create clients
        Client alice = new Client("Alice", 25);
        Client bob = new Client("Bob", 30);

        alice.addAccount(new DebitAccount("ACC001", 1000.0)); 
        alice.addAccount(new DebitAccount("ACC002", 500.0));  

        bob.addAccount(new DebitAccount("ACC003", 200.0));   
        bob.addAccount(new CreditAccount("ACC004", 0.0));     

        bank.addClient(alice);
        bank.addClient(bob);

        System.out.println("Initial balances:");
        alice.getAccounts().forEach(account -> 
            System.out.println("Alice's Account (" + account.getAccountNumber() + "): $" + account.getBalance())
        );
        bob.getAccounts().forEach(account -> 
            System.out.println("Bob's Account (" + account.getAccountNumber() + "): $" + account.getBalance())
        );

        ATM aliceATM = new ATMImplementation(alice);
        ATM bobATM = new ATMImplementation(bob);

        // Perform transactions
        System.out.println("\nPerforming transactions...");
        aliceATM.depositToAccount(alice.getAccounts().get(0), 300.0); // Deposit $300 to Alice's first account
        aliceATM.withdrawFromAccount(alice.getAccounts().get(1), 200.0); // Withdraw $200 from Alice's second account
        bobATM.depositToAccount(bob.getAccounts().get(0), 100.0);     // Deposit $100 to Bob's first account

        // Check balances after transactions
        System.out.println("\nBalances after transactions:");
        alice.getAccounts().forEach(account -> 
            System.out.println("Alice's Account (" + account.getAccountNumber() + "): $" + account.getBalance())
        );
        bob.getAccounts().forEach(account -> 
            System.out.println("Bob's Account (" + account.getAccountNumber() + "): $" + account.getBalance())
        );

        System.out.println("\nIssuing credits...");
        if (bank.issueCredit(alice, 1200.0)) { 
            System.out.println("Credit issued to Alice.");
        } else {
            System.out.println("Credit not issued to Alice.");
        }

        if (bank.issueCredit(bob, 500.0)) {
            System.out.println("Credit issued to Bob.");
        } else {
            System.out.println("Credit not issued to Bob.");
        }

        System.out.println("\nBalances after credit issuance:");
        alice.getAccounts().forEach(account -> 
            System.out.println("Alice's Account (" + account.getAccountNumber() + "): $" + account.getBalance())
        );
        bob.getAccounts().forEach(account -> 
            System.out.println("Bob's Account (" + account.getAccountNumber() + "): $" + account.getBalance())
        );

        System.out.println("\nAttempting to add a duplicate client...");
        try {
            bank.addClient(new Client("Alice", 22));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
