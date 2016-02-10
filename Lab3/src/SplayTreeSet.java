public class SplayTreeSet<E  extends Comparable<? super E>> implements SimpleSet<E> {

    private Node root;

    public SplayTreeSet() {
        this.root = null;
    }

    private class Node {
        private E data;
        private Node left, right, parent;

        public Node() {
            this.data = null;
            this.left = null;
            this.right = null;
        }

        public Node(E data) {
            this.data = data;
        }

        public boolean isLeftChild() {
            if (parent != null) {
                return this.data.compareTo(this.parent.data) < 0;
            }
            return false;
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean add(E x) {
        Node node = add(root, null, x);

        if (node == null) {
            return false;
        }

        doSplay(node);

        return true;
    }

    private void doSplay(Node node) {
        if (node.data.compareTo(root.data) == 0) {

        } else if (node.parent.data.compareTo(root.data) == 0) {
            // TODO: Zig
        } else if (node.isLeftChild() == node.parent.isLeftChild()){
            // TODO: ZigZig
        } else if(node.isLeftChild() != node.parent.isLeftChild()){
            // TODO: ZigZag
        }
    }

    private Node add(Node current, Node parent, E x) {
        if (current == null) {
            current = new Node(x);
            current.parent = parent;
            return current;
        } else if (x.compareTo(current.data) < 0) {
            return add(current.left, current, x);
        } else if (x.compareTo(current.data) > 0) {
            return add(current.right, current, x);
        }

        return null;
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
