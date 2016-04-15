
public class test1 {
	public static void main(String [] args) {
		RBTree t = new RBTree();

		for (int i =0; i<10; i++) {
			t.insert(i, ""+i);
			System.out.println(t.toString(t.getRoot()));
		}
	
		t.delete(7);
		System.out.println(t.toString(t.getRoot()));
		/*int [] arr= t.keysToArray();
		for (int i : arr)
			System.out.println(i);*/
		//System.out.println(t.toString(t.getRoot()));

		//t.insert(5,"5");
		
		System.out.println(t.search(0));
		System.out.println(t.search(1));
		System.out.println(t.search(2));
		System.out.println(t.search(6));
		System.out.println(t.search(7));
		

	}
}
