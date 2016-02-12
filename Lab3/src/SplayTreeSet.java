public class SplayTreeSet<E  extends Comparable<? super E>> implements SimpleSet<E> {

    private Node root;
    private int size = 0;

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
        return size;
    }

    @Override
    public boolean add(E x) {
        Node node = addRec(root, null, x);

        if (node == null) {
            return false;
        }else if(root == null) {
            root = node;
        }
        doSplay(node);
        size++;
        return true;
    }

    private void doSplay(Node node) {

        while(node.data.compareTo(root.data) != 0) {
            if (node.parent.data.compareTo(root.data) == 0) {
                //is node a right or left child? if right rotateleft - if left rotateright
                if(node.isLeftChild()){
                    rotateRight(node);
                }else{
                    rotateLeft(node);
                }
            } else if (node.isLeftChild() == node.parent.isLeftChild()) { // If node and it's parents both are both left children or right children
                // if node and parent are leftchildren, rotate parent and gparent right, then node and gparent right
                if(node.isLeftChild()){
                    rotateRight(node.parent);
                    rotateRight(node);
                }else{
                    rotateLeft(node.parent);
                    rotateLeft(node);
                }
            } else if (node.isLeftChild() != node.parent.isLeftChild()) { //If node and it's parent are not both left children or right children
                if(node.isLeftChild()){
                    rotateRight(node);
                    rotateLeft(node);
                }else{
                    rotateLeft(node);
                    rotateRight(node);
                }
            }
        }
    }

    private void rotateLeft(Node node){
        Node parent = node.parent;

        parent.right = node.left;
        node.left = parent;
        node.parent = parent.parent;
        parent.parent = node;
        updateRoot(node, parent);
    }

    private void rotateRight(Node node){
        Node parent = node.parent;

        parent.left = node.right;
        node.right = parent;
        node.parent = parent.parent;
        parent.parent = node;
        updateRoot(node, parent);
    }

    private void updateRoot(Node node, Node parent){
        if(parent.data.compareTo(root.data) == 0){
            root = node;
        }
    }

    private Node addRec(Node current, Node parent, E x) {
        if (current == null) {
            current = new Node(x);
            current.parent = parent;
            return current;
        } else if (x.compareTo(current.data) < 0) {
            return addRec(current.left, current, x);
        } else if (x.compareTo(current.data) > 0) {
            return addRec(current.right, current, x);
        }

        return null;
    }

    @Override
    public boolean remove(E x) {
        Node node = containsRec(root, x);
        if(node == null){
            return false;
        }
        if(root.data.compareTo(node.data) == 0){
            root = null;
        }else if(node.left == null && node.right == null){
            if(node.isLeftChild()){
                node.parent.left = null;
            }else{
                node.parent.right = null;
            }
        }else if(node.left == null){
            if(node.isLeftChild()){
                node.parent.left = node.right;
            }else{
                node.parent.right = node.right;
            }
        }else if(node.right == null){
            if(node.isLeftChild()){
                node.parent.left = node.left;
            }else{
                node.parent.right = node.left;
            }
        }else if(node.left != null && node.right != null){
            Node lrNode = findRightMostNode(node.left);
            node.data = lrNode.data;
            lrNode.parent.right = lrNode.left;
        }else{
            return false;
        }
        size--;
        return true;
    }

    private Node findRightMostNode(Node node){ //Find the (in this case) right most node under parameter Node
        if(node.right == null){
            return node;
        }
        return findRightMostNode(node.right);
    }

    @Override
    public boolean contains(E x) {
        Node node = containsRec(root, x);
        if(node != null){
            doSplay(node);
            return true;
        }
        return false;
    }

    public Node containsRec(Node node, E x){
        if(node == null) {
            return null;
        }else if(node.data.compareTo(x) > 0) {
            return containsRec(node.left, x);
        }else if(node.data.compareTo(x) < 0){
            return containsRec(node.right, x);
        }
        return node;
    }




}
