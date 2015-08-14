package sorts;

public class MergeFaster {
	//Problem 10 from 2.2

	public static <T extends Comparable<T>> void sort(T[] a) {
		int N = a.length;
		@SuppressWarnings("unchecked")
		T[] aux = (T[]) new Comparable[N];
		sort(a, aux, 0, N - 1);
		
		assert isSorted(a);
	}
	
	private static <T extends Comparable<T>> void sort(T[] a, T[] aux, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
	}
	
	private static <T extends Comparable<T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
		for (int k = lo; k <= mid; k++) {
			aux[k] = a[k];
		}
		
		for (int k = hi, i = mid + 1; k >= mid + 1; k--, i++) {
			aux[i] = a[k];
		}
		
		int i = lo, j = hi;
		for (int k = lo; k <= hi; k++) {
			if (less(aux[j], aux[i])) a[k] = aux[j--];
			else 					  a[k] = aux[i++];
		}
	}
	
	private static <T extends Comparable<T>> boolean less(T v, T w)
	{ return v.compareTo(w) < 0; }

	private static <T extends Comparable<T>> boolean isSorted(T[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i- 1])) return false;
		}
		return true;
	}
}
