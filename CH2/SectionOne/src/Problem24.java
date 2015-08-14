import java.util.Arrays;

import sorts.*;

public class Problem24 {
	
    public static void main(String[] args) { 
        String alg1 = "InsertionX"; 
        String alg2 = "Insertion";
        int N = Integer.parseInt(args[0]); 
        int T = Integer.parseInt(args[1]); 
        double time1 = SortCompare.timeRandomInput(alg1, N, T); // Total for alg1. 
        double time2 = SortCompare.timeRandomInput(alg2, N, T); // Total for alg2. 
        StdOut.printf("For %d random Doubles\n    %s is", N, alg1); 
        StdOut.printf(" %.3f times faster than %s\n", time2/time1, alg2);
    } 
    
    public static void test() {
    	Double[] a = {4.,3.,6.,5.,2.,1.};
    	InsertionX.sort(a);
    	System.out.println(Arrays.toString(a)); 	
    }
}
