import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;


public class MinPQ<Key> {
	private Key[] pq;
	private Comparator<Key> comparator;
	private int N;  //number of items on pq
	
	public MinPQ() {
		this(1);
	}
	
	@SuppressWarnings("unchecked")
	public MinPQ(int initCapacity) {
		pq = (Key[]) new Object[initCapacity + 1];
		N = 0;
	}
	
	public MinPQ(int initCapacity, Comparator<Key> comparator) {
		this(initCapacity);
		this.comparator = comparator;
	}
	
	public MinPQ(Comparator<Key> comparator) {
		this(1, comparator);
	}
	
	public void show() {
		StdOut.println(Arrays.toString(pq));
	}
	
	/**
	 * linear time algorithm based on inductively building heap using sinks
	 * @param a array to be heapified
	 */
	@SuppressWarnings("unchecked")
	public MinPQ(Key[] a) {
		int N = a.length;
		pq = (Key[]) new Object[N + 1];
		for (int i = 0; i < N; i++)  {
			pq[i+1] = a[i];
		}
		for (int k = N/2; k >= 1; k--) {
			sink(k);
		}
		assert isMinHeap();
	}
	
	public void insert(Key v) {
		if (N == pq.length - 1) { resize(2*pq.length); }
		pq[++N] = v;
		swim(N);
		assert isMinHeap();
	}
	
	public Key delMin() {
		if (isEmpty()) throw new NoSuchElementException("Priority Queue Underflow");
		exch(1,N);
		Key min = pq[N--];
		sink(1);
		pq[N+1] = null;
		if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length/2);
		assert isMinHeap();
		return min;
	}
	
	private void exch(int i, int j) {
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}
	
	private void sink(int k) {
		while(2 * k <= N){
			int j = 2*k;
			if (j < N && greater(j, j+1)) j++;
			if (!greater(k, j)) break;
			exch(k, j);
			k = j;
		}
	}
	
	private void swim(int k) {
		while (k > 1 && greater(k/2, k)) {
			exch(k/2, k);
			k = k/2;
		}
	}
	
	private boolean isMinHeap() {
		return isMinHeap(1);
	}
	
	/**
	 * Is subheap rooted at k a true heap
	 * @param k
	 * @return
	 */
	private boolean isMinHeap(int k) {
		if (k > N) return true;
		int left = 2*k, right = 2*k + 1;
		if (left <= N && greater(k, left)) return false;
		if (right <= N && greater(k, right)) return false;
		return isMinHeap(left) && isMinHeap(right);
	}
	
	/**
	 * Assumes if comparator is null that Key implements Comparable<Key>
	 * @param i
	 * @param j
	 * @return true if pq[i] > pq[j]
	 */
	@SuppressWarnings("unchecked")
	private boolean greater(int i, int j) {
		if (comparator != null) { return comparator.compare(pq[i], pq[j]) > 0; }
		else { return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0; }
	}

	public Key min() {
		return pq[1];
	}
	
	public boolean isEmpty() {
		return (N == 0);
	}
	
	public int size() {
		return N;
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		assert capacity > N;
		Key[] temp = (Key[]) new Object[capacity];
		for (int i = 1; i <= N; i++) {
			temp[i] = pq[i];
		}
		pq = temp;
	}
	
	/**
	 * Read in data from command line
	 * where - means delMin, otherwise insert
	 * @param args
	 */
	public static void main(String[] args) {
		MinPQ<String> pq = new MinPQ<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) pq.insert(item);
			else if (!pq.isEmpty()) StdOut.print(pq.delMin() + "\n");
		}
		StdOut.println("(" + pq.size() + " left on pq)");
	}
}