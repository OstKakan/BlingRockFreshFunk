class SortedLinkedListSet<E  extends Comparable<? super E>> implements SimpleSet<E> {

    private int size;
    private final Node dummyNode;

    public SortedLinkedListSet() {
        size = 0;
        dummyNode = new Node();
    }

    private class Node {
        private E data;
        private Node next;

        public Node() {
            this(null);
        }

        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * @return The size of the set
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Adds a new element to the set
     * @param x The data to add to the element
     * @return true if element was added, false otherwise
     */
    @Override
    public boolean add(E x) {
        Node node = new Node(x);
        Node currentNode = dummyNode;

        // Go through all nodes to see if already exists
        while (currentNode.next != null) {
            if (x.compareTo(currentNode.next.data) == 0) {
                return false;
            } else if (x.compareTo(currentNode.next.data) < 0) {
                node.next = currentNode.next;
                break;
            }
            currentNode = currentNode.next;
        }

        currentNode.next = node;
        size++;

        return true;
    }

    /**
     * Removes an element of the set
     * @param x The data of the element to remove
     * @return true if element was removed, false otherwise
     */
    @Override
    public boolean remove(E x) {
        Node currentNode = dummyNode;

        // Find the node and remove it
        while (currentNode.next != null) {
            if (x.compareTo(currentNode.next.data) == 0) {
                currentNode.next = currentNode.next.next;
                size--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    /**
     * @param x The data of the element to look for
     * @return true if element was found, false otherwise
     */
    @Override
    public boolean contains(E x) {
        Node currentNode = dummyNode;

        while (currentNode.next != null) {
            if (x.compareTo(currentNode.next.data) == 0) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }
}
