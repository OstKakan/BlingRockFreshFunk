class SortedLinkedListSet<E  extends Comparable<? super E>> implements SimpleSet<E> {

    private int size;
    private Node next;
    private Node first;

    public SortedLinkedListSet() {
        size = 0;
        next = null;
        first = null;
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
        Node loopNode = first;
        Node prevNode = null;

        while(loopNode != null && loopNode.data.compareTo(node.data) >= 0){
            prevNode = loopNode;
            loopNode = loopNode.next;
        }
        if(prevNode == null){
            first = node;
            node.next = loopNode;
            size++;
            return true;
        }else if(loopNode == null) {
            node.next = loopNode;
            prevNode.next = node;
            size++;
            return true;
        }else if(prevNode != null && loopNode != null){
            prevNode.next = node;
            node.next = loopNode;
            size++;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(E x) {
        Node node = new Node(x);
        Node loopNode = first;
        Node prevLoopNode = null;

        while(loopNode != null){

            if(loopNode.data.compareTo(node.data) == 0){
                if(prevLoopNode != null){
                    prevLoopNode.next = loopNode.next;
                }else{
                    first = loopNode.next;
                }
                size--;
                return true;
            }

            prevLoopNode = loopNode;
            loopNode = loopNode.next;
        }
        return false;
    }

    @Override
    public boolean contains(E x) {
        Node node = new Node(x);
        Node loopNode = first;

        while(loopNode != null){
            if(loopNode.data.compareTo(node.data) == 0){
                return true;
            }
            loopNode = loopNode.next;
        }
        return false;
    }
}