/**
 * Implements 3 way partitioning, useful for arrays with many duplicate keys
 */
package sorts;

import edu.princeton.cs.introcs.StdRandom;

public class Quick3way extends ParentSort {

	public static <T extends Comparable<T>> void sort(T[] a) {
		StdRandom.shuffle(a);		
		sort(a, 0, a.length - 1);
		
		assert isSorted(a);		
	}
	
	private static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
		if (hi <= lo) return;
		int lt = lo, i = lo + 1, gt = hi;
		T key = a[lo];
		while (i <= gt) {
			int cmp = a[i].compareTo(key);
			if      (cmp < 0) exch(a, lt++, i++);
			else if (cmp > 0) exch(a, i, gt--);
			else              i++;  
		}
		sort(a, lo, lt - 1);
		sort(a, gt + 1, hi);
	}
}
