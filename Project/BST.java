public class BST {
    static class Node {
        //instance variable of Node class
        public Word key;
        public Node left;
        public Node right;

        //constructor
        public Node(Word data){
            key = data;
            left = null;
            right = null;
        }
    }
    
    // instance variable of binary tree class
    public Node root;

    // constructor for initialise the root to null BYDEFAULT
    public BST() {
        root = null;
    }

    
    // insert method to insert the new Date
    public void insert(Word key) {
        this.root = insert(root, key);
    }

    public Node insert(Node root, Word key) {
        // Base Case: root is null or not
        if (root == null) {
            // Insert the new data, if root is null.
            root = new Node(key);
            // return the current root to his sub tree
            return root;
        }
        // Here checking for root data is greater or equal to newData or not
        else if (key.compareTo(root.key) < 0) {
            // if current root data is greater than the new data then now process the left sub-tree
            root.left = insert(root.left, key);
        } else {
            // if current root data is less than the new data then now process the right sub-tree
            root.right = insert(root.right, key);
        }
        return root;
    }

    void delete(Word key) { 
        root = deleteHelper(root, key); 
    } 

    Node deleteHelper(Node root, Word key)  { 
        if (root == null)  return root;  //check the base case
        if (key.compareTo(root.key)<0) 
            root.left = deleteHelper(root.left, key); 
        else if (key.compareTo(root.key)>0)
            root.right = deleteHelper(root.right, key); 
        else  { 
            if (root.left == null) 
                return root.right; 
            else if (root.right == null) 
                return root.left; 

            root.key = findMinimum(root.right); 
            root.right = deleteHelper(root.right, root.key); 
        } 
        return root; 
    } 
    // method to get minimum value in the binary search tree
    // we are assured the minimum value is present is in root data if root is null otherwise
    // it is in left subtree of the binary search tree

    public Word findMinimum(Node root) {
        Word min = root.key; 
        while (root.left != null)  { 
            min = root.left.key; 
            root = root.left; 
        } 
        return min; 
    }
}


//this java implementation of the bst was done with the help of https://favtutor.com/blogs/binary-search-tree-java