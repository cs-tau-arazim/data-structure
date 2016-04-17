/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with non-negative, distinct integer
 * keys and values
 *
 */

public class RBTree {

	/**
	 * public class RBNode
	 */


	private final RBNode nil = new RBNode(Integer.MAX_VALUE, null, null);


	private RBNode root;
	private int size;

	/**
	 * public RBTree()
	 * 
	 * Builds an empty red-black tree.
	 */
	public RBTree() {
		this.size = 0;
		this.root = nil;
		this.nil.isRed = false;
		this.nil.right = nil;
		this.nil.left = nil;
		nil.parent = nil;

	}

	public class RBNode {

		// constructor for RBNode.
		public RBNode(int key, String value, RBNode parent) {
			this.isRed = true;
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.left = nil;
			this.right = nil;
		}

		private boolean isRed;
		private int key;
		private String value;

		private RBNode parent;
		private RBNode left;
		private RBNode right;

		// getters for some of RBNode's fields.
		public boolean isRed() {
			return isRed;
		}

		public int getKey() {
			return key;
		}

		public RBNode getLeft() {
			return left;
		}

		public RBNode getRight() {
			return right;
		}

	}

	// helper functions- similar to RBNode's isRed, right, left but helps sometimes.
	private boolean isRed(RBNode z)
	{
		if (z == nil)

			return false;
		else
			return z.isRed;
	}


	private RBNode right(RBNode z)
	{
		if (z == nil)
			return nil;
		else
			return z.right;
	}


	private RBNode left(RBNode z)
	{
		if (z == nil)

			return nil;
		else
			return z.left;
	}

	/**
	 * public RBNode getRoot()
	 *
	 * returns the root of the red black tree
	 *
	 */
	public RBNode getRoot() {
		return this.root;
	}

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() {
		return (getRoot() == nil);
	}

