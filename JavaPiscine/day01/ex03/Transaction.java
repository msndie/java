import java.util.UUID;

public class Transaction {
    private final String id;
    private final User recipient;
    private final User sender;
    private final Integer amount;
    private final String category;

    public Transaction(UUID id, User recipient, User sender, Integer amount, String category) {
        this.id = id.toString();
        this.recipient = recipient;
        this.sender = sender;
        this.category = category;
        if (amount < 0 && category.equalsIgnoreCase("credit")) {
            this.amount = amount;
        } else if (amount > 0 && category.equalsIgnoreCase("debit")) {
            this.amount = amount;
        } else {
            this.amount = 0;
        }
    }

    public String getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }
}
