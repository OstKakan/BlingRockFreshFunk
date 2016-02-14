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
            if (this.parent != null) {
                return this.data.compareTo(this.parent.data) < 0;
            }
            return false;
        }
    }

    @Override
    public int size() {
        System.out.println("Checked size");
        return size;
    }

    @Override
    public boolean add(E x) {
        if(root == null){
            root = new Node(x);
        }
        Node node = addRec(root, x);
        if (node == null) {
            return false;
        }
        System.out.println("Trying to add");
        doSplay(node);
        size++;
        System.out.println("Added");
        return true;
    }


    private Node addRec(Node current, E x) {
        if(x.compareTo(current.data) < 0){
            if(current.left == null){
                current.left = new Node(x);
                current.left.parent = current;
                return current.left;
            }else{
                return addRec(current.left, x);
            }
        }else if(x.compareTo(current.data) > 0){
            if(current.right == null){
                current.right = new Node(x);
                current.right.parent = current;
                return current.right;
            }else{
                return addRec(current.right, x);
            }
        }
        return null;
    }

    private void doSplay(Node node) {

        while(node.data.compareTo(root.data) != 0) {
            /*    if (node.parent.data.compareTo(root.data) == 0) {
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
            }*/
            if(node.parent.data.compareTo(root.data) == 0){ //Parent of node is root - only one rotation
                if(node.parent.left == node){
                    rotateRight(node);
                }else if(node.parent.right == node){ //May want to check if right child
                    rotateLeft(node);
                }
            }else if(node.parent.left == node && node.parent.parent.left == node.parent){ //Both node and parent are left children
                rotateRight(node.parent);
                rotateRight(node);
            }else if(node.parent.right == node && node.parent.parent.right == node.parent){ // Both node and parent are right children
                rotateLeft(node.parent);
                rotateLeft(node);
            }else if(node.parent.left == node && node.parent.parent.right == node.parent){ //Parent right child, node left child
                rotateRight(node);
                rotateLeft(node);
            }else if(node.parent.right == node && node.parent.parent.left == node.parent){ // Parent left child, node right child
                rotateLeft(node);
                rotateRight(node);
            }//else{
                //System.out.println("Not sure - but I dont think this should be reached.......");
            //}
        }
    }

    private void rotateLeft(Node node){
        if(node == null){
            System.out.println("Parameter for rotateLeft is null, which shouldn't be allowed");
            return;
        }
        if(node.parent == null){
            System.out.println("rotateLeft: parameter.parent is null. Is parameter root? (Still not an allowed call) " + root + " = " + node);
            return;
        }
        if(node.parent.right != node){ //Should check the same thing
            System.out.println("Parameter node is not a rightChild and this method should not be picked");
            return;
        }
        Node exParent = node.parent;
        Node subtreeParent = exParent.parent;

        // Move "node"'s left subtree to its former parent.
        exParent.right = node.left;
        if (node.left != null) {
            node.left.parent = exParent;
        }

        // Make exParent become a child of "node".
        node.left = exParent;
        exParent.parent = node;

        // Make "node" become a child of exParent's former parent.
        node.parent = subtreeParent;
        if (subtreeParent == null) {
            root = node;
        } else if (subtreeParent.right == exParent) {
            subtreeParent.right = node;
        } else {
            subtreeParent.left = node;
        }

        /*Node parent = node.parent;

        parent.right = node.left;
        node.left = parent;
        node.parent = parent.parent;
        parent.parent = node;*/
        //updateRoot(node, exParent);
    }

    private void rotateRight(Node node){
        if (node == null || node.parent == null || node.parent.left != node) {
            System.out.println("Illegal call to rotateRight().  You have bug #1.");
            return;
        }

        Node exParent = node.parent;
        Node subtreeParent = exParent.parent;

        // Move "node"'s right subtree to its former parent.
        exParent.left = node.right;
        if(node.right != null){
            node.right.parent = exParent;
        }

        // Make exParent become a child of "node".
        node.right = exParent;
        exParent.parent = node;

        // Make "node" become a child of exParent's former parent.
        node.parent = subtreeParent;
        if(subtreeParent == null){
            root = node;
        }else if(subtreeParent.right == exParent) {
            subtreeParent.right = node;
        }else{
            subtreeParent.left = node;
    }

        /*Node parent = node.parent;

        parent.left = node.right;
        node.right = parent;
        node.parent = parent.parent;
        parent.parent = node;*/
        //updateRoot(node, exParent);
    }

    private void updateRoot(Node node, Node parent){
        if(parent.data.compareTo(root.data) == 0){
            root = node;
        }
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
        System.out.println("Removed");
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
        if(root == null){
            System.out.println("Checked contained");
            return false;
        }
        Node node = containsRec(root, x);
        if(node != null){
            doSplay(node);
            System.out.println("Checked contained");
            return true;
        }
        System.out.println("Checked contained");
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
