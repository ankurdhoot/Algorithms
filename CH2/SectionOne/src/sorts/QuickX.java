/**
 * Implements median of 3 partitioning as well as InsertionSort cutoff for small subarrays
 */
package sorts;

import edu.princeton.cs.introcs.*;

public class QuickX extends ParentSort {
	private static final int CUTOFF = 10;
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		StdRandom.shuffle(a);
		
		sort(a, 0, a.length - 1);
		
		assert isSorted(a);
	}
	
	public static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
		if (hi <= lo) return;
		if (hi - lo + 1 <= CUTOFF) { Insertion.sort(a, lo, hi); return; }
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}
	
    private static <T extends Comparable<T>> int median3(T[] a, int i, int j, int k) {
        return (less(a[i], a[j]) ?
               (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
               (less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
    }
	
	private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
		int N = hi - lo + 1;
		int mid = median3(a, lo, lo + N/2, hi);
		exch(a, lo, mid);   //currently no sentinel nor insertion sort
		
		int i = lo, j = hi + 1;
		T key = a[lo];
		while (true) {
			while (less(a[++i], key)) if (i == hi) break;
			while (less(key, a[--j])) ;
			if (j <= i) break;
			exch(a, i, j);
		}
		exch(a, lo, j);
		return j;
	}
	
	
}
