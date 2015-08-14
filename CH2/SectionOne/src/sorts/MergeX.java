/**
 * Uses insertion sort for small subarrays, eliminates copy to auxiliary array during merge
 */
package sorts;

public class MergeX extends ParentSort {
	private static final int CUTOFF = 8;
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		int N = a.length;
		T[] aux = a.clone();
		sort(aux, a, 0, N - 1);
		
		assert isSorted(a);
	}
	
	/**
	 * Sorts dst from lo ... hi
	 * @param src
	 * @param dst
	 * @param lo
	 * @param hi
	 */
	public static <T extends Comparable<T>> void sort(T[] src, T[] dst, int lo, int hi) {
		if (hi <= lo) return;
		if ((hi - lo + 1) < CUTOFF) {Insertion.sort(dst, lo, hi); }  //hi - lo + 1 is subarray size
		else {
			int mid = lo + (hi - lo) / 2;
			sort(dst, src, lo, mid);  //sorts src from lo ... mid
			sort(dst, src, mid+1, hi);
			if (!less(src[mid],src[mid+1])) { merge(src, dst, lo, mid, hi); }
			else {System.arraycopy(src, lo, dst, lo, hi - lo + 1); }
		}
	}
	
	public static <T extends Comparable<T>> void merge(T[] src, T[] dst, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		
		for (int k = lo; k <= hi; k++) {
			if       (i > mid)              dst[k] = src[j++];
			else if  (j > hi)               dst[k] = src[i++];
			else if  (less(src[j], src[i])) dst[k] = src[j++];
			else                            dst[k] = src[i++];                     
		}
	}
}
