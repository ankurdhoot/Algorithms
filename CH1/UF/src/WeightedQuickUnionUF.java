
public class WeightedQuickUnionUF implements UF {
	private int[] size;
	private int[] id;
	private int count;
	private int numArrayHits = 0;
	
	public WeightedQuickUnionUF(int N) {
		size = new int[N];
		id = new int[N];
		count = N;
		//numArrayHits = 2 * N;
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
		numArrayHits++;
		while (p != id[p]) { numArrayHits++; p = id[p]; }
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
		numArrayHits += 3; //size[i] and size[j] are temporarily cached
	}
	
	public static void main(String[] args) {
		int N = StdIn.readInt();
		WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
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


