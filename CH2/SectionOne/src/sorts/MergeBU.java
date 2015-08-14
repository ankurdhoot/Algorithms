package sorts;

public class MergeBU extends ParentSort {
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		int N = a.length;
		@SuppressWarnings("unchecked")
		T[] aux = (T[]) new Comparable[N];
		for (int sz = 1; sz < N; sz = sz + sz) {
			for (int lo = 0; lo < N - sz; lo += sz + sz) {
				merge(a, aux, lo, lo+sz-1, Math.min(lo + sz + sz - 1, N-1));
			}
		}
		
		assert isSorted(a);
	}
	
	public static <T extends Comparable<T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}
		
		for (int k = lo; k <= hi; k++) {
			if (i > mid)                   a[k] = aux[j++];
			else if (j > hi)               a[k] = aux[i++];
			else if (less(aux[j], aux[i])) a[k] = aux[j++];
			else                           a[k] = aux[i++];
		}
	}
}
