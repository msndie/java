public interface TransactionsList {
    void addTransaction(Transaction t);
    void removeTransaction(String id);
    Transaction[] toArray();
}
