/**
 * Puts the highest element in the last position to eliminate the bound checks in partition method
 * The lower bound will never be exceeded since a[lo] is not greater than a[lo]
 * The upper bound will never be exceeded since the leftmost entry to the right of a[hi] is the 
 * partition which be definition is >= all elements to the left of it
 */
package sorts;

import edu.princeton.cs.introcs.StdRandom;

public class QuickSentinel extends ParentSort {

	public static <T extends Comparable<T>> void sort(T[] a) {
		StdRandom.shuffle(a);
		T max = a[0];
		int id = 0;
		for (int i = 1; i < a.length; i++) {
			if (less(max, a[i])) { max = a[i]; id = i; }
		}
		exch(a, id, a.length - 1);  //places largest element at end of array
		sort(a, 0, a.length - 1);
		
		assert isSorted(a);
	}
	
	private static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
		if (hi <= lo) return;
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}
	
	private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
		int i = lo, j = hi + 1;
		T key = a[lo];
		while (true) {
			while (less(a[++i], key)) ;
			while (less(key, a[--j])) ;
			if (j <= i) break;
			exch(a, i, j);
		}
		exch(a, lo, j);
		return j;
	}
}
