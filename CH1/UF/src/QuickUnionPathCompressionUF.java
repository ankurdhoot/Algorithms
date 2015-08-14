
public class QuickUnionPathCompressionUF implements UF {
	private int id[];
	private int count;
	private int numArrayHits = 0;
	
	public QuickUnionPathCompressionUF(int N) {
		id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
		count = N;
		//numArrayHits = N;
	}
	
	public int arrayHits() 
	//expect find operations to be more array hit intensive 
	//as more sites are added, amortized cost should increase?
	{ return numArrayHits; }
	
	public boolean connected(int p, int q) 
	{ return find(p) == find(q); }
	
	public int count()
	{ return count; }
	
	public int find(int p) {
		int root = p;
		numArrayHits++;
		while (p != id[p]) { numArrayHits++; p = id[p]; }
		while (root != p) {  //path compression
			int newp = id[root];
			id[root] = p;
			root = newp;
			numArrayHits += 2;
		}
	return p;
	}
	
	public void union(int p, int q) {
		int i = find(p);
		int j = find(q);
		if (i == j) return;
		id[i] = j;
		numArrayHits++;
		count--;
	}
	
	public static void main(String[] args) {
		int N = StdIn.readInt();
		QuickUnionPathCompressionUF uf = new QuickUnionPathCompressionUF(N);
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
