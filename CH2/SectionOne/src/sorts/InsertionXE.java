/**
 * Same as InsertionX except exchanges aren't performed in every iteration of inner loop
 */
package sorts;

public class InsertionXE extends ParentSort {
	
	public static <T extends Comparable<T>> void sort(T[] a){
		int N = a.length;
		int min = 0; //index of minimum element
		for (int i = 1; i < N; i++) {
			if (less(a[i], a[min])) { min = i; }
		}
		/*for (int i = N-1; i > 0; i--)
            if (less(a[i], a[i-1])) exch(a, i, i-1);*/
		exch(a, min, 0); //place smallest element in first position
		for (int i = 2; i < N; i++) {
			T v = a[i];
			int j = i;
			while (less(v, a[j-1])) {
				a[j] = a[j-1];
				j--;
			}
			a[j] = v;
		}
		
		assert isSorted(a);
	}
}
