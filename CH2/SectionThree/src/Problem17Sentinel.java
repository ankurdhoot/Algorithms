import java.util.Arrays;

import edu.princeton.cs.introcs.*;


public class Problem17Sentinel {

	public static <T extends Comparable<T>> void sort(T[] a) {
		StdRandom.shuffle(a);
		T max = a[0];
		int id = 0;
		for (int i = 1; i < a.length; i++) {
			if (less(max, a[i])) { max = a[i]; id = i; }
		}
		exch(a, id, a.length - 1);  //places largest element at end of array
		sort(a, 0, a.length - 1);
		
		assert isSorted(a);
		System.out.println(Arrays.toString(a));
	}
	
	private static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
		if (hi <= lo) return;
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}
	
	private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
		int i = lo, j = hi + 1;
		T key = a[lo];
		while (true) {
			while (less(a[++i], key)) ;
			while (less(key, a[--j])) ;
			if (j <= i) break;
			exch(a, i, j);
		}
		exch(a, lo, j);
		return j;
	}
	
	private static <T extends Comparable<T>> boolean isSorted(T[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i-1])) return false;
		}
		return true;
	}
	
	protected static <T extends Comparable<T>> boolean less(T v, T w) 
	{ return v.compareTo(w) < 0; }
	
	
	protected static void exch(Object[] a, int i, int j) 
	{ Object t = a[i]; a[i] = a[j]; a[j] = t; }
	
	
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Double[] a = new Double[N];
        for (int i = 0; i < N; i++)
            a[i] = Math.random();
        sort(a);
    }
}
