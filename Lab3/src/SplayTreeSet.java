import java.util.Iterator;

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
        return size;
    }

    @Override
    public boolean add(E x) {
        if(root == null){
            root = new Node(x);
            size++;
            return true;
        }
        Node node = addRec(root, x);
        if (node == null) {
            return false;
        }
        //System.out.println("Trying to add");
        doSplay(node);
        size++;
        //System.out.println("Added");
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
        if(node == null){
            return;
        }
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

        //Save variables
        Node exParent = node.parent;
        Node exGrandParent = exParent.parent;

        exParent.right = node.left;
        /*
        Vi missade i princip att referera om .parent i subträden, vilket orsakade våra cirkulära relationer
         */
        if (node.left != null) {
            node.left.parent = exParent;
        }
        node.left = exParent;
        exParent.parent = node;

        node.parent = exGrandParent;
        if (exGrandParent == null) {
            root = node;
        } else if (exGrandParent.right == exParent) {
            exGrandParent.right = node;
        } else {
            exGrandParent.left = node;
        }
    }

    private void rotateRight(Node node){
        if(node == null){
            System.out.println("Parameter for rotateRight is null, which shouldn't be allowed");
            return;
        }
        if(node.parent == null){
            System.out.println("rotateRight: parameter.parent is null. Is parameter root? (Still not an allowed call) " + root + " = " + node);
            return;
        }
        if(node.parent.left != node){
            System.out.println("Parameter node is not a leftChild and this method should not be picked");
            return;
        }
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
        /*parent.left = node.right;
        node.right = parent;
        node.parent = parent.parent;
        parent.parent = node;
        updateRoot(node, parent);
        parent.parent = node;*/
    }

    private Node getRightMost(Node node){
        //Kan va kass kod
        if(node.right != null){
            return getRightMost(node.right);
        }
        return node;
    }


    ///////////////////////////////////////////////ORSAKAR NULLPOINTER///////////////////////////////////////////////
    private void removeTwoChildren(Node node){

        Node rightMost = getRightMost(node.left);
        node.data = rightMost.data;
        removeNode(rightMost);

    }

    private void removeOneChild(Node node){
        if((node.left == null && node.right == null) || (node.left != null && node.right != null)){
            System.out.println("Idiot, den har ju antingen inget eller två barn! [removeOneChild]");
            return;
        }
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

    private void removeNoChild(Node node){
        if(node.left != null || node.right != null){
            System.out.println("Idiot, den har ju för fan barn! [removeNoChild]");
            return;
        }
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
            removeTwoChildren(node);
        }else if(node.left == null && node.right == null){
            removeNoChild(node);
        }else{
            removeOneChild(node);
        }
        doSplay(node.parent);
    }



    @Override
    public boolean remove(E x) {
        Node node = containsRec(root, x);
        if(node == null){
            return false;
        }
        removeNode(node);
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
        if(root == null){
            return false;
        }
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
