package Bank;

class ATMImplementation implements ATM {
    private Client client;

    public ATMImplementation(Client client) {
        this.client = client;
    }

    @Override
    public void depositToAccount(Account account, double amount) {
        account.deposit(amount);
    }

    @Override
    public void withdrawFromAccount(Account account, double amount) {
        if (!account.withdraw(amount)) {
            System.out.println("Insufficient funds.");
        }
    }

    @Override
    public double checkBalance(Account account) {
        return account.getBalance();
    }
}