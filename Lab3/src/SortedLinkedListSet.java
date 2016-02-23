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

    @Override
    public int size() {
        return this.size;
    }

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

    @Override
    public boolean remove(E x) {
        Node currentNode = dummyNode;

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
