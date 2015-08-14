/**
 * Same as Insertion sort except eliminates j > 0 test by first putting the smallest
 * item into position
 */
package sorts;

public class InsertionX extends ParentSort {
	
	public static <T extends Comparable<T>> void sort(T[] a){
		int N = a.length;
		int min = 0; //index of minimum element
		for (int i = 1; i < N; i++) {
			if (less(a[i], a[min])) { min = i; }
		}
		exch(a, min, 0); //place smallest element in first position
		for (int i = 2; i < N; i++) {
			for (int j = i; less(a[j], a[j-1]); j--) {
				exch(a, j, j-1);
			}
		}
		
		assert isSorted(a);
	}
}
