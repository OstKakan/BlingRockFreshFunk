class SortedLinkedListSet<E  extends Comparable<? super E>> implements SimpleSet<E> {

    private int size;
    private Node next;

    public SortedLinkedListSet() {
        size = 0;
        next = null;
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

        public Node getNext() {
            return this.next;
        }

        public E getData() {
            return this.data;
        }

        public void setData(E data) {
            this.data = data;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean add(E x) {
        Node node = new Node(x);

        return false;
    }

    @Override
    public boolean remove(E x) {
        return false;
    }

    @Override
    public boolean contains(E x) {
        return false;
    }
}