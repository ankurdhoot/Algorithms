import java.util.Arrays;


public class Problem20IndexSort {
	
	private static boolean isSorted(double[] a, int[] id) {
		int N = id.length;
		for (int i = 1; i < N; i++) {
			if (less(id[i], id[i-1], a)) return false;
		}
		return true;
	}
	
	private static boolean less(int v, int w, double[] a) {
		return a[v] < a[w];
	}
	
	public static void sort(double[] a) {
		int N = a.length;
		int[] id = new int[N];
		int[] aux = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
		sort(a, id, aux, 0, N-1);
		
		assert isSorted(a, id);
		System.out.println(Arrays.toString(a));
		System.out.println(Arrays.toString(id));
	}
	
	private static void sort(double[] a, int[] id, int[] aux, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		sort(a, id, aux, lo, mid);
		sort(a, id, aux, mid+1, hi);
		merge(a, id, aux, lo, mid, hi);
	}
	
	private static void merge(double[] a, int[] id, int[] aux, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		
		for(int k = lo; k <= hi; k++) {
			aux[k] = id[k];
		}
		
		for (int k = lo; k <= hi; k++) {
			if (i > mid)                      id[k] = aux[j++];
			else if (j > hi)                  id[k] = aux[i++];
			else if (less(aux[j], aux[i], a)) id[k] = aux[j++];
			else                              id[k] = aux[i++];
		}
	}
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		double[] a = new double[N];
		for (int i = 0; i < N; i++) {
			a[i] = Math.random();
		}
		sort(a);
	}
}
