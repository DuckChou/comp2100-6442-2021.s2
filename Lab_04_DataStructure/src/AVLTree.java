/**
 * Welcome! Make sure to check out 'readme.md' for a rundown of requirements/description of this implementation
 * that may differ from normal implementations. Before starting, it may also be worth checking out Tree.java
 * and BinarySearchTree.java as all method description is contained in the superclass unless edited. For
 * example: the description for 'insert' cannot be found here. It is in the superclass!
 * <p>
 * Please note that you may edit this class as much as you like (i.e.,create helper methods if you want!).
 * So long as you genuinely pass the tests (i.e., do not change existing methods signatures). Ask questions if you are
 * lost or unsure.
 * You SHALL NOT edit any other classes.
 * <p>
 * Lastly, if you are looking to better visualise the results of your insertion, you are free print the contents
 * of the method '.display()' (found in Tree.java which class, AVLTree, extends through BinarySearchTree). This
 * method will provide you with a graphical representation of the tree.
 */
public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {
    /*
        As a result of inheritance by using 'extends BinarySearchTree<T>,
        all class fields within BinarySearchTree are also present here.
        So while not explicitly written here, this class has:
            - value
            - leftNode
            - rightNode
     */

    public AVLTree(T value) {
        super(value);
        // Set left and right children to be of EmptyAVL as opposed to EmptyBST.
        this.leftNode = new EmptyAVL<>();
        this.rightNode = new EmptyAVL<>();
    }

    public AVLTree(T value, Tree<T> leftNode, Tree<T> rightNode) {
        super(value, leftNode, rightNode);
    }

    /**
     * @return balance factor of the current node.
     */
    public int getBalanceFactor() {
        /*
             Note:
             Calculating the balance factor and height each time they are needed is less efficient than
             simply storing the height and balance factor as fields within each tree node (as some
             implementations of the AVLTree do). However, although it is inefficient, it is easier to implement.
         */
        return leftNode.getHeight() - rightNode.getHeight();
    }

    @Override
    public AVLTree<T> insert(T element) {
        /*
            TODO: Write and or complete your insertion code here
            Note that what each method does is described in its superclass unless edited.
            E.g. what 'insert' does is described in Tree.java.
         */

        // Ensure input is not null.
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");

        AVLTree<T> result;
        if (element.compareTo(value) > 0) {
            // COMPLETE
            result= new AVLTree<>(value, leftNode, rightNode.insert(element));
            return getBalance(result);
        } else if(element.compareTo(value) < 0) {
            result= new AVLTree<>(value, leftNode.insert(element), rightNode);
            return getBalance(result);
        }else {
            return this;
        }
         // Change to return something different
    }

    public AVLTree<T> getBalance(AVLTree<T> node){
        if(node.getBalanceFactor()<-1){
            return node.leftRotate();
        }else if(node.getBalanceFactor()>1){
            return node.rightRotate();
        }else {
            return node;
        }

    }

    /**
     * Conducts a left rotation on the current node.
     *
     * @return the new 'current' or 'top' node after rotation.
     */
    public AVLTree<T> leftRotate() {
        /*
            TODO: Write and or complete this method so that you can conduct a left rotation on the current node.
            This can be quite difficult to get your head around. Try looking for visualisations
            of left rotate if you are confused.

            Note: if this is implemented correctly than the return MUST be an AVL tree. However, the
            rotation may move around EmptyAVL trees. So when moving trees, the type of the trees can
            be 'Tree<T>'. However, the return type should be of AVLTree<T>. To cast the return type into
            AVLTree<T> simply use: (AVLTree<T>).

            If you get an casting exception such as:
            'java.lang.ClassCastException: class AVLTree$EmptyAVL cannot be cast to class AVLTree
            (AVLTree$EmptyAVL and AVLTree are in unnamed module of loader 'app')'
            than something about your code is incorrect!
         */


        // COMPLETE

        // RR
        if(((AVLTree<T>)this.rightNode).getBalanceFactor()<=0){
            Tree<T> newParent = this.rightNode;
            Tree<T> newRightOfCurrent = newParent.leftNode;
            this.rightNode=newRightOfCurrent;
            newParent.leftNode=this;
            return new AVLTree<>(newParent.value,newParent.leftNode,newParent.rightNode);
        }
        // RL
        else {
            Tree<T> B = this.rightNode;
            Tree<T> C = this.rightNode.leftNode;
            this.rightNode=C.leftNode;
            B.leftNode=C.rightNode;
            return new AVLTree<>(C.value,this,B);


        }

        //return null; // Change to return something different
    }

    /**
     * Conducts a right rotation on the current node.
     *
     * @return the new 'current' or 'top' node after rotation.
     */
    public AVLTree<T> rightRotate() {
        /*
            TODO: Write this method so that you can conduct a right rotation on the current node.
            This can be quite difficult to get your head around. Try looking for visualisations
            of right rotate if you are confused.

            Note: if this is implemented correctly than the return MUST be an AVL tree. However, the
            rotation may move around EmptyAVL trees. So when moving trees, the type of the trees can
            be 'Tree<T>'. However, the return type should be of AVLTree<T>. To cast the return type into
            AVLTree<T> simply use: (AVLTree<T>).

            If you get an casting exception such as:
            'java.lang.ClassCastException: class AVLTree$EmptyAVL cannot be cast to class AVLTree
            (AVLTree$EmptyAVL and AVLTree are in unnamed module of loader 'app')'
            than something about your code is incorrect!
         */
        //Tree<T> newParent = this.leftNode;
        // LL
        if(((AVLTree<T>)this.leftNode).getBalanceFactor()>=0){
            Tree<T> newParent = this.leftNode;
            Tree<T> newRightOfCurrent = newParent.rightNode;
            this.leftNode=newRightOfCurrent;
            newParent.rightNode=this;
            return new AVLTree<>(newParent.value,newParent.leftNode,newParent.rightNode);
        }
        // LR
        else {
            Tree<T> newLeft = this.leftNode;
            Tree<T> newParent = this.leftNode.rightNode;
            this.leftNode=newParent.rightNode;
            newLeft.rightNode=newParent.leftNode;
            return new AVLTree<>(newParent.value,newLeft,this);
        }

        //return null; // Change to return something different
    }

    /**
     * Note that this is not within a file of its own... WHY?
     * The answer is: this is just a design decision. 'insert' here will return something specific
     * to the parent class inheriting Tree from BinarySearchTree. In this case an AVL tree.
     */
    public static class EmptyAVL<T extends Comparable<T>> extends EmptyTree<T> {
        @Override
        public Tree<T> insert(T element) {
            // The creation of a new Tree, hence, return tree.
            return new AVLTree<T>(element);
        }
    }
}
