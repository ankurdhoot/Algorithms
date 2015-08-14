package sorts;
import edu.princeton.cs.introcs.*;

public class MergeBars {
	
	private static int row;
	
	private static boolean isSorted(double[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i- 1])) return false;
		}
		return true;
	}
	
	public static void sort(double[] a) {
		initializePlot(a.length);
		double[] aux = new double[a.length];
		sort(a, aux, 0, a.length - 1);  //bottom row should be completely sorted
		
		assert isSorted(a);
	}
	
	private static void initializePlot(int N) {
		row = N - 1;
		StdDraw.setXscale(-1, N);
		StdDraw.setYscale(0, N); //max number of calls to merge is N - 1
		StdDraw.setPenRadius(.006);
	}
	private static void sort(double[] a, double[] aux, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
		
		//System.out.printf("Calling show with lo = %d, hi = %d, row = %d \n", lo, hi, row);
		show(a, lo, hi);
	}
	
	private static void merge(double[] a, double[] aux, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}
		
		for (int k = lo; k <= hi; k++) {
			if (i > mid) 					a[k] = aux[j++];
			else if (j > hi) 				a[k] = aux[i++];
			else if (less(aux[j], aux[i]))  a[k] = aux[j++];
			else 							a[k] = aux[i++];
		}
	}
	
	private static boolean less(double v, double w) 
	{ return v < w; }
	
	//assumes all the values are between 0 and 1
	public static void show(double[] a, int lo, int hi) {
		for (int k = 0; k < a.length; k++) {
			if (k < lo || k > hi) {
				StdDraw.setPenColor(StdDraw.GRAY);
			}
			else {
				StdDraw.setPenColor(StdDraw.BLACK);
			}
			StdDraw.line(k, row, k, row + a[k] * .6);
		}
		row--;
	}
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		double[] a = new double[N];
		for (int i = 0; i < N; i++) {
			a[i] = Math.random();
		}
		sort(a);
	}
}
