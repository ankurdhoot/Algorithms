package sorts;
import edu.princeton.cs.introcs.*;

public class InsertionBars {
	
	public static void sort(double[] a) {
		int N = a.length;
		initializePlot(N);
		for (int i = 1; i < N; i++) {
			int j;
			for (j = i; j > 0 && less(a[j], a[j-1]); j--) {
				exch(a, j, j-1);
			}
			show(a, i, j);
		}
	}
	
	private static void initializePlot(int N) {
		StdDraw.setXscale(-1, N+1);
		StdDraw.setYscale(-N, 0);
		StdDraw.setPenRadius(.006);
	}
	
	private static void show(double[] a, int i, int j) {
		int N = a.length;
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		for (int t = 0; t < j; t++)
			{ StdDraw.line(t, -i, t, -i + a[t]*.6); }
		StdDraw.setPenColor(StdDraw.BOOK_RED);
		StdDraw.line(j, -i, j, -i + a[j] * .6);  //object moved
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int t = j+1; t <= i; t++) 
			{ StdDraw.line(t, -i, t, -i + a[t] * .6); }
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		for (int t = i + 1; t < N; t++)
			{ StdDraw.line(t, -i, t, -i + a[t] * .6); }		
	}
	
	private static boolean less(double i, double j) 
	{ return i < j; }
	
	private static void exch(double[] a, int i, int j) 
	{ double t = a[i]; a[i] = a[j]; a[j] = t; }
	
    public static void main(String[] args) {
        //int N = Integer.parseInt(args[0]);
        int N = 10;
        double[] a = new double[N];
        for (int i = 0; i < N; i++)
            a[i] = Math.random();
        sort(a);
    }
}
