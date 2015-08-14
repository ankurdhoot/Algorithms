package sorts;

import java.util.Arrays;

public class Shell extends ParentSort {
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		int N = a.length;
		int h = 1;
		while (h < N/3) h = 3 * h + 1; //1,4,13,40,121....
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
					exch(a, j, j-h);
				}
			}
			h = h/3;
		}
		
		assert isSorted(a);
	}
	
	public static <T extends Comparable<T>> void hSort(T[] a, int h) {
		int N = a.length;
		for (int i = h; i < N; i++) {
			for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
				exch(a, j, j-h);
				show(a);
			}
		}
	}
	
	public static <T extends Comparable<T>> boolean isHSorted(T[] a, int h) {
		assert h >= 1;
		int N = a.length;
		for (int i = h; i < N; i++) {
			if (less(a[i], a[i-h])) {System.out.println(i + " , " + (i-h));  return false; }
		}
		return true;
	}
	
	public static void main(String[] args) {
		Integer[] a = {0,6,1,2,3,8,3,4,5};
		System.out.println(isHSorted(a, 4));
		hSort(a, 3);
		System.out.println(Arrays.toString(a));
		System.out.println(isHSorted(a, 3));
		System.out.println(isHSorted(a, 4));
	}
}
