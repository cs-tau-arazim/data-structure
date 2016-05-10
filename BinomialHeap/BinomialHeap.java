import java.util.LinkedList;

/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers. Based on
 * exercise from previous semester.
 */
public class BinomialHeap {
	private HeapNode head;
	private HeapNode min;
	private int size;

	public BinomialHeap() {
		this.head = null;
		this.min = null;
		this.size = 0;
	}



	private HeapNode binomialHeapMerge(BinomialHeap h) {
		HeapNode newHead = new HeapNode();
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
		}

		// Iterate remaining nodes
		return newHead;
	}

	

	/**
	 * public int minTreeRank()
	 *
	 * Return the minimum rank of a tree in the heap.
	 * 
	 */
	public int minTreeRank() {
		return 0; // to be replaced by student code
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

		LinkedList<Integer> list = new LinkedList<Integer>();
		while (node.next != null) {
			list.add(node.degree); // Operates at O(1)
			numNodes++;
		}

		boolean[] arr = new boolean[list.get(numNodes - 1)];
		for (int i = 0; i < arr.length; i++) {
			if (list.get(index) == i) {
				arr[i] = true;
				index++;
			} else {
				arr[i] = false;
			}
		}

		return arr; // to be replaced by student code
	}

	/**
	 * public void arrayToHeap()
	 *
	 * Insert the array to the heap. Delete previous elemnts in the heap.
	 * 
	 */
	public void arrayToHeap(int[] array) {
		return; // to be replaced by student code
	}

	/**
	 * public boolean isValid()
	 *
	 * Returns true if and only if the heap is valid.
	 * 
	 */
	public boolean isValid() {
		return false; // should be replaced by student code
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
		return; // should be replaced by student code
	}


	/**
	 * public class HeapNode
	 * 
	 * If you wish to implement classes other than BinomialHeap (for example
	 * HeapNode), do it in this file, not in another file
	 * 
	 */
	
	
   /**
    * public boolean empty()
    *
    * precondition: none
    * 
    * The method returns true if and only if the heap
    * is empty.
    *   
    */
    public boolean empty()
    {
    	return this.head != null;
    }
		
   /**
    * public void insert(int value)
    *
    * Insert value into the heap 
    *
    */
    public void insert(int value) 
    {    
    	HeapNode x = new HeapNode(value);
    	BinomialHeap h = new BinomialHeap();
    	h.head = x;
    	meld(h);
    	if (x.key < this.min.key)
    		this.min = x;
    	this.size += 1;
    }

   /**
    * public void deleteMin()
    *
    * Delete the minimum value
    *
    */
    public void deleteMin()
    {
    	HeapNode x = this.min;
    	if (x == this.head)
    		this.head = x.next;
    	else
    	{
	    	HeapNode prevX = this.head;
	    	while (prevX.next != x)
	    		prevX = prevX.next;
	    	prevX.next = x.next;
    	}
    	BinomialHeap h = new BinomialHeap();
    	HeapNode prev = null;
    	HeapNode current = x.child;
    	HeapNode next = current.next;
    	while(next != null)
    	{
    		current.next = prev;
    		prev = current;
    		current = next;
    		next = next.next;
    	}
    	current.next = prev;
    	h.head = current;
    	meld(h);
    	this.size -= 1;
    }

   /**
    * public int findMin()
    *
    * Return the minimum value
    *
    */
    public int findMin()
    {
    	return this.min.key;
    } 
    
   /**
    * public void meld (BinomialHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
    public void meld (BinomialHeap heap2)
    {
    	if (heap2.min.key < this.min.key)
    		this.min = heap2.min;
    	this.head = binomialHeapMerge(heap2);
    	if (this.head == null)
    		return;
    	HeapNode prevX = null;
    	HeapNode x = this.head;
    	HeapNode nextX = x.next;
    	while (nextX != null)
    	{
    		if (x.degree != nextX.degree || (nextX.next != null && nextX.next.degree == x.degree))
    		{
    			prevX = x;
    			x = nextX;
    		}
    		else if (x.key <= nextX.key)
    		{
    			x.next = nextX.next;
    			binominalLink(x, nextX);
    		}
    		else 
    		{
    			if (prevX == null)
	    		{
	    			this.head = nextX;
	    		}
	    		else
	    		{
	    			prevX.next = nextX;
	    		}
    			binominalLink(x, nextX);
    			x = nextX;
    		}
    	}
    }
    

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *   
    */
    public int size()
    {
    	return this.size; // should be replaced by student code
    }
    

   /**
    * public void decreaseKey(int oldValue, int newValue)
    *
    * If the heap doen't contain an element with value oldValue, don't change the heap.
    * Otherwise decrease the value of the element whose value is oldValue to be newValue. 
    * Assume newValue <= oldValue.
    */
    public void decreaseKey(int oldValue, int newValue) 
    {    
    	//TODO HASH FUNC 
    	HeapNode x = HASHFUNC(oldValue);
    	
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than BinomialHeap
    * (for example HeapNode), do it in this file, not in 
    * another file 
    *  
    */
    public class HeapNode{
    	private int key;
    	// TODO should there by value field?
    	private HeapNode next;
    	private HeapNode parent;
    	private HeapNode child;
    	private int degree;
    	
		public HeapNode(int key) {
			this.parent = null;
	    	this.next = null;
	    	this.child = null;
			this.key = key;
			degree = 0;
		}


		public HeapNode() {
			// TODO Auto-generated constructor stub
		}
	}

    
    private void binominalLink(HeapNode y, HeapNode z)
    {
    	y.parent = z;
    	y.next = z.child;
    	z.child = y;
    	z.degree += 1;
    }
    
    

}
