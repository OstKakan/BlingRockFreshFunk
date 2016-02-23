/**
 * Splay Tree, recently used nodes will be moved to the top of the tree, whilst maintaining a BST structure
 *
 * @param <E> The data in the nodes
 */
public class SplayTreeSet<E  extends Comparable<? super E>> implements SimpleSet<E> {

    private Node root;
    private int size = 0;

    public SplayTreeSet() {
        this.root = null;
    }

    private class Node {
        private E data;
        private Node left, right, parent;

        private Node() {
            this.data = null;
            this.left = null;
            this.right = null;
        }

        public Node(E data) {
            this();
            this.data = data;
        }
    }

    /**
     * @return the number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Adds a new node to the tree and splays it to the top of the tree
     *
     * @param x the data that the new node will contain
     * @return boolean stating if the add was successful or not
     */
    @Override
    public boolean add(E x) {
        // If root is null the tree is empty and a new root is made
        if(root == null){
            root = new Node(x);
            size++;
            return true;
        }

        // Insert node if it doesn't already exist
        Node node = addRec(root, x);
        if (node == null) {
            return false;
        }
        doSplay(node);
        size++;
        return true;
    }

    private Node addRec(Node current, E x) {
        // New node should be added to left side of current node
        if(x.compareTo(current.data) < 0){
            if(current.left == null){
                current.left = new Node(x);
                current.left.parent = current;
                return current.left;
            }else {
                return addRec(current.left, x);
            }
        // New node should be added to right side of current node
        } else if(x.compareTo(current.data) > 0){
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
        if(node == null){
            return;
        }

        while(node.data.compareTo(root.data) != 0) {
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
            }
        }
    }

    private void rotateLeft(Node node){
        //Save variables
        Node exParent = node.parent;
        Node exGrandParent = exParent.parent;

        exParent.right = node.left;
        if (node.left != null) { //Update the subtree of node.left, so it references the correct parent
            node.left.parent = exParent;
        }
        node.left = exParent; //As by the rules of a left rotation, node.left needs to reference its former parent as a left child
        exParent.parent = node; // the former parent which now is a child of a node, needs to get its parent reference updated

        node.parent = exGrandParent; //nodes new parent is its former grandparent
        if (exGrandParent == null) { //check if it's the new root for the tree and make it happen if so
            root = node;
        } else if (exGrandParent.right == exParent) {//Check if nodes former parent was a left or right child and take its place
            exGrandParent.right = node;
        } else {
            exGrandParent.left = node;
        }
    }

    private void rotateRight(Node node){
        //Save variables
        Node exParent = node.parent;
        Node exGrandParent = exParent.parent;

        exParent.left = node.right;
        if(node.right != null){ //Update the subtree root of node.right, so it reference the correct parent
            node.right.parent = exParent;
        }
        node.right = exParent; // As by the rules of a right rotation, node.right needs to reference its former parent as a right child
        exParent.parent = node; // the former parent which now is a child of node, needs to get it's parents reference updated

        node.parent = exGrandParent; //nodes new parent is it's former grandparent
        if(exGrandParent == null){ //check if it's the new root for the tree and make it happen if so
                root = node;
        }else if(exGrandParent.right == exParent) { //Check if nodes former parent was a left or right child and take its place
            exGrandParent.right = node;
        }else{
            exGrandParent.left = node;
        }
    }

    /**
     * Finds the right most node in a tree, which is the largest value
     *
     * @param node the root of the tree of which you need the largest value
     * @return returns the node that hold the largest value
     */
    private Node getRightMost(Node node){
        if(node.right != null){
            return getRightMost(node.right);
        }
        return node;
    }

    private void removeWithTwoChildren(Node node){
        Node rightMost = getRightMost(node.left);
        node.data = rightMost.data;
        removeNode(rightMost);
    }

    private void removeWithOneChild(Node node){
        if(node.parent == null){
            if(node.right != null){
                node.right.parent = null;
                root = node.right;
            }else {
                node.left.parent = null;
                root = node.left;
            }
            return;
        }

        if(node.parent.right == node){
            if(node.right != null){
                node.parent.right = node.right;
                node.right.parent = node.parent;
            }else{
                node.parent.right = node.left;
                node.left.parent = node.parent;
            }
        }else{
            if(node.right != null){
                node.parent.left = node.right;
                node.right.parent = node.parent;
            }else {
                node.parent.left = node.left;
                node.left.parent = node.parent;
            }
        }
    }

    private void removeWithNoChild(Node node){
        if(node.parent == null){
            root = null;
            return;
        }
        if(node.parent.right == node){
            node.parent.right = null;
        }else{
            node.parent.left = null;
        }
    }

    private void removeNode(Node node){
        if(node.left != null && node.right != null){
            removeWithTwoChildren(node);
        }else if(node.left == null && node.right == null){
            removeWithNoChild(node);
        }else{
            removeWithOneChild(node);
        }
        doSplay(node.parent);
    }

    /**
     * Removes node with specific data
     * @param x The data of the node to remove
     * @return true if node was removed, false otherwise
     */
    @Override
    public boolean remove(E x) {
        Node node = findNode(root, x);
        if(node == null){
            return false;
        }
        removeNode(node);
        size--;
        return true;
    }

    /**
     * @param x The data to check if exists in the set
     * @return true if the data is in set, false otherwise
     */
    @Override
    public boolean contains(E x) {
        if(root == null){
            return false;
        }
        Node node = findNode(root, x);
        if(node != null){
            doSplay(node);
            return true;
        }
        return false;
    }

    /**
     * Find a node in this set with a specific data
     * @param node The node to search from
     * @param x The data to search for
     * @return The node which holds the data
     */
    public Node findNode(Node node, E x){
        if(node == null) {
            return null;
        }else if(node.data.compareTo(x) > 0) {
            return findNode(node.left, x);
        }else if(node.data.compareTo(x) < 0){
            return findNode(node.right, x);
        }
        return node;
    }

    @Override
    public String toString(){
        StringBuilder values = new StringBuilder("[");
        toStringRec(root, values);
        values.append("]");
        return values.toString();
    }

    private void toStringRec(Node node, StringBuilder values) {
        if(node == null){
            return;
        }
        if (node.left != null) {
            toStringRec(node.left, values);
            values.append(", ");
        }
        values.append(node.data);
        if (node.right != null) {
            values.append(", ");
            toStringRec(node.right, values);
        }
    }
}
