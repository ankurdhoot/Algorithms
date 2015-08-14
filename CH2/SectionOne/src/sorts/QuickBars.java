package sorts;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;

public class QuickBars {
	
	private static int row;
	
	public static void sort(double[] a) {
		StdRandom.shuffle(a);
		initializePlot(a.length);
		sort(a, 0, a.length - 1);
		
		assert isSorted(a);
	}
	
	private static boolean isSorted(double[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i- 1])) return false;
		}
		return true;
	}
    private static boolean less(double v, double w) {
        return v < w;
    }
   
	private static void initializePlot(int N) {
		row = N; //worst case scenario
		StdDraw.setPenRadius(.006);
		StdDraw.setXscale(-1, N + 1);
		StdDraw.setYscale(0, row);
	}
	
	private static void sort(double[] a, int lo, int hi) {
		if (hi <= lo) return;
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}
	
	private static int partition(double[] a, int lo, int hi) {
		double key = a[lo];
		int i = lo, j = hi + 1;
		while (true) {
			while (less(a[++i], key)) if (i == hi) break;
			while (less(key, a[--j])) if (j == lo) break;  //necessary check now 
			if (j <= i) break;
			exch(a, i, j);
		}
		exch(a, lo, j);
		row--;
		show(a, lo, hi, j);
		return j;
	}
	
	private static void show(double[] a, int lo, int hi, int j) {
		StdDraw.setPenColor(StdDraw.GRAY);
		for (int k = 0; k < lo; k++) 
			{ StdDraw.line(k, row, k, row + a[k] * .6); }
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int k = lo; k <= hi; k++) 
			{ StdDraw.line(k, row, k, row + a[k] * .6); }
		StdDraw.setPenColor(StdDraw.GRAY);
		for (int k = hi + 1; k < a.length; k++) 
			{ StdDraw.line(k, row, k, row + a[k] * .6); }
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.line(j, row, j, row + a[j] * .6);
	}

	//private static boolean lessequal(double v, double w)
	//{ return (v <= w); }
	
	private static void exch(double[] a, int i, int j) 
	{ double t = a[i]; a[i] = a[j]; a[j] = t; }
	
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        //int N = 10;
        double[] a = new double[N];
        //double[] a = {.5, .5, .5, .5, .5, .5, .5, .5, .5, .5};
        for (int i = 0; i < N; i++)
            a[i] = Math.random();
        sort(a);
    }
}
