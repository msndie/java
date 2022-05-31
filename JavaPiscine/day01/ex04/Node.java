public class Node {
    Node prev;
    Node next;
    Transaction t;

    public Node(Node prev, Node next, Transaction t) {
        this.prev = prev;
        this.next = next;
        this.t = t;
    }
}
