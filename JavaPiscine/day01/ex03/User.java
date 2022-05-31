public class User {
    private final Integer id;
    private final String name;
    private Integer balance;
    private TransactionsList transactions;

    public User(String name, Integer balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        if (balance < 0) {
            this.balance = 0;
        } else {
            this.balance = balance;
        }
    }

    public User(String name) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = 0;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        if (balance < 0) {
            return;
        }
        this.balance = balance;
    }

    public TransactionsList getTransactions() {
        return transactions;
    }

    public void setTransactions(TransactionsList transactions) {
        this.transactions = transactions;
    }
}
