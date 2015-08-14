
public class WeightedQuickUnionPathCompressionUF implements UF {

	private int[] size;
	private int[] id;
	private int count;
	private int numArrayHits = 0;
	
	public WeightedQuickUnionPathCompressionUF(int N) {
		size = new int[N];
		id = new int[N];
		count = N;
		//numArrayHits = 2*N;
		for (int i = 0; i < N; i++) {
			size[i] = 1;
			id[i] = i;
		}
	}
	
	public int arrayHits() 
	{ return numArrayHits; }
	
	public int count() 
	{ return count; }
	
	public boolean connected(int p, int q) 
	{ return find(p) == find(q); }
	
	public int find(int p) {
		int root = p;
		numArrayHits++;
		while (p != id[p]) { numArrayHits ++; p = id[p]; }
		while (root != p) {
			int next = id[root];
			id[root] = p;
			root = next;
			numArrayHits += 2;
		}
		return p;
	}
	
	public void union(int p, int q) {
		int i = find(p);
		int j = find(q);
		if (i == j) return;
		
		if (size[i] < size[j]) {
			id[i] = j;
			size[j] += size[i];
		} else {
			id[j] = i;
			size[i] += size[j];
		}
		count--;
		numArrayHits += 4;  //size[i] and size[j] are cached
	}
	
	public static void main(String[] args) {
		int N = StdIn.readInt();
		WeightedQuickUnionPathCompressionUF uf = new WeightedQuickUnionPathCompressionUF(N);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (uf.connected(p,q)) continue;
			uf.union(p,q);
			StdOut.println(p + " " + q);
		}
		StdOut.println(uf.count() + " components");
	}
	
}
