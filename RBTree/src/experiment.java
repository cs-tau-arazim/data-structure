import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class experiment {

	static int num = 10000;

	public static void main(String[] args) {
		System.out.println("number\toperations\tinsert avg\tdelete avg");
		for (int i = 1; i < 11; i++)
			exp(i);

	}

	public static void exp(int j) {
		RBTree_galwiernik_tomsegal rb = new RBTree_galwiernik_tomsegal();
		int insChanges = 0;
		int delChanges = 0;
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < 10*j*num; i++) {
			list.add(new Integer(i));
		}
		
		Collections.shuffle(list);
		for (int i = 0; i < j*num; i++) {
			insChanges += rb.insert(list.get(i), ""+list.get(i));
		}
		list = (list.subList(0, j*num));
		
		Collections.sort(list);
		for (int i = 0; i < j*num; i++) {
			//System.out.println(list.get(i));
		}
		
		
		
		for (int i = 0; i < j*num; i++) {
			delChanges += rb.delete(list.get(i));
		}
		//System.out.println(rb.toString());
		
		System.out.println(j + "\t" + j*num + "\t\t" + Math.round((double)insChanges/(j*num)*10000)/(double)10000 + "\t\t" + Math.round((double)delChanges/(j*num)*10000)/(double)10000);
	}

}
