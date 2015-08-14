package sorts;

import edu.princeton.cs.introcs.StdDraw;

public class MergeXBars {
	private final static int CUTOFF = 15;
	private static int row;
	
	public static void sort(double[] a) {
		int N = a.length;
		initializePlot(N);
		double[] aux = a.clone();
		sort(aux, a, 0, N - 1);
		
		assert isSorted(a);
	}
	
	private static void initializePlot(int N) {
		row = (int) Math.floor(N/4.0);  //max number of calls possible with CUTOFF = 15
		StdDraw.setXscale(-1, 2*N + 1);
		StdDraw.setYscale(0, row + 1); //max number of calls to merge is N - 1
		StdDraw.setPenRadius(.006);
	}
	
	public static void show(double[] src, double[] dst, int lo, int hi) {
		int N = src.length;
		for (int k = 0; k < N; k++) {
			if (k < lo || k > hi) {
				StdDraw.setPenColor(StdDraw.GRAY);
			}
			else {
				StdDraw.setPenColor(StdDraw.BLACK);
			}
			StdDraw.line(k, row, k, row + dst[k] * .6);
			if ((hi - lo + 1) < CUTOFF) {
				StdDraw.setPenColor(StdDraw.BOOK_RED);
				StdDraw.textLeft(0, row +.7, "Insertion");
			}
		}
		
		for (int k = N+1, i = 0; k < 2*N + 1; i++, k++) {
			if (k < lo || k > hi) {
				StdDraw.setPenColor(StdDraw.GRAY);
			}
			else {
				StdDraw.setPenColor(StdDraw.BLACK);
			}
			StdDraw.line(k, row, k, row + src[i] * .6);
		}
		
		row--;
	}
	
	//return dst sorted from lo....hi
	private static void sort(double[] src, double[] dst, int lo, int hi) {
		if (hi <= lo) return;
		if ((hi - lo + 1) < CUTOFF) {insertionSort(dst, lo, hi); }  //sorts src from lo .... mid
		else {
			int mid = lo + (hi - lo) / 2;
			sort(dst, src, lo, mid);  //sorts src from lo .... mid
			sort(dst, src, mid+1, hi);  // sorts src from mid + 1 .... hi
			if (!less(src[mid], src[mid+1])) { merge(src, dst, lo, mid, hi); }
			else { System.arraycopy(src, lo, dst, lo, hi - lo + 1); }
		}
		show(src, dst, lo, hi);
	}
	
	private static void insertionSort(double[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++) {
			for (int j = i; j > lo && less(a[j], a[j-1]); j--) {
				exch(a, j, j-1);
			}
		}
	}
	
	private static void merge(double[] src, double[] dst, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		
		for (int k = lo; k <= hi; k++) {
			if (i > mid) 					dst[k] = src[j++];
			else if (j > hi) 				dst[k] = src[i++];
			else if (less(src[j], src[i]))  dst[k] = src[j++];
			else 							dst[k] = src[i++];
		}
	}
	
	private static void exch(double[] a, int i, int j) 
	{ double t = a[i]; a[i] = a[j]; a[j] = t; }

	private static boolean isSorted(double[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i- 1])) return false;
		}
		return true;
	}
	
	private static boolean less(double v, double w) 
	{ return v < w; }
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		double[] a = new double[N];
		for (int i = 0; i < N; i++) {
			a[i] = Math.random();
		}
		sort(a);
	}
}
