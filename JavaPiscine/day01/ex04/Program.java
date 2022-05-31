public class Program {
    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();
        service.addUser("Mike");
        service.addUser("John", 500);

        System.out.printf("Mike's balance %d\n", service.retrieveBalance(1));
        System.out.printf("John's balance %d\n", service.retrieveBalance(2));
        service.performTransaction(2, 1, 500);
        System.out.printf("Mike's balance %d\n", service.retrieveBalance(1));
        System.out.printf("John's balance %d\n", service.retrieveBalance(2));
        Transaction[] tmp = service.retrieveTransactions(1);
        for (Transaction t : tmp) {
            System.out.println("Mike's transactions");
            System.out.printf("%s -> %s %d %s\n", t.getSender().getName(), t.getRecipient().getName(), t.getAmount(), t.getCategory());
        }
        tmp = service.retrieveTransactions(2);
        for (Transaction t : tmp) {
            System.out.println("John's transactions");
            System.out.printf("%s -> %s %d %s\n", t.getSender().getName(), t.getRecipient().getName(), t.getAmount(), t.getCategory());
        }
        service.removeTransaction(1, tmp[0].getId());
        System.out.println("\nkekekekeke\n");
        tmp = service.checkValidityOfTransactions();
        for (Transaction t : tmp) {
            System.out.printf("%s -> %s %d %s\n", t.getSender().getName(), t.getRecipient().getName(), t.getAmount(), t.getCategory());
        }
    }
}
