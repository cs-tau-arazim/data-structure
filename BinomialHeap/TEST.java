import java.util.Arrays;
import java.util.Random;

public class TEST {
	public static void main(String[]args) {

		
        int[] vals = createValues(5);
        BinomialHeap heap1 = new BinomialHeap();
    	System.out.println(heap1);

        for (int v : vals) {
            heap1.insert(v);
        	System.out.println(heap1);
        	
            if(!heap1.isValid()) {
            	System.out.println("ERROR INSERT");
            }
            else
            	System.out.println("SUCCESS INSERT");
            	
        }
        
        Arrays.sort(vals);
        for (int v : vals) {
          //  if (heap1.findMin() != v) {
               // setFailed("min is "+v+" but findMin() says "+
               //         heap1.findMin());
              //  break;
            }
            heap1.deleteMin();
            if(!heap1.isValid()) {
            	System.out.println("ERROR DELETEMIN");
            }
            
        
	}

    private static int[] createValues(int n) {
        int[] values = new int[n];
        int maxValue = n * 10;
        Random randomGenerator = new Random();

        for (int i = 0; i < n; ++i){
            while (true) {
                int j, randInt = randomGenerator.nextInt(maxValue);

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
