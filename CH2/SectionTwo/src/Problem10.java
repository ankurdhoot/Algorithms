
public class Problem10 {
	
	public static void sort(double[] a) {
		int N = a.length;
		double[] aux = new double[N];
		sort(a, aux, 0, N - 1);
		
		assert isSorted(a);
	}
	
	private static void sort(double[] a, double[] aux, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
	}
	
	private static void merge(double[] a, double[] aux, int lo, int mid, int hi) {
		for (int k = lo; k <= mid; k++) {
			aux[k] = a[k];
		}
		
		for (int k = hi; k >= mid + 1; k--) {
			aux[k] = a[k];
		}
		
		int i = lo, j = hi;
		for (int k = lo; k <= hi; k++) {
			if (less(aux[j], aux[i])) a[k] = aux[j--];
			else 					  a[k] = aux[i++];
		}
	}
	
	private static boolean less(double v, double w)
	{ return v < w; }

	private static boolean isSorted(double[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i- 1])) return false;
		}
		return true;
	}
}
