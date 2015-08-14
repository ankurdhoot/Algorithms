package sorts;
import edu.princeton.cs.introcs.*;
public class ShellBars {
	
	public static void sort(double[] a) {
		int N = a.length;
		int k = (int) Math.round(Math.log(N) / Math.log(3));  // number of h-increment values
		initializePlot(N, k);
		int h = 1;
		while (h < N/3) { h = 3*h + 1; }
		show(a, k--, "input");
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
					exch(a, j, j-h);
				}
			}
			show(a, k--, h + "-sorted");
			h = h/3;
		}
	}
	
	private static void initializePlot(int N, int k) {
		StdDraw.setXscale(-2, N+1);
		StdDraw.setYscale(-1, k+1);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.006);;
	}
	private static boolean less(double i, double j) 
	{ return i < j; }
	
	private static void exch(double[] a, int i, int j) 
	{ double t = a[i]; a[i] = a[j]; a[j] = t; }
	
	private static void show(double[] a, int k, String h) {
		int N = a.length;
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int i = 0; i < N; i++) {
			StdDraw.line(i, k, i, k + a[i] * .6);
		}
		StdDraw.setPenColor(StdDraw.BOOK_RED);
		StdDraw.textLeft(0, k+.6, h);
	}
	
    public static void main(String[] args) {
        //int N = Integer.parseInt(args[0]);
    	int N = 100;
        double[] a = new double[N];
        for (int i = 0; i < N; i++)
          a[i] = Math.random();
        sort(a);
    }
}
