
public class QuickFindUF implements UF {

	private int[] id;
	private int count;
	private int numArrayHits = 0;
	
	public QuickFindUF(int N) {
		id = new int[N];
		count = N;
		//numArrayHits = N;
		for (int i = 0; i < N; i++) id[i] = i;
	}
	
	public int arrayHits() 
	{ return numArrayHits; }
	
	public int count() 
	{ return count; }
	
	public int find(int p) 
	{ numArrayHits++; return id[p]; }
	
	public boolean connected(int p, int q) 
	{ return find(p) == find(q); }
	
	public void union(int p, int q) {
		int pID = find(p);
		int qID = find(q);
		if (pID == qID) return;
		
		for (int i = 0; i < id.length; i++) {
			numArrayHits++; //increment for condition check array hit
			if (id[i] == pID) { numArrayHits++; id[i] = qID; }
		}
		count--;
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
