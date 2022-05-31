import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        int id = 1;
        User userOne = new User(id++, "John", 500);
        User userTwo = new User(id++, "Mike", 700);
        int amount = -500;

        System.out.printf("%s balance %d\n%s balance %d\n", userOne.getName(),
                                                            userOne.getBalance(),
                                                            userTwo.getName(),
                                                            userTwo.getBalance());
        userOne.setBalance(userOne.getBalance() + amount);
        Transaction one = new Transaction(UUID.randomUUID(), userOne, userTwo, amount, "credit");
        amount *= -1;
        userTwo.setBalance(userTwo.getBalance() + amount);
        Transaction two = new Transaction(UUID.randomUUID(), userTwo, userOne, amount, "debit");
        System.out.printf("Transaction one: %s - id, %d - recipient, %d - sender, %d - amount, %s - category\n",
                one.getId(), one.getRecipient().getId(), one.getSender().getId(), one.getAmount(), one.getCategory());
        System.out.printf("Transaction two: %s - id, %d - recipient, %d - sender, %d - amount, %s - category\n",
                two.getId(), two.getRecipient().getId(), two.getSender().getId(), two.getAmount(), two.getCategory());
        System.out.printf("%s balance %d\n%s balance %d\n", userOne.getName(),
                                                            userOne.getBalance(),
                                                            userTwo.getName(),
                                                            userTwo.getBalance());
    }
}
