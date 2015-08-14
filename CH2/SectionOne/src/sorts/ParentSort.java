package sorts;
import edu.princeton.cs.introcs.*;

abstract class ParentSort {
	//maybe make all methods protected so subclasses can access the methods
	
	protected static <T extends Comparable<T>> boolean less(T v, T w) 
	{ return v.compareTo(w) < 0; }
	
	
	protected static void exch(Object[] a, int i, int j) 
	{ Object t = a[i]; a[i] = a[j]; a[j] = t; }
	
	protected static void show(Object[] a) {
		for(int i = 0; i < a.length; i++) 
			{ StdOut.print(a[i] + " " ); }
		StdOut.println();
	}
	
	protected static <T extends Comparable<T>> boolean isSorted(T[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i-1])) return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		Integer[] a = {1,2,3};
		//sort(a);
		assert isSorted(a);
		show(a);
	}
	
}
