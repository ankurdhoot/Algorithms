/**
 * Construct a heap first, then sortdown
 * Heap construction takes <= 2N
 * sortdown takes <= 2NlogN
 * Uses integer values to keep track of whether an element has been compared, exchanged, or neither
 * Red color means that element participated in an exchange
 * Black means that element participated in compare, but not exchange
 */
package sorts;

import edu.princeton.cs.introcs.StdDraw;

public class HeapTrace extends ParentSort {
	//need to implement to start at index 0 instead of 1 to work with sortCompare
	private static int row;
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		//first need to construct a heap
		int size = a.length; //size to use for colorKey array
		int N = a.length - 1;
		for (int k = N/2; k >= 1; k--) {
			int[] colorKey = new int[size];
			sink(a, k, N, colorKey);
			show(a, k, N, colorKey);
			row--;
		}
		
		showHeap(a);
		row--;	
		
		while (N > 1) {
			int[] colorKey = new int[size];
			exch(a, 1, N--);
			colorKey[1] += 2;  
			colorKey[N+1] += 2;
			sink(a, 1, N, colorKey);
			show(a, 1, N, colorKey);
			row--;
		}
		
		assert isSorted(a);
	}
	
	private static <T extends Comparable<T>> void show(T[] a, int k, int N, int[] colorKey) {
		System.out.println(colorKey.length);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(-2, row, "" + N);
		StdDraw.text(-1, row, "" + k);
		for (int i = 1; i < a.length; i++) {
			int val = colorKey[i];
			if (val == 0) { StdDraw.setPenColor(StdDraw.GRAY); }
			else if (val == 1) { StdDraw.setPenColor(StdDraw.BLACK);}
			else if (val >= 2) { StdDraw.setPenColor(StdDraw.RED); }
			else               { System.out.printf("Incorrect colorKey value : %d \n", val); break; }
			
			StdDraw.text(i, row, a[i].toString());
		}
	}
		
	private static <T extends Comparable<T>> void showHeap(T[] a) {
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.textLeft(-2.5, row, "heap-ordered");
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int i = 0; i < a.length; i++) {
			StdDraw.text(i, row, a[i].toString());
		}
	}
	private static <T extends Comparable<T>> void initializePlot(T[] a) {
		int N = a.length;
		StdDraw.setPenRadius(.006);
		StdDraw.setXscale(-3, N+1);
		StdDraw.setYscale(-1, row + 1);
		StdDraw.text(-2, row, "N");
		StdDraw.text(-1, row, "k");
		for (int i = 0; i < N; i++) {
			StdDraw.text(i, row, "" + i);
		}
		row--;
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.textLeft(-2.5, row, "initial values");
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int i = 0; i < N; i++) {
			StdDraw.text(i, row, a[i].toString());
		}
		row--;
	}
	
	private static <T extends Comparable<T>> void sink(T[] a, int k, int N, int[] colorKey) {
		//if both are checked but neither exchanged, color should be black
		while (2 * k <= N) {
			int j = 2 * k;
			colorKey[j]++;
			if (j < N && less(a[j], a[j+1])) { j++; colorKey[j]++; }
			else if (j < N)                  { colorKey[j+1]++; }
			if (!less(a[k], a[j])) break;
			exch(a, k, j);
			colorKey[k]++;
			colorKey[j]++;
			k = j;
		}
	}
	
	protected static <T extends Comparable<T>> boolean isSorted(T[] a) {
		System.out.println("Calling isSorted within Heap class");
		for (int i = 2; i < a.length; i++) {
			if (less(a[i], a[i-1])) return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		//Double[] a = new Double[]{1., 2., 3.};
		String[] a = new String[] {"", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
		String[] b = new String[] {"", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
		//for (int i = 1; i < N; i++) {
		//	a[i] = StdRandom.uniform();
		//}
		row = 0;
		sort(b);
		row = Math.abs(row) + 2;
		initializePlot(a);
		sort(a);		
	}
}
