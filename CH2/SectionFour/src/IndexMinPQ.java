import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class IndexMinPQ<Key extends Comparable<Key>>{
	private int N;
	private int[] pq;    //pq[i] gives the position of i in qp
	private int[] qp;    //qp[i] gives the position of i in pq, index j s.t pq[j] is i
	private Key[] keys;
	
	@SuppressWarnings("unchecked")
	public IndexMinPQ(int initCapacity) {
		N = 0;
		keys = (Key[]) new Comparable[initCapacity + 1];
		pq = new int[initCapacity + 1];
		qp = new int[initCapacity + 1];
		for (int i = 0; i <= initCapacity; i++) qp[i] = -1;
	}
	
	public IndexMinPQ() {
		this(1);
	}
	
	private void swim(int k) {
		while (k > 1 && less(k, k/2)) {
			exch(k/2, k);
			k = k/2;
		}
	}
	
	private void sink(int k) {
		while (2*k <= N) {
			int j = 2 * k;
			if (j < N && less(j+1, j)) j++;
			if (!less(j, k)) break;
			exch(j, k);
			k = j;
		}
	}
	
	private void exch(int i, int j) {
		int id1 = pq[i];   //id1 is location of ith object in qp
		int id2 = pq[j];   //id2 is location of jth object in qp
		pq[i] = id2;
		pq[j] = id1;
		qp[id1] = j;
		qp[id2] = i;
		//could swap and then qp[pq[i]] = i; qp[pq[j]] = j;
	}
	
	private boolean less(int i, int j) {
		Key v = keys[pq[i]];
		Key w = keys[pq[j]];
		return (v.compareTo(w) < 0);
	}
	
	public void insert(int i, Key key) {
		if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
		System.out.printf("Inserting object %d with key %s \n", i, key);
		N++;
		qp[i] = N;
		pq[N] = i;
		keys[i] = key;
		swim(N);
		assert isValidIndexMinPQ();
	}
	
	public void changeKey(int i, Key key) {
		if (!contains(i)) throw new NoSuchElementException("Index is not in the priority queue");
		System.out.printf("Changing key of object %d from %s to %s \n", i, keys[i], key);
		keys[i] = key;
		swim(qp[i]);   //key could need to go up or down
		sink(qp[i]);
		assert isValidIndexMinPQ();
	}
	/**
	 * returns true if index is already taken
	 * @param i - index of element in qp
	 * @return
	 */
	public boolean contains(int i) {
		return (qp[i] != -1);
	}
	
	public void delete(int i) {
		if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
		int index = qp[i];   //location of i in pq
		exch(index, N--);
		//could null out pq[N+1];
		swim(index);
		sink(index);
		System.out.printf("Deleting object %d with key %s \n", i, keys[i]);
		keys[i] = null;
		qp[i] = -1;
		assert isValidIndexMinPQ();
	}
	
	private boolean isValidIndexMinPQ() {
		//first check to make sure qp[pq[i]] == i for all i
		for (int i = 1; i <= N; i++) {
			if (qp[pq[i]] != i) return false;
		}
		return validateMinHeap(1);
	}
	
	private boolean validateMinHeap(int k) {
		if (k > N) return true;
		int left = 2*k;
		int right = 2*k + 1;
		if (left <= N && less(left, k)) return false;
		if (right <= N && less(right, k)) return false;
		return validateMinHeap(left) && validateMinHeap(right);
	}
	public Key minKey() {
		return keys[pq[1]];
	}
	
	public int minIndex() {
		return pq[1];
	}
	
	public int delMin() {
		int indexOfMin = pq[1];
		exch(1, N--);
		sink(1);
		System.out.printf("Deleting object %d with key %s \n", indexOfMin, keys[indexOfMin]);
		keys[indexOfMin] = null;
		qp[indexOfMin] = -1;
		assert isValidIndexMinPQ();
		return indexOfMin;
	}
	
	public boolean isEmpty() {
		return (N == 0);
	}
	
	public int size() {
		return N;
	}
	
	public Key keyOf(int i) {
		return keys[i];
	}
	
	public static void main(String[] args) {
		//format of file
		//a 1
		//z 2
		//- 2
		//delMin
		String filename;
		Scanner s = new Scanner(System.in);
		if (args.length > 0) {
			filename = args[0];
			System.out.println("Using file : " + filename);
			try {
				s = new Scanner(new File(filename));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int N = s.nextInt();
		System.out.println("Using pq of size : " + N);
		IndexMinPQ<String> pq = new IndexMinPQ<String>(N);
		while (s.hasNext()) {
			String key = s.next();
			if (key.equals("delMin")) pq.delMin();
			else if (key.equals("-")) pq.delete(s.nextInt());
			else {
				int i = s.nextInt();
				if (pq.contains(i)) pq.changeKey(i, key);
				else                pq.insert(i, key);
			}
		}
	}
}
