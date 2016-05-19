import java.util.Arrays;
import java.util.Random;

public class Measurments {

	public static void main(String[] args) {
		
		int num = 2048;
		//int[] vals = new int[num];
		//for (int i = 0 ; i < vals.length ; i++)
			//vals[i] = i+1;
		
		int[] vals = createValues(num);
		
		//Arrays.sort(vals);
		BinomialHeap heap1 = new BinomialHeap();
		heap1.count =0;
    	//System.out.println(heap1);

		int k =  (int)Math.pow(2, (int)(Math.log(vals.length)/Math.log(2)));
        for (int i = 0 ; i < k - 1; i++) {
            heap1.insert(vals[i]);
        }
        for (int i = k - 1 ; i < vals.length - 2; i+=2) {
        	heap1.delete(vals[k-2]);
        	//heap1.deleteMin();
            heap1.insert(vals[k-2]);
        }
        System.out.println(heap1.count);
        System.out.println(Arrays.toString(heap1.binaryRep()));
        heap1.insert(vals[vals.length - 1]);
        System.out.println(heap1.count);
        System.out.println(Arrays.toString(heap1.binaryRep()));
	}

	
	 private static int[] createValues(int n) {
	        int[] values = new int[n];
	        int maxValue = n * 10;
	        Random randomGenerator = new Random();

	        for (int i = 0; i < n; ++i){
	            while (true) {
	                int j, randInt = randomGenerator.nextInt(maxValue)+10;

	                for (j = 0; j < i && randInt != values[j]; ++j);
	                if (j < i) {
	                    continue;
	                }
	                values[i] = randInt;
	                break;
	            }
	        }

	        return values;
	    }
}
