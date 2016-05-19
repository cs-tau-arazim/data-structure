import java.util.LinkedList;
import java.util.HashMap;

/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers. Based on
 * exercise from previous semester.
 */
public class BinomialHeap {
	private HashMap<Integer, HeapNode> map;
	private HeapNode head;
	private HeapNode min;
	private int size;

	/**
	 * public BinomialHeap()
	 * 
	 * an empty constructor for BinomialHeap.
	 */
	public BinomialHeap() {
		this.head = null;
		this.min = null;
		this.size = 0;
		map = new HashMap<Integer, HeapNode>();
	}

	/**
	 * public class HeapNode
	 * 
	 * the basic node from which the heap is made
	 */

	public class HeapNode {
		private int key;
		private HeapNode next;
		private HeapNode parent;
		private HeapNode child;
		private int degree;

		/**
		 * constructor using key
		 * 
		 * @param key
		 */
		public HeapNode(int key) {
			this.key = key;
			this.parent = null;
			this.next = null;
			this.child = null;
			degree = 0;
		}

		/**
		 * toString()
		 * 
		 * toString function used for debugging
		 */
		public String toString() {
			int pKey = -1;
			if (this.parent != null)
				pKey = this.parent.key;
			int nKey = -1;
			if (this.next != null)
				nKey = this.next.key;
			return "(key:" + this.key + ", degree: " + this.degree + ", parent key: " + pKey + ", next key: " + nKey
					+ ")";
		}
	}

	/**
	 * public boolean empty()
	 *
	 * precondition: none
	 * 
	 * The method returns true if and only if the heap is empty.
	 * 
	 */
	public boolean empty() {
		return this.head == null;
	}

	/**
	 * public void insert(int value)
	 *
	 * Insert value into the heap
	 *
	 */
	public void insert(int value) {
		// create new heap with only one node, and merge it with existing heap
		HeapNode x = new HeapNode(value);
		BinomialHeap h = new BinomialHeap();
		h.head = x;
		h.min = x;
		h.size = 1;
		h.map.put(value, x);
		meld(h); 
	}


	/**
	 * public void deleteMin()
	 *
	 * Delete the minimum value in the heap.
	 *
	 */
	public void deleteMin() {
		// special case
		if (this.empty())
			return;

		// update fields
		this.map.remove(this.min.key);
		this.size -= 1;
		HeapNode x = this.min;
	
		// remove from root list
		if (x == this.head)
			this.head = x.next;
		else {
			HeapNode prev = this.head;
			HeapNode current = this.head;
			while (current != x) {
				prev = current;
				current = current.next;
			}
			prev.next = x.next;
		}

		// find new minimum for heap
		HeapNode newMin = this.head;
		this.min = newMin;
		while (newMin != null) {
			if (newMin.key < this.min.key)
				this.min = newMin;
			newMin = newMin.next;
		}

		BinomialHeap h = new BinomialHeap();
		// create new heap from the minimum's children
		if (x.child == null)
			return;
		HeapNode prev = null;
		HeapNode current = x.child;
		HeapNode next = current.next;
		h.min = current;
		
		// change children's order
		while (next != null) {
			current.next = prev;
			prev = current;
			current = next;
			next = next.next;
			if (current.key < h.min.key)
				h.min = current;
		}
		current.next = prev;
		h.head = current;
		
		// meld with original heap
		meld(h);

	}
	
	/**
	 * public int findMin()
	 *
	 * Return the minimum value in the heap
	 *
	 */
	public int findMin() {
		return this.min.key;
	}


