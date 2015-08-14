
public class HeapTraceOneOff {
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		int N = a.length;
		for (int k = N/2; k >= 1; k++) {
			sink(a, k, N);
		}
		
		while (N > 1) {
			exch(a, 1, N--);
			sink(a, 1, N);
		}
		assert isSorted(a);
	}
	
	private static <T extends Comparable<T>> void sink(T[] a, int k, int N) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && less(a, j, j+1)) j++;
			if (!less(a, k, j)) break;
			exch(a, k, j);
			k = j;
		}
	}
	
	private static <T extends Comparable<T>> boolean less(T[] a, int i, int j) {
		return a[i-1].compareTo(a[j-1]) < 0;
	}
	
	private static void exch(Object[] pq, int i, int j) {
		Object swap = pq[i-1];
		pq[i-1] = pq[j-1];
		pq[j-1] = swap;
	}
	
	private static <T extends Comparable<T>> boolean less(T v, T w) {
		return v.compareTo(w) < 0;
	}
	
	private static <T extends Comparable<T>> boolean isSorted(T[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i-1])) return false;
		}
		return true;
	}

}
