import sorts.SortCompare;

public class DoublingRatioTest {
	
	public static void main(String[] args) {
		String alg1 = args[0];
		String alg2 = args[1];
		int T = Integer.parseInt(args[2]);  //number of times to repeat on each trial
		int start = Integer.parseInt(args[3]);
		int numDoubles = Integer.parseInt(args[4]); //number of times to double starting at start
		System.out.printf("%s is x times faster than %s \n", alg1, alg2);
		for (int N = start; numDoubles > 0; N *= 2, numDoubles--) {
			double time1 = SortCompare.timeRandomInput(alg1, N, T);
			double time2 = SortCompare.timeRandomInput(alg2, N, T);
			System.out.printf("%4d       %-4.2f  \n", N, time2/time1);
		}
	}
}
