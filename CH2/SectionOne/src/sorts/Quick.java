/**
 * Basic QuickSort without any enhancements, shuffles input array to for average performance
 */
package sorts;

import edu.princeton.cs.introcs.*;

public class Quick extends ParentSort {
	public static <T extends Comparable<T>> void sort(T[] a) {
		StdRandom.shuffle(a);
		sort(a, 0, a.length - 1);
		
		assert isSorted(a);
	}
	
	private static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
		if (hi <= lo) return;
		int j = partition(a, lo, hi);
		sort(a, lo, j-1);
		sort(a, j + 1, hi);
	}
	
	private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
		int i = lo, j = hi + 1;
		T key = a[lo];
		while (true) {
			while (less(a[++i], key)) if (i == hi) break;
			while (less(key, a[--j])) if (j == lo) break;  //redundant if check since key = a[lo]
			if (j <= i) break;
			exch(a, i, j);
		}
		exch(a, lo, j);
		return j;
	}
	
}
