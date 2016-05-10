import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test1 {
	public static void main(String [] args) {
		RBTree_galwiernik_tomsegal t = new RBTree_galwiernik_tomsegal();
		testRank();
		/*
		for (int i =0; i<10; i++) {
			t.insert(i, ""+i);
			System.out.println(t.toString(t.getRoot()));
		}
		System.out.println(t.search(1));
		for (int i =0; i<10; i++) {
			t.delete(i);
			System.out.println(t.toString(t.getRoot()));
		}		


		System.out.println(t.search(0));
		System.out.println(t.search(1));
		System.out.println(t.search(2));
		System.out.println(t.search(6));
		System.out.println(t.search(7));
		int [] arr = t.keysToArray();
		String [] arr2 = t.valuesToArray();
		
		for(int i: arr){
			System.out.print(i +" ");
		}
		System.out.println(" ");

		for(String i: arr2){
			System.out.print(i +" ");
			
		}
	*/
	}
	
	public static void testRank() {
		RBTree_galwiernik_tomsegal rb = new RBTree_galwiernik_tomsegal();
		
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			list.add(i*2);
		}
		
		Collections.shuffle(list);
		for (int i = 0; i < 10; i++) {
			rb.insert(list.get(i), ""+list.get(i));
		}
		System.out.println(rb.toString());
		
		
		for (int i = 0; i < 10; i++) {
			System.out.println(rb.rank(i));
		}
		
	}
}
