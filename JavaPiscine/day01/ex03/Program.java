import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        Transaction[] arr = new Transaction[10];
        User one = new User("John");
        User two = new User("Mike");
        TransactionsLinkedList list = new TransactionsLinkedList();

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                arr[i] = new Transaction(UUID.randomUUID(), one, two, 0, "credit");
            } else {
                arr[i] = new Transaction(UUID.randomUUID(), one, two, 0, "debit");
            }
        }
        System.out.println("size - " + list.getSize());
        for (Transaction t : arr) {
            list.addTransaction(t);
        }
        System.out.println("size - " + list.getSize());
        for (Transaction t : arr) {
            System.out.println(t.getId());
            list.removeTransaction(t.getId());
        }
        System.out.println("size - " + list.getSize());
        for (int i = 0; i < 5; i++) {
            list.addTransaction(arr[i]);
        }
        System.out.println("size - " + list.getSize());
        Transaction[] tmp = list.toArray();
        for (Transaction t : tmp) {
            System.out.println(t.getId());
        }
        list.removeTransaction("kek");
    }
}
