package sorts;

public class Selection extends ParentSort {

	public static <T extends Comparable<T>> void sort(T[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int min = i;
			for (int j = i + 1; j < N; j++) 
				if (less(a[j], a[min])) min = j;
			exch(a, i, min);
		}	
		
		assert isSorted(a);
	}

}
