package sorts;

import edu.princeton.cs.introcs.*;

public class MergeBUBars {
	
	public static void sort(double[] a) {
		int N = a.length;
		initializePlot(N);
		int row = N - 1;
		double[] aux = new double[N];
		for (int sz = 1; sz < N; sz = sz + sz) {
			for (int lo = 0; lo < N - sz; lo += sz + sz) {
				int hi = Math.min(lo + sz + sz - 1,  N - 1);
				int mid = lo + sz - 1;
				merge(a, aux, lo, mid, hi);
				show(a, lo, hi, row--);
			}
		}
		
		assert isSorted(a);
	}
	
	private static void initializePlot(int N) {
		StdDraw.setXscale(-1, N);
		StdDraw.setYscale(0, N);
		StdDraw.setPenRadius(.006);
	}
	
	private static void show(double[] a, int lo, int hi, int row) {
		for (int k = 0; k < a.length; k++) {
			if (k < lo || k > hi) { StdDraw.setPenColor(StdDraw.GRAY); }
			else  				  { StdDraw.setPenColor(StdDraw.BLACK); }
			StdDraw.line(k, row, k, row + a[k] * .6);
		}
	}
	
	private static boolean isSorted(double[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i- 1])) return false;
		}
		return true;
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
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		double[] a = new double[N];
		for (int i = 0; i < N; i++) {
			a[i] = Math.random();
		}
		sort(a);
	}
}
