import java.util.UUID;

public class TransactionsService {
    private UsersList users;

    public TransactionsService() {
        users = new UsersArrayList();
    }

    void addUser(String name) {
        users.addUser(new User(name));
    }

    void addUser(String name, Integer balance) {
        users.addUser(new User(name, balance));
    }

    void performTransaction(Integer senderId, Integer recipientId, Integer amount) throws IllegalTransactionException {
        User recipient = users.getUserById(recipientId);
        User sender = users.getUserById(senderId);
        if (amount < 0) {
            amount *= -1;
        }
        if (sender.getBalance() < amount) {
            throw new IllegalTransactionException("Wrong amount");
        }
        UUID uuid = UUID.randomUUID();
        Transaction outcome = new Transaction(uuid, recipient, sender, amount * -1, "credit");
        Transaction income = new Transaction(uuid, sender, recipient, amount, "debit");
        recipient.addTransaction(income);
        sender.addTransaction(outcome);
        recipient.setBalance(recipient.getBalance() + amount);
        sender.setBalance(sender.getBalance() - amount);
    }

    Integer retrieveBalance(Integer id) {
        return users.getUserById(id).getBalance();
    }

    Transaction[] retrieveTransactions(Integer id) {
        return users.getUserById(id).getTransactions().toArray();
    }

    void removeTransaction(Integer userId, String transactionId) {
        users.getUserById(userId).getTransactions().removeTransaction(transactionId);
    }

    Transaction[] checkValidityOfTransactions() {
        TransactionsLinkedList unpaired = new TransactionsLinkedList();
        Transaction[] userTrans;

        for (int i = 0; i < users.getNumberOfUsers(); i++) {
            userTrans = users.getUserByIndex(i).getTransactions().toArray();
            if (userTrans != null) {
                for (Transaction tran : userTrans) {
                    if (!isPaired(tran.getId(), i)) {
                        unpaired.addTransaction(tran);
                    }
                }
            }
        }
        return unpaired.toArray();
    }

    private boolean isPaired(String transactionId, int indexToSkip) {
        Transaction[] userTrans;
        for (int i = 0; i < users.getNumberOfUsers(); i++) {
            if (i != indexToSkip) {
                userTrans = users.getUserByIndex(i).getTransactions().toArray();
                if (userTrans != null) {
                    for (Transaction tran : userTrans) {
                        if (tran.getId().equals(transactionId)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
