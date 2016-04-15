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

	private RBNode root;
	private int size;

	public RBTree() {
		this.size = 0;
		this.root = null;
	}

	public static class RBNode {

		public RBNode(int key, String value, RBNode parent) {
			this.isRed = true;
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.left = null;
			this.right = null;
		}

		private boolean isRed;
		private int key;
		private String value;

		private RBNode parent;
		private RBNode left;
		private RBNode right;

		public boolean isRed() {
			return isRed;
		}

		public int getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public RBNode getParent() {
			return parent;
		}

		public RBNode getLeft() {
			return left;
		}

		public RBNode getRight() {
			return right;
		}

		
		
		
		/*
		 * public void setRed(boolean isRed) { this.isRed = isRed; } public void
		 * setKey(int key) { this.key = key; } public void setValue(int value) {
		 * this.value = value; } public void setParent(RBNode parent) {
		 * this.parent = parent; } public void setLeft(RBNode left) { this.left
		 * = left; } public void setRight(RBNode right) { this.right = right; }
		 */

	}
	
	private boolean isRed(RBNode z)
	{
		if (z == null)
			return false;
		else
			return z.isRed;
	}
	private RBNode right(RBNode z)
	{
		if (z == null)
			return null;
		else
			return z.right;
	}
	private RBNode left(RBNode z)
	{
		if (z == null)
			return null;
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
		return (this.root == null);
	}

	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */

	public String search(int k) {
		if (this.empty())
			return null;
		RBNode node = this.root;

		while (node != (null)) {
			if (node.key == k)
				return node.value;
			else if (node.key > k)
				node = left(node);
			else
				node = right(node);
		}
		return null;
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
		if (empty()) {
			this.root = new RBNode(k, v, null);
			this.root.isRed = false;
			this.size += 1;
			return 0; // TODO need to check if 0 or 1
		}
		RBNode y = treePosition(this.root, k);
		if (y.key == k)
			return -1;

		RBNode z = new RBNode(k, v, y);

		if (k < y.key)
			y.left = z;
		else
			y.right = z;

		this.size += 1;
		return insertFixup(z);

	}

	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were needed. returns -1 if an item
	 * with key k was not found in the tree.
	 */
	public int delete(int k) {
		if (empty())
			return -1;
		RBNode z = treePosition(this.root, k);
		RBNode x;

		if (z.key == k)
			return -1;

		boolean initColor = isRed(z);

		if (left(z) == null) {
			x = right(z);
			transplant(z, right(z));
		} else if (right(z) == null) {
			x = left(z);
			transplant(z, left(z));
		} else {
			RBNode y = succesor(z);
			initColor = isRed(y);
			replace(y, z);
			y.isRed = isRed(z);
			transplant(z, right(z));
			x = right(z);
		}
		if (initColor)
			return 0;
		else
			return deleteFixup(x);

	}

	/**
	 * public int deleteFixup(RBNode node)
	 * 
	 * fixes the Red-Black Tree in terms of making sure all of its properties
	 * remains- black length, etc. returns number of color changes made.
	 */
	public int deleteFixup(RBNode node) {
		int changes = 0;
		if (node == null)
			return 0;

		while (node != root && !isRed(node)) {
			if (node == left(node.parent)) {
				RBNode w = right(node.parent);
				if (w != null && isRed(w)) { // case 1
					w.isRed = false;
					node.parent.isRed = true;
					leftRotate(node.parent);
					w = right(node.parent);
					changes += 2;
				}
				if (!isRed(left(w)) && !isRed(right(w))) { // case 2
					w.isRed = true;
					node = node.parent;
					changes += 1;
				} else {
					if (!isRed(right(w))) { // case 3

						left(w).isRed = false;
						w.isRed = true;
						rightRotate(w);
						changes += 2;
					} // case 4
					w.isRed = isRed(node.parent);
					node.parent.isRed = false;
					right(w).isRed = false;
					leftRotate(node.parent);
					node = root;
					changes += 2;
				}

			}
			if (node == right(node.parent)) {
				RBNode w = left(node.parent);
				if (w != null && isRed(w)) { // case 1
					w.isRed = false;
					node.parent.isRed = true;
					rightRotate(node.parent);
					w = left(node.parent);
					changes += 2;
				}
				if (!isRed(right(w)) && !isRed(left(w))) { // case 2
					w.isRed = true;
					node = node.parent;
					changes += 1;
				} else {
					if (!isRed(left(w))) { // case 3

						right(w).isRed = false;
						w.isRed = true;
						leftRotate(w);
						changes += 2;
					} // case 4
					w.isRed = isRed(node.parent);
					node.parent.isRed = false;
					left(w).isRed = false;
					rightRotate(node.parent);
					node = root;
					changes += 2;
				}
			}
		}
		node.isRed = false;
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
		RBNode current = this.root;
		while (left(current) != null) {
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
		RBNode current = this.root;
		while (right(current) != null) {
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

		RBNode node = root;
		while (left(node) != null) { // go to minimum
			node = left(node);
		}

		while (node != null) {
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

		RBNode node = root;
		while (left(node) != null) { // go to minimum
			node = left(node);
		}

		while (node != null) {
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

		RBNode node = this.root;
		RBNode parent = this.root; // save the parent for later use
		while (node != null) {
			if (node.key == k) {
				parent = node;
				break;
			} else if (node.key > k) {
				parent = node;
				node = left(node);
			} else {
				parent = node;
				node = right(node);
			}
		}
		int count = -1;
		if (node != null) {
			node = parent; // saved parent so we can go back if k isn't in the
							// tree
			if (node.key < k)
				count += 1;
		}

		while (node != null) { // start counting number of predecessors
			node = predeccesor(node);
			count++;
		}
		return count; // will always be >= 0
	}

	private RBNode succesor(RBNode node) {
		RBNode next = node;
		if (right(next) != null) { // case 1, node has right subtree
			next = right(next);
			while (left(next) != null)
				next = left(next);
			return next;
		} else { // case 2, node doesn't have right subtree
			RBNode parent = node.parent;
			while (parent != null && node == right(parent)) {
				node = parent;
				parent = node.parent;
			}
			return parent;
		}
	}

	private RBNode predeccesor(RBNode node) {
		RBNode next = node;
		if (left(next) != null) { // case 1, node has left subtree
			next = left(next);
			while (right(next) != null)
				next = right(next);
			return next;
		} else { // case 2, node doesn't have left subtree
			RBNode parent = node.parent;
			while (parent != null && node == left(parent)) {
				node = parent;
				parent = node.parent;
			}
			return parent;
		}
	}

	private void leftChild(RBNode x, RBNode y) {
		x.left = y;
		if (y != null)
			y.parent = x;
	}

	private void rightChild(RBNode x, RBNode y) {
		x.right = y;
		if (y != null)
			y.parent = x;
	}

	private void transplant(RBNode x, RBNode y) {
		if (x == left(x.parent)) {
			leftChild(x.parent, y);
		} else {

			rightChild(x.parent, y);

		}
	}

	private void replace(RBNode x, RBNode y) {
		transplant(x, y);
		leftChild(y, left(x));
		rightChild(y, right(x));
	}

	private void leftRotate(RBNode x) {
		RBNode y = right(x);
		transplant(x, y);
		rightChild(x, left(y));
		leftChild(y, x);
	}

	private void rightRotate(RBNode x) {
		RBNode y = left(x);
		transplant(x, y);
		leftChild(x, right(y));
		rightChild(y, x);
	}

	private RBNode treePosition(RBNode x, int k) {
		RBNode y = null;
		while (x != null) {
			y = x;
			if (k == x.key)
				return x;
			else if (k < x.key)
				x = left(x);
			else
				x = right(x);
		}
		return y;
	}

	private int insertFixup(RBNode z) // TODO check this method
	{
		int changes = 0;
		while (z.parent != null && isRed(z.parent)) {
			if (z.parent == left(z.parent.parent)) { // left side
				RBNode uncle = right(z.parent.parent);
				if (uncle != null && isRed(uncle)) // case 1
				{
					z.parent.isRed = false;
					uncle.isRed = false;
					z.parent.parent.isRed = true;
					z = z.parent.parent;
					changes += 3; // parent, uncle and grand-parent changed
									// color

				} else {
					if (z == right(z.parent)) // case 2
					{
						z = z.parent;
						leftRotate(z);
					}
					// case 3
					z.parent.isRed = false;
					z.parent.parent.isRed = true;
					rightRotate(z.parent.parent);
					changes += 2; // parent and grand-parent changed color
				}
			} else { // right side
				RBNode uncle = left(z.parent.parent);
				if (uncle != null && isRed(uncle)) // case 1
				{
					z.parent.isRed = false;
					uncle.isRed = false;
					z.parent.parent.isRed = true;
					z = z.parent.parent;
					changes += 3; // parent, uncle and grand-parent changed
									// color

				} else {
					if (z == left(z.parent)) // case 2
					{
						
						z = z.parent;
						leftRotate(z);
					}
					// case 3
					z.parent.isRed = false;
					z.parent.parent.isRed = true;
					rightRotate(z.parent.parent);
					changes += 2; // parent and grand-parent changed color
				}
			}
		}

		this.root.isRed = false;
		return changes;
	}
}