	/**
	 * public String treeSearch(RBNode node, int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null. same like search but starts with a given node.
	 */
	private String treeSearch(RBNode node, int k) {
		while (node != nil && k != node.key) {
			if (k < node.key)
				node = node.left;

			else
				node = node.right;
		}
		return node.value;
	}
	
	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k) {
		return treeSearch(root, k);
	}

	/**
	 * private int insert(RBNode z)
	 *
	 * inserts a node to the red black tree. the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were necessary. returns -1 if an item
	 * with key k already exists in the tree.
	 */
	private int insert(RBNode z) {
		RBNode y = nil;
		RBNode x = root;
		while (x != nil) { // find where to insert
			y = x;
			if (z.key < x.key)
				x = x.left;
			else
				x = x.right;
		}
		z.parent = y;
		if (y == nil) // check if insertion is at root
		{
			root = z;
			nil.left = z;
		}
		else if (z.key < y.key)
			y.left = z;
		else
			y.right = z;

		z.left = nil;
		z.right = nil;
		z.isRed = true;

		this.size += 1; // increment size

		
		return insertFixup(z); // fix tree properties
	}

	/**
	 * public int insert(int k, String v)
	 *
	 * inserts an item with key k and value v to the red black tree. the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were necessary. returns -1 if an item
	 * with key k already exists in the tree.
	 */
	public int insert(int k, String v) {
		RBNode z = new RBNode(k, v, this.nil);
		if (k == treePosition(k).key)
			return -1;
		return insert(z);
	}

	/**
	 * public int delete(int k)
	 * deletes an item with key k from the binary
	 * tree, if it is there; the tree must remain valid (keep its invariants).
	 * returns the number of color switches, or 0 if no color switches were
	 * needed. returns -1 if an item with key k was not found in the tree.
	 */
	public int delete(int k) {
		RBNode z = treePosition(k);
		RBNode y = z;
		RBNode x; // the node that could cause trouble later in fixup
		if (z == nil) { // node doesn't exists in tree
			return -1;

		}
		boolean yColor = y.isRed;
		if (z.left == nil) { // just "skip" z
			x = z.right;
			transplant(z, z.right);
		}
		else if (z.right == nil) { // "skip" z
			x = z.left;
			transplant(z, z.left);
		}
		else // z has 2 children, replace with succesor (which has only 1 child) and delete.
		{
			y = succesor(y);
			yColor = y.isRed;
			x = y.right;
			if (y.parent == z)
				x.parent = y;
			else {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z,y);
			y.left = z.left;
			y.left.parent = y;
			y.isRed = z.isRed;
		}
		size -= 1;
		if (yColor == false) // if the deleted node was black we need to fix
			return deleteFixup(x);
		return 0;
	}

	/**
	 * public int deleteFixup(RBNode node)
	 * 
	 * fixes the Red-Black Tree in terms of making sure all of its properties
	 * remains- black length, etc. returns number of color changes made.
	 */
	public int deleteFixup(RBNode x) {
		int changes = 0;
		if (x == nil)
			return 0;

		while (x != getRoot() && !isRed(x)) {
			if (x == left(x.parent)) {
				RBNode w = right(x.parent);
				if (isRed(w)) { // case 1
					w.isRed = false;
					if (!x.parent.isRed) // check color change
						changes += 1;
					x.parent.isRed = true;
					leftRotate(x.parent);
					w = right(x.parent);
					changes += 1;
				}
				if (!isRed(left(w)) && !isRed(right(w))) { // case 2
					w.isRed = true;
					x = x.parent;
					changes += 1;
				} else {
					if (!isRed(right(w))) { // case 3
						left(w).isRed = false;
						w.isRed = true;
						rightRotate(w);
						w = x.parent.right;
						changes += 2;
					} // case 4
					if (w.isRed != isRed(x.parent)) // check color change
						changes += 1;
					w.isRed = isRed(x.parent);
					if (x.parent.isRed) // check color change
						changes += 1;
					x.parent.isRed = false;
					if (right(w).isRed) // check color change
						changes += 1;
					right(w).isRed = false;
					leftRotate(x.parent);
					x = getRoot();
				}

			}
			else if (x == right(x.parent)) {
				RBNode w = left(x.parent);
				if (isRed(w)) { // case 1
					w.isRed = false;
					if (!x.parent.isRed) // check color change
						changes += 1;
					x.parent.isRed = true;
					rightRotate(x.parent);
					w = left(x.parent);
					changes += 1;
				}
				if (!isRed(right(w)) && !isRed(left(w))) { // case 2
					w.isRed = true;
					x = x.parent;
					changes += 1;
				} else {
					if (!isRed(left(w))) { // case 3

						right(w).isRed = false;
						w.isRed = true;
						leftRotate(w);
						w = x.parent.left;
						changes += 2;
					} // case 4
					if (w.isRed != isRed(x.parent)) // check color change
						changes += 1;
					w.isRed = isRed(x.parent);
					if (x.parent.isRed) // check color change
						changes += 1;
					x.parent.isRed = false;
					if (left(w).isRed) // check color change
						changes += 1;
					left(w).isRed = false;
					rightRotate(x.parent);
					x = getRoot();
				}

			}
			
		}
		if (x.isRed) // check color change
		{
			x.isRed = false;
			changes += 1;
		}
		return changes;
	}

	/**
	 * public String min()
	 *
	 * Returns the value of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min() {
		if (empty())
			return null;
		RBNode current = getRoot();
		while (left(current) != nil) {
			current = left(current);
		}
		return current.value;
	}

	/**
	 * public String max()
	 *
	 * Returns the value of the item with the largest key in the tree, or null
	 * if the tree is empty
	 */
	public String max() {
		if (empty())
			return null;
		RBNode current = getRoot();
		while (right(current) != nil) {
			current = right(current);
		}
		return current.value;
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray() {
		int[] arr = new int[size]; // new array with appropriate size
		int i = 0;
		if (this.empty()) // check if empty
			return arr;

		RBNode node = getRoot();
		while (left(node) != nil) { // go to minimum
			node = left(node);
		}

		while (node != nil) {
			arr[i] = node.key;
			node = succesor(node);
			i++;
		}
		return arr;
	}

	/**
	 * public String[] valuesToArray()
	 *
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray() {
		String[] arr = new String[size]; // new array with appropriate size
		int i = 0;
		if (this.empty()) // check if empty
			return arr;

		RBNode node = getRoot();
		while (left(node) != nil) { // go to minimum
			node = left(node);
		}

		while (node != nil) {
			arr[i] = node.value;
			node = succesor(node);
			i++;
		}
		return arr;
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 */
	public int size() {
		return this.size;
	}

	/**
	 * public int rank(int k)
	 *
	 * Returns the number of nodes in the tree with a key smaller than k.
	 *
	 * precondition: none postcondition: none
	 */
	public int rank(int k) {
		if (this.empty()) // checking "edge" cases
			return 0;

		RBNode node = getRoot();
		RBNode parent = root; // save the parent for later use
		while (node != nil) {
			parent = node;
			if (k < node.key)
				node = node.left;
			else
				node = node.right;
		}
		
		int count = 0;
		if (parent.key < k)
			count += 1;
		

		
		while (parent != nil) { // start counting number of predecessors
			parent = predeccesor(parent);
			if (parent.key < k)
				count++;
		}
		return count; // will always be >= 0
	}

	/**
	 * private RBNode succesor(RBNode node)
	 *
	 * Returns the succesor of node.
	 *
	 * precondition: node != null (can be nil) postcondition: succesor != null
	 */
	private RBNode succesor(RBNode node) {
		RBNode next = node;

		if (right(next) != nil) { // case 1, node has right subtree


			next = right(next);
			while (left(next) != nil)
				next = left(next);
			return next;
		} else { // case 2, node doesn't have right subtree
			RBNode parent = node.parent;
			while (parent != nil && node == right(parent)) {
				node = parent;
				parent = node.parent;
			}
			return parent;
		}
	}

	/**
	 * private RBNode predeccesor(RBNode node)
	 *
	 * Returns the predeccesor of node.
	 *
	 * precondition: node != null (can be nil) postcondition: predeccesor != null
	 */
	private RBNode predeccesor(RBNode node) {
		RBNode next = node;
		if (left(next) != nil) { // case 1, node has left subtree
			next = left(next);
			while (right(next) != nil)
				next = right(next);
			return next;
		} else { // case 2, node doesn't have left subtree
			RBNode parent = node.parent;
			while (parent != nil && node == left(parent)) {
				node = parent;
				parent = node.parent;
			}
			return parent;
		}
	}

	/**
	 * private void leftChild(RBNode x, RBNode y)
	 *
	 * sets y to be the left child of x, x perent of y
	 *
	 * precondition: x,y != null (can be nil) postcondition: x.left = y
	 */
	private void leftChild(RBNode x, RBNode y) {
		x.left = y;
		if (y != nil)
			y.parent = x;
	}

	/**
	 * private void rightChild(RBNode x, RBNode y)
	 *
	 * sets y to be the right child of x, x perent of y
	 *
	 * precondition: x,y != null (can be nil) postcondition: x.right = y
	 */
	private void rightChild(RBNode x, RBNode y) {
		x.right = y;
		if (y != nil)
			y.parent = x;
	}

	/**
	 * private void transplant(RBNode x, RBNode y)
	 *
	 * replace the subtree of x by the subtree of y
	 *
	 * precondition: x,y != null (can be nil) postcondition: none
	 */
	private void transplant(RBNode x, RBNode y) {
		if (x.parent == nil)
		{
			root = y;
			nil.left = root;
		}
		else if (x == x.parent.left)
		{
			x.parent.left = y;
		}
		else
		{
			x.parent.right = y;
		}
		y.parent = x.parent;
	}

	private void replace(RBNode x, RBNode y) {
		transplant(x, y);
		leftChild(y, left(x));
		rightChild(y, right(x));
	}

	private void leftRotate(RBNode x) {

		RBNode y = x.right;
		x.right = y.left;
		if (y.left != nil)
			y.left.parent = x;
		y.parent = x.parent;
		if (x.parent == nil)
		{
			root = y;
			nil.left = root;
		}
		else if (x == x.parent.left)
			x.parent.left = y;
		else
			x.parent.right = y;
		y.left = x;
		x.parent = y;
	}

	private void rightRotate(RBNode x) {
		RBNode y = x.left;
		x.left = y.right;
		if (y.right != nil)
			y.right.parent = x;
		y.parent = x.parent;
		if (x.parent == nil)
		{
			root = y;
			nil.left = root;
		}
		else if (x == x.parent.right)
			x.parent.right = y;
		else
			x.parent.left = y;
		y.right = x;
		x.parent = y;

	}



	private RBNode treePosition(int k) {
		RBNode node = root;
		while (node != nil && k != node.key) {
			if (k < node.key)
				node = node.left;

			else
				node = node.right;
		}
		return node;
	}
	
	
	private int insertFixup(RBNode z) {
		//System.out.println("insert fixup");
		int changes = 0;

		while (z.parent.isRed) {
			//System.out.println("still red");
			if (z.parent == z.parent.parent.left) {
				RBNode y = z.parent.parent.right;
				if (y.isRed) { // case 1

					z.parent.isRed = false;
					y.isRed = false;
					if (!z.parent.parent.isRed) // check color change
						changes += 1;
					z.parent.parent.isRed = true;
					z = z.parent.parent;
					changes += 2;
				} else {
					if (z == z.parent.right) { // case 2
						z = z.parent;
						leftRotate(z);
					}
					z.parent.isRed = false; // case 3
					if (!z.parent.parent.isRed) // check color change
						changes += 1;
					z.parent.parent.isRed = true;
					rightRotate(z.parent.parent);
					changes += 1;
				}

			}
			else if (z.parent == z.parent.parent.right) { // Symmetrical to other side
				RBNode y = z.parent.parent.left;
				if (y.isRed) { // case 1

					z.parent.isRed = false;
					y.isRed = false;
					if (!z.parent.parent.isRed) // check color change
						changes += 1;
					z.parent.parent.isRed = true;
					z = z.parent.parent;
					changes += 2;
				} else {
					if (z == z.parent.left) { // case 2
						z = z.parent;
						rightRotate(z);
					}
					z.parent.isRed = false; // case 3
					if (!z.parent.parent.isRed) // check color change
						changes += 1;
					z.parent.parent.isRed = true;
					leftRotate(z.parent.parent);
					changes += 1;
				}
			}
			
		}
		
		if (root.isRed)
		{
			root.isRed = false;
			changes += 1;
		}
		

		return changes;
	}

	public String toString() {
		return toString(root);
	}
	private String toString(RBNode n) {
		String c;
		if (n == nil)
			return "";
		if (n.isRed)
			c = "R";
		else
			c = "B";

		return " (" + toString(n.left) + n.key + c + toString(n.right) + ") ";
	}
}
