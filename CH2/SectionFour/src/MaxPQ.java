import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class MaxPQ<Key> {
	private Key[] pq; 
	private int N;
	private Comparator<Key> comparator;
	
	public MaxPQ() {
		this(1);
	}
	
	@SuppressWarnings("unchecked")
	public MaxPQ(int initCapacity) {
		pq = (Key[]) new Object[initCapacity+1];
		N = 0;
	}
	
	public MaxPQ(Comparator<Key> comparator) {
		this(1, comparator);
	}
	
	public MaxPQ(int initCapacity, Comparator<Key> comparator) {
		this(initCapacity);
		this.comparator = comparator;
	}
	
	private void swim(int k) {
		while (k > 1 && less(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}
	
	private void sink(int k) {
		while (2 * k <= N) {
			int j = 2*k;
			if (j < N && less(j,j+1)) j++;
			if (! less(k, j)) break;
			exch(k, j);
			k = j;
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean less(int i, int j) {
		if (!(comparator == null)) return (comparator.compare(pq[i], pq[j]) < 0);
		return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
	}
	
	private void exch(int i, int j) {
		Key temp = pq[i]; pq[i] = pq[j]; pq[j] = temp;
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int length) {
		assert (length > N);
		Key[] temp = (Key[]) new Object[length];
		for (int i = 1; i <= N; i++) {
			temp[i] = pq[i];
		}
		pq = temp;
	}
	
	private boolean isMaxHeap() {
		return isMaxHeap(1);
	}
	private boolean isMaxHeap(int root) {
		if (root > N) return true;  //could use N/2?
		int left = 2*root;
		int right = 2*root + 1;
		if (left <= N && less(root, left)) return false;
		if (right <= N && less(root, right)) return false;
		return isMaxHeap(left) && isMaxHeap(right);
	}
	
	public void insert(Key key) {
		if (N == pq.length - 1) resize(2 * pq.length);
		pq[++N] = key;
		swim(N);
		assert isMaxHeap();
	}
	
	public Key max() {
		return pq[1];
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public Key delMax() {
		if (isEmpty()) throw new NoSuchElementException("Priority Queue Underflow");
		exch(1,N);
		Key max = pq[N--];
		sink(1);
		pq[N+1] = null;
		if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length/2);
		assert isMaxHeap();
		return max;
	}
	
	public int size() {
		return N;
	}
	
	private void show() {
		System.out.println(Arrays.toString(pq));
	}
	public static void main(String[] args) {
		MaxPQ<String> pq = new MaxPQ<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) pq.insert(item);
			else if (!pq.isEmpty()) StdOut.print(pq.delMax() + "\n");
		}
		StdOut.println("(" + pq.size() + " left on pq)");
		pq.show();
	}
	
}
