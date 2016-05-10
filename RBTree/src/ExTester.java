import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class ExTester {

	public static final int SIZE = 2048;//TODO

	public static int[] sortInts(int[] arr) {
		int[] sortedArr = new int[arr.length];
		for (int j = 0; j < arr.length; j++) {
			sortedArr[j] = arr[j];
		}
		Arrays.sort(sortedArr);
		return sortedArr;
	}

	public static boolean arraysIdentical(int[] arr1, int[] arr2) {
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int j = 0; j < arr1.length; j++) {
			if (arr1[j] != arr2[j]) {
				return false;
			}
		}
		return true;
	}

	public static int[] stringToInt(String[] arr) {
		int[] arr2 = new int[arr.length];
		for (int i = 0 ; i < arr2.length ; i++) {
			arr2[i] = Integer.parseInt(arr[i]);
		}
		return arr2;
	}

	public static int intValue(String str) {
		if (str == null)
			return -1;
		else
			return Integer.parseInt(str);
	}

	public static boolean checkEmpty(RBTree_galwiernik_tomsegal rbTree, MyTree myTree) {
		return rbTree.empty() == myTree.empty();
	}

	public static boolean checkSize(RBTree_galwiernik_tomsegal rbTree, MyTree myTree) {
		return rbTree.size() == myTree.size();
	}

	public static boolean checkMin(RBTree_galwiernik_tomsegal rbTree, MyTree myTree) {
		return intValue(rbTree.min()) == myTree.min();
	}

	public static boolean checkMax(RBTree_galwiernik_tomsegal rbTree, MyTree myTree) {
		return intValue(rbTree.max()) == myTree.max();
	}

	public static boolean checkKeysArray(RBTree_galwiernik_tomsegal rbTree, MyTree myTree) {
		return arraysIdentical(rbTree.keysToArray(),
							   sortInts(myTree.array()));
	}

	public static boolean checkValuesArray(RBTree_galwiernik_tomsegal rbTree, MyTree myTree) {
		return arraysIdentical(stringToInt(rbTree.valuesToArray()),
							   sortInts(myTree.array()));
	}

	public static boolean checkSearch(RBTree_galwiernik_tomsegal rbTree, MyTree myTree) {
		for (int i = 0; i < SIZE; i++) {
			//System.out.println(intValue(rbTree.search(i)) + ", " + i + ", " + myTree.contains(i));
			if ((intValue(rbTree.search(i)) == i) != myTree.contains(i))
			{
				//System.out.println(intValue(rbTree.search(i)) + ", " + i + ", " + myTree.contains(i));
				return false;
			}
		}
		//System.out.println("yes");
		return true;
	}

	public static boolean checkAll(RBTree_galwiernik_tomsegal rbTree, MyTree myTree) {
		return (checkEmpty(rbTree, myTree) &&
				checkSize(rbTree, myTree) &&
				checkMin(rbTree, myTree) &&
				checkMax(rbTree, myTree) &&
				checkKeysArray(rbTree, myTree) &&
				checkValuesArray(rbTree, myTree));
	}

	public static void insert(RBTree_galwiernik_tomsegal rbTree, MyTree myTree, int[] keys) {
		for (int j = 0; j < keys.length; j++) {
			rbTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
		}
	}

	public static int[] generateKeys() {
		int[] arr = new int[SIZE];
	    for (int i = 0; i < SIZE; i++) {
	        arr[i] = i;
	    }
	    Collections.shuffle(Arrays.asList(arr), new Random(539996358));

	    // mid -> min_max sort
	    int tmp[] = Arrays.copyOf(arr, SIZE/4);
	   	Arrays.sort(tmp);
	   	for (int i = 0; i < tmp.length/2; i++) {
	   		arr[2*i] = tmp[tmp.length/2-1-i];
	   		arr[2*i+1] = tmp[tmp.length/2+i];
	   	}

	   	// max -> min sort
	    Arrays.sort(arr, SIZE/4, SIZE/2);
	    for (int i = 0; i < SIZE/8; i++) {
	    	int swapped = arr[SIZE/4+i];
	    	arr[SIZE/4+i] = arr[SIZE/2-1-i];
	    	arr[SIZE/2-1-i] = swapped;
	    }

	    // min -> max sort
	    Arrays.sort(arr, SIZE/2, 3*SIZE/4);

	    return arr;
	}

	public static boolean emptyTreeTest() {
		RBTree_galwiernik_tomsegal rbTree = new RBTree_galwiernik_tomsegal();
		MyTree myTree = new MyTree();

		if (!checkAll(rbTree, myTree))
			return false;

		rbTree.insert(1, "1");
		rbTree.delete(1);

		return checkAll(rbTree, myTree);
	}

	public static boolean insertAndSearchTest() {
		//System.out.println("insertAndSearch start");
		RBTree_galwiernik_tomsegal rbTree = new RBTree_galwiernik_tomsegal();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		
		for (int j = 0; j < keys.length; j++) {
			//System.out.println(j);
			rbTree.insert(keys[j],(""+keys[j]));
			//System.out.println(j + "inserted key rb");
			myTree.insert(keys[j]);
			//System.out.println(j + "inserted key my");
			if (!checkSearch(rbTree, myTree))
			{
				
				return false;
			}
			
		}
		
		return true;
	}

	public static boolean deleteAndSearchTest() {
		RBTree_galwiernik_tomsegal rbTree = new RBTree_galwiernik_tomsegal();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(rbTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			rbTree.delete(keys[j]);
			myTree.delete(keys[j]);
			if (!checkSearch(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean insertAndMinMaxTest() {
		RBTree_galwiernik_tomsegal rbTree = new RBTree_galwiernik_tomsegal();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			rbTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkMin(rbTree, myTree) || !checkMax(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean deleteMinMaxTest() {
		RBTree_galwiernik_tomsegal rbTree = new RBTree_galwiernik_tomsegal();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(rbTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			rbTree.delete(keys[j]);
			myTree.delete(keys[j]);
			if (!checkMin(rbTree, myTree) || !checkMax(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean insertAndSizeEmptyTest() {
		RBTree_galwiernik_tomsegal rbTree = new RBTree_galwiernik_tomsegal();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			rbTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkSize(rbTree, myTree) || !checkEmpty(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean insertAndArraysTest() {
		RBTree_galwiernik_tomsegal rbTree = new RBTree_galwiernik_tomsegal();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			rbTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkKeysArray(rbTree, myTree))
				return false;
			if (!checkValuesArray(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean deleteAndArraysTest() {
		RBTree_galwiernik_tomsegal rbTree = new RBTree_galwiernik_tomsegal();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(rbTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			rbTree.delete(keys[j]);
			myTree.delete(keys[j]);
			if (!checkKeysArray(rbTree, myTree))
				return false;
			if (!checkValuesArray(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean doubleInsertTest() {
		RBTree_galwiernik_tomsegal rbTree = new RBTree_galwiernik_tomsegal();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(rbTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			if (rbTree.insert(keys[j],""+(-1)) != -1)
				return false;
			if (!checkSize(rbTree, myTree))
				return false;
		}
		return checkValuesArray(rbTree, myTree);
	}

	public static boolean doubleDeleteTest() {
		RBTree_galwiernik_tomsegal rbTree = new RBTree_galwiernik_tomsegal();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			if (rbTree.delete(keys[j]) != -1)
				return false;
			rbTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkSize(rbTree, myTree))
				return false;
		}
		return checkValuesArray(rbTree, myTree);
	}

	public static int parseArgs(String[] args) {
		int test_num;

		if (args.length != 1)
			return -1;

		try {
			test_num = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return -1;
		}

		if (test_num < 0 || test_num > 9)
			return -1;

		return test_num;
	}

	public static void main(String[] args) {
		int test_num = parseArgs(args);

		if (test_num == -1) {
			System.out.println("USAGE: java ExTester <test_num>");
			System.exit(1);
		}

		TestRun test_runner = new TestRun(test_num);
		Thread test_thread = new Thread(test_runner);
		test_thread.start();
		try {
			test_thread.join(10000); //TODO
			if (test_thread.isAlive())
				System.out.println("Timeout on Test " + test_num);
		}
		catch (Exception e) {
			System.out.println("Exception on Test " + test_num + " : " + e);
		}
		System.out.println("Result #" + test_num + ": " + test_runner.success);
		System.exit(0);
	}
}