	/**
	 * public void meld (BinomialHeap heap2)
	 *
	 * Melds the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2) {
		// special cases
		if (this.empty()) {
			this.head = heap2.head;
			this.map = heap2.map;
			this.min = heap2.min;
			this.size += heap2.size;
			return;
		}

		if (heap2.empty()) {
			return;
		}

		// standard case. combine both heaps and all of their attributes.
		this.map.putAll(heap2.map);
		this.size += heap2.size;
		if (heap2.min.key < this.min.key)
			this.min = heap2.min;

		// merge lists of roots
		this.head = binomialHeapMerge(heap2);

		if (this.head == null)
			return;
		HeapNode prevX = null;
		HeapNode x = this.head;
		HeapNode nextX = x.next;

		// fix root list
		while (nextX != null) {
			if (x.degree != nextX.degree || (nextX.next != null && nextX.next.degree == x.degree)) {
				prevX = x;
				x = nextX;
			} else if (x.key <= nextX.key) {
				x.next = nextX.next;
				binominalLink(nextX, x);
			} else {
				if (prevX == null) {
					this.head = nextX;
				} else {
					prevX.next = nextX;
				}
				binominalLink(x, nextX);
				x = nextX;
			}
			nextX = x.next;
		}
	}
	

	/**
	 * HeapNode binomialHeapMerge(BinomialHeap h)
	 * 
	 * the function receives another BinomialHeap. It merges the lists of roots
	 * of both heaps into one list, and returns the node of the beginning of
	 * that list.
	 * 
	 */
	private HeapNode binomialHeapMerge(BinomialHeap h) {
		HeapNode newHead;
		HeapNode node1 = this.head;
		HeapNode node2 = h.head;

		// Check if merge is trivial
		if (node1 == null)
			return node2;
		if (node2 == null)
			return node1;

		// choose new head
		if (node1.degree <= node2.degree) {
			newHead = node1;
			node1 = node1.next;
		} else {
			newHead = node2;
			node2 = node2.next;
		}

		// Iterator
		HeapNode newNode = newHead;

		// Merge lists of current Binomial Heap roots and h's roots
		while (node1 != null && node2 != null) {
			if (node1.degree <= node2.degree) {
				newNode.next = node1;
				node1 = node1.next;
			} else {
				newNode.next = node2;
				node2 = node2.next;
			}
			newNode = newNode.next;
		}

		// Iterate remaining nodes
		while (node1 != null) {
			newNode.next = node1;
			node1 = node1.next;
			newNode = newNode.next;
		}

		while (node2 != null) {
			newNode.next = node2;
			node2 = node2.next;
			newNode = newNode.next;

		}
		return newHead;
	}


	/**
	 * public int minTreeRank()
	 *
	 * Return the minimum rank of a tree in the heap.
	 * 
	 */
	public int minTreeRank() {
		return this.head.degree;
	}

	/**
	 * public boolean[] binaryRep()
	 *
	 * Return an array containing the binary representation of the heap.
	 * 
	 */
	public boolean[] binaryRep() {
		int numNodes = 0;
		int index = 0;
		HeapNode node = this.head;

		// Check if empty
		if (this.head == null) {
			boolean[] arr = new boolean[0];
			return arr;
		}

		// count number of nodes in the root list
		LinkedList<Integer> list = new LinkedList<Integer>();
		while (node != null) {
			list.add(node.degree); // Operates at O(1)
			numNodes++;
			node = node.next;
		}

		// create array
		boolean[] arr = new boolean[list.get(numNodes - 1) + 1];
		for (int i = 0; i < arr.length; i++) {
			// set appropriate values
			if (list.get(index) == i) {
				arr[i] = true;
				index++;
			} else {
				arr[i] = false;
			}
		}
		
		return arr;
	}

	

	/**
	 * public boolean isValid()
	 *
	 * Returns true if and only if the heap is valid.
	 * Meaning, it maintains all of the heap's properties,
	 * and all of it's trees maintain the binomial tree properties.
	 * 
	 */
	public boolean isValid() {
		if (this.empty())
			return true;

		HeapNode node = this.head;
		LinkedList<Integer> list = new LinkedList<Integer>();
		int children;
		int minKey = Integer.MAX_VALUE;
		
		while (node != null) { // for each root
			
			// calculate number of children
			children = numOfChildren(node);
			
			// check if valid tree recursively 
			if (!isValidTree(node, children)) {
				System.out.println("Invalid tree structure");

				return false;
			}

			if (list.contains(children)) { // check if there are two trees of
											// same size
				System.out.println("REPEATING tree structure");

				return false;
			}
			list.add(children);
			if (node.key < minKey)
				minKey = node.key;
			node = node.next;
		}
		if (minKey != min.key) {
			System.out.println("Invalid MINIMUM structure");

			return false; // check if the minimum really is the minimum key
		}
		// calculate number of nodes.
		// if the code made it this far, then all trees are legal
		// and each k-degree tree has exactly 2^k nodes.
		double sum = 0;
		for (int i : list) {
			sum += Math.pow(2, i);
		}
		if ((int) sum != this.size)
			return false;

		return true;
	}

