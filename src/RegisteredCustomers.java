public class RegisteredCustomers {
    private Node head; // Points to the oldest customer
    private Node tail; // Points to the newest customer
    private int size;

    public RegisteredCustomers() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    private class Node {
        private Customer customer;
        private Node next;

        public Node(Customer customer) {
            this.customer = customer;
            this.next = null;
        }
    }

    public void addCustomer(Customer customer) {
        Node newNode = new Node(customer);
        if (head == null) { // If the list is empty
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head; // Add the new customer at the beginning
            head = newNode;
        }
        size++;
    }

    public Customer getCustomer(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid customer index");
        }
        // Traverse the list from the head to find the customer at the specified index
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.customer;
    }


}