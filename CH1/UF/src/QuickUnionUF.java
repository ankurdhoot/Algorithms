
public class QuickUnionUF implements UF {
	
	private int id[];
	private int count;
	private int numArrayHits = 0;
	
	public int arrayHits() 
	{ return numArrayHits; }
	
	public QuickUnionUF(int N) {
		id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
		count = N;
		//numArrayHits = N;
	}
	
	public boolean connected(int p, int q) 
	{ return find(p) == find(q); }
	
	public int count()
	{ return count; }
	
	public int find(int p) {
		numArrayHits++; //at least one array hit if condition fails immediately
		while (p != id[p]) { p = id[p]; numArrayHits++; } //one hit for condition check and update
	return p;
	}
	
	public void union(int p, int q) {
		int i = find(p);
		int j = find(q);
		if (i == j) return;
		id[i] = j;
		count--;
		numArrayHits++;
	}
	
	public static void main(String[] args) {
		int N = StdIn.readInt();
		QuickUnionUF uf = new QuickUnionUF(N);
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
