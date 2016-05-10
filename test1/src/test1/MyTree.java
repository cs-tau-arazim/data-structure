package test1;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

class MyTree {
	private TreeSet<Integer> set;

	MyTree() {
		this.set = new TreeSet<Integer>();
	}

	public int size() {
		return this.set.size();
	}

	public boolean empty() {
		return this.set.isEmpty();
	}

	public void insert(int v) {
		this.set.add((Integer)v);
	}

	public void delete(int v) {
		this.set.remove((Integer)v);
	}

	public int min() {
		if (this.empty())
			return -1;
		return (int)(this.set.first());
	}

	public int max() {
		if (this.empty())
			return -1;
		return (int)(this.set.last());
	}
	
	public int rank(int k) {
		if (this.empty())
			return 0;
		return (int)(this.set.headSet((Integer)k,false).size());
	}

	public boolean contains(int v) {
		return this.set.contains((Integer)v);
	}

	public int[] array() {
		int[] arr = new int[this.size()];
		Iterator<Integer> itr = this.set.iterator();
		for (int i = 0; i < this.size(); i++)
			arr[i] = (int)(itr.next());
		return arr;
	}
}

