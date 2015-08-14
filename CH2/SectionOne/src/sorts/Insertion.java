package sorts;

public class Insertion extends ParentSort {
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		int N = a.length;
		for (int i = 1; i < N; i++) {
			for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
				exch(a, j, j-1);
			}
		}
		
		assert isSorted(a);
	}
	
	/**
	 * Used for speeding up merge sort
	 * Used for sorting small subarrays(e.g < 15)
	 * @param a
	 * @param lo
	 * @param hi
	 */
	public static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++) {
			for (int j = i; j > lo && less(a[j], a[j-1]); j--) {
				exch(a, j, j-1);
			}
		}
	}
}
