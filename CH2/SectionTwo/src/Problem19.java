/**
 * Count the number of inversions in linearithmic time
 * Inversions = number of exchanges performed by Insertion Sort
 * @author Ankur Dhoot
 *
 */
public class Problem19 {
	public static int count(double[] a) {
		int N = a.length;
		double[] aux = new double[N];
		double[] b = a.clone();
		double[] c = a.clone();
		int inversions = count(a, aux, 0, N-1);
		int bruteInversions = brute(b);
		int correctInversions = bruteCorrect(c, 0, N-1);
		
		assert isSorted(a);
		assert isSorted(b);
		System.out.println("MergeSort counts inversions = " + inversions);
		System.out.println("InsertionSort counts inversions = " + bruteInversions);
		System.out.println("Correct inversions = " + correctInversions);
		assert inversions == bruteInversions;
		
		return inversions;
	}
	
	private static boolean isSorted(double[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i- 1])) return false;
		}
		return true;
	}
	
	private static int count(double[] a, double[] aux, int lo, int hi) {
		if (hi <= lo) return 0;
		int inversions = 0;
		int mid = lo + (hi - lo) / 2;
		inversions += count(a, aux, lo, mid);
		inversions += count(a, aux, mid+1, hi);
		inversions += merge(a, aux, lo, mid, hi);
		return inversions;
	}
	
	private static boolean less(double v, double w) 
	{ return v < w; }
	
	private static int merge(double[] a, double[] aux, int lo, int mid, int hi) {
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}
		
		int inversions = 0;
		int i = lo, j = mid + 1;
		
		for (int k = lo; k <= hi; k++) {
			if (i > mid)               a[k] = aux[j++];
			else if (j > hi)    	   a[k] = aux[i++];
			else if (less(aux[j], aux[i])) { a[k] = aux[j++]; inversions += (mid - i + 1); }
			else                       { a[k] = aux[i++]; }
		}
		return inversions;
	}
	
	private static int brute(double[] b) {
		int N = b.length;
		int inversions = 0;
		for (int k = 1; k < N; k++) {
			for (int i = k; i > 0 && less(b[i], b[i-1]); i--) {
				exch(b, i, i-1);
				inversions++;
			}
		}
		return inversions;
	}
	
    private static int bruteCorrect(double[] a, int lo, int hi) {
        int inversions = 0;
        for (int i = lo; i <= hi; i++)
            for (int j = i + 1; j <= hi; j++)
                if (less(a[j], a[i])) inversions++;
        return inversions;
    }
	
	private static void exch(double[] a, int i, int j) 
	{ double t = a[i]; a[i] = a[j]; a[j] = t; }
	
    public static void main(String[] args) {
        //int N = Integer.parseInt(args[0]);
        int N = 10;
        double[] a = new double[N];
        for (int i = 0; i < N; i++)
            a[i] = Math.random();
        count(a);
    }
}
