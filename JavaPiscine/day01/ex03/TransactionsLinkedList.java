public class TransactionsLinkedList implements TransactionsList {
    private Node first;
    private Node last;
    private int size;

    public TransactionsLinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public void addTransaction(Transaction t) {
        if (size == 0) {
            first = new Node(null, null, t);
            last = first;
        } else {
            last.next = new Node(last, null, t);
            last = last.next;
        }
        ++size;
    }

    @Override
    public void removeTransaction(String id) throws TransactionNotFoundException {
        Node tmp;
        Node tmpPrev;
        Node tmpNext;

        if (first == null) {
            throw new TransactionNotFoundException("List is empty");
        }
        if (id == null) {
            return;
        }
        if (first.t.getId().equals(id)) {
            first = first.next;
            --size;
            return;
        }
        tmp = first;
        while (tmp != null) {
            if (tmp.t.getId().equals(id)) {
                break;
            }
            tmp = tmp.next;
        }
        if (tmp == null) {
            throw new TransactionNotFoundException("Wrong id");
        }
        tmpPrev = tmp.prev;
        tmpNext = tmp.next;
        if (tmpPrev != null) {
            tmpPrev.next = tmp.next;
        }
        if (tmpNext != null) {
            tmpNext.prev = tmp.prev;
        }
        tmp.prev = null;
        tmp.next = null;
        --size;
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] arr;
        Node tmp;
        int i = 0;
        if (size == 0) {
            return null;
        }
        arr = new Transaction[size];
        tmp = first;
        while (tmp != null) {
            arr[i++] = tmp.t;
            tmp = tmp.next;
        }
        return arr;
    }

    public int getSize() {
        return size;
    }
}
