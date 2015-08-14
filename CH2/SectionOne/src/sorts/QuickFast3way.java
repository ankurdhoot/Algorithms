/**
 * Problem 22 2.3 pg. 306
 */
package sorts;

import java.util.Random;

import edu.princeton.cs.introcs.StdRandom;

public class QuickFast3way extends ParentSort {
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		StdRandom.shuffle(a);		
		//show(a);
		sort(a, 0, a.length - 1);
		
		assert isSorted(a);
	}
	
	/**
	 * a[lo....p] = key, a[p + 1....i] < key, a[j .... q - 1] > key, a[q ... hi] = key
	 * at termination a[p+1....j] < key and a[i....q-1] > key
	 * @param a
	 * @param lo
	 * @param hi
	 */
	private static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
		if (hi <= lo) return;
		int p = lo , i = lo, j = hi + 1, q = hi + 1;
		T key = a[lo];
		//System.out.println(key);
		while (true) {
			while (less(a[++i], key)) if (i == hi) break;
			while (less(key, a[--j])) if (j == lo) break;
			
			if (i == j && eq(a[i], key)) 
				exch(a, ++p, i);
			if (j <= i) break;
			exch(a, i, j);
			
			if (eq(a[i], key)) exch(a, ++p, i);
			if (eq(a[j], key)) exch(a, --q, j);
		}
		
		i = j + 1; //could be a redundant assignment, could change if (i == j) to exch(a, ++p, i++)
		
		for (int k = lo; k <= p; k++) exch(a, k, j--);
		for (int k = hi; k >= q; k--) exch(a, k, i++);
		//show(a);
		sort(a, lo, j);
		sort(a, i, hi);
	}
	
	private static <T extends Comparable<T>> boolean eq(T v, T w) 
	{ return v.compareTo(w) == 0; }
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		//int N = 10;
		Random rand = new Random();
		Integer[] a = new Integer[N];
		for (int i = 0; i < N; i++) {
			a[i] = (rand.nextInt(N));
		}
		sort(a);
	}
}