	/**
	 * private int numOfChildren()
	 *
	 * Returns number of children of node. Helper function for isValid.
	 * theoretically, numOfChildren(node) == node.degree for every node in the
	 * heap.
	 * 
	 */
	private int numOfChildren(HeapNode node) {
		HeapNode child = node.child;
		int count = 0;
		// calculate number of children
		while (child != null) {
			count++;
			child = child.next;
		}
		return count;
	}

	/**
	 * boolean isValidTree(HeapNode node, int degree)
	 * given a root HeapNode, and the degree it *should* have, the function
	 * checks recursively that the tree is indeed a binomial tree of given degree.
	 * 
	 */
	private boolean isValidTree(HeapNode node, int degree) {
		HeapNode child = node.child;
		int k = degree;

		while (child != null) {
			k--;
			if (child.key < node.key) // checking heap rule
				return false;
			if (!isValidTree(child, k)) // check valid son
				return false;
			child = child.next;
		}
		if (k > 0)
			return false; // not enough children
		return true;
	}

	
	/**
	 * public void arrayToHeap()
	 *
	 * Insert the array to the heap. Delete previous elemnts in the heap.
	 * 
	 */
	public void arrayToHeap(int[] array) {
		BinomialHeap newHeap = new BinomialHeap();
		for (int i = 0; i < array.length; i++) {
			newHeap.insert(array[i]);
		}
		this.head = newHeap.head;
		this.min = newHeap.min;
		this.size = newHeap.size;
		this.map = newHeap.map;
	}

	/**
	 * public void delete(int value)
	 *
	 * Delete the element with the given value from the heap, if such an element
	 * exists. If the heap doen't contain an element with the given value, don't
	 * change the heap.
	 *
	 */
	public void delete(int value) {
		// System.out.println(this);
		this.decreaseKey(value, Integer.MIN_VALUE);
		// System.out.println(value);
		// System.out.println(this);
		this.deleteMin();
	}



	/**
	 * public int size()
	 *
	 * Return the number of elements in the heap
	 * 
	 */
	public int size() {
		return this.size; // should be replaced by student code
	}

	/**
	 * public void decreaseKey(int oldValue, int newValue)
	 *
	 * If the heap doen't contain an element with value oldValue, don't change
	 * the heap. Otherwise decrease the value of the element whose value is
	 * oldValue to be newValue. Assume newValue <= oldValue.
	 */
	public void decreaseKey(int oldValue, int newValue) {
		HeapNode x = map.get(oldValue);
		if (x == null)
			return;
		x.key = newValue;
		map.remove(oldValue);
		map.put(newValue, x);
		if (newValue < this.min.key)
			this.min = x;
		HeapNode y = x;
		HeapNode z = y.parent;
		while (z != null && y.key < z.key) {
			int temp = y.key;
			y.key = z.key;
			z.key = temp;
			map.put(y.key, y);
			map.put(z.key, z);
			y = z;
			z = y.parent;
		}
		if (newValue < this.min.key)
			this.min = y;
	}

	private void binominalLink(HeapNode y, HeapNode z) {
		y.parent = z;
		y.next = z.child;
		z.child = y;
		z.degree += 1;
	}

	public String toString() {
		if (empty())
			return "empty";
		String str = "head key: " + this.head.key + "\n";
		str += "min key: " + this.min.key + "\n";
		str += "size: " + this.size + "\n";
		str += map.toString();
		return str;
	}

}
