
public class test1 {
	public static void main(String [] args) {
		RBTree t = new RBTree();

		for (int i =0; i<3; i++) {
			t.insert(i, ""+i);
		}
		System.out.println(t.search(0));
		System.out.println(t.search(3));
		System.out.println(t.search(2));


	}
}
