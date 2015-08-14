/**
 * Construct a heap first, then sortdown
 * Heap construction takes <= 2N
 * sortdown takes <= 2NlogN
 */
package sorts;

public class Heap extends ParentSort {
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		int N = a.length;
		for (int k = N/2; k >= 1; k++) {
			sink(a, k, N);
		}
		
		while (N > 1) {
			exch(a, 1, N--);
			sink(a, 1, N);
		}
		assert isSorted(a);
	}
	
	private static <T extends Comparable<T>> void sink(T[] a, int k, int N) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && less(a, j, j+1)) j++;
			if (!less(a, k, j)) break;
			exch(a, k, j);
			k = j;
		}
	}
	
	private static <T extends Comparable<T>> boolean less(T[] a, int i, int j) {
		return a[i-1].compareTo(a[j-1]) < 0;
	}
}
