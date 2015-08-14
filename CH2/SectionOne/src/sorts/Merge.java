package sorts;

public class Merge extends ParentSort {
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		@SuppressWarnings("unchecked")
		T[] aux = (T[]) new Comparable[a.length];
		sort(a, aux, 0, a.length - 1);
		
		assert isSorted(a);
	}
	
	public static <T extends Comparable<T>> void sort(T[] a, T[] aux, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo)/2;
		sort(a, aux, lo, mid);
		sort(a, aux,  mid+1, hi);
		merge(a, aux, lo, mid, hi);
	}
	
	public static <T extends Comparable<T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}
		for (int k = lo; k <= hi; k++) {
			if       (i > mid)              a[k] = aux[j++];
			else if  (j > hi)               a[k] = aux[i++];
			else if  (less(aux[j], aux[i])) a[k] = aux[j++];
			else                            a[k] = aux[i++];                     
		}
	}
}