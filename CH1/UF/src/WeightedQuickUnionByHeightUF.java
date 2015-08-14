
public class WeightedQuickUnionByHeightUF implements UF {
	private int[] height;
	private int[] id;
	private int count;
	private int numArrayHits = 0;
	
	public WeightedQuickUnionByHeightUF(int N) {
		height = new int[N];
		id = new int[N];
		count = N;
		//numArrayHits = 2 * N;
		for (int i = 0; i < N; i++) {
			height[i] = 1;
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
		while (p != id[p]) { numArrayHits ++; p = id[p]; }
		return p;
	}
	
	public void union(int p, int q) {
		//do nothing to heights if unequal
		//increment height by one if unioning two trees with equal height
		int i = find(p);
		int j = find(q);
		if (i == j) return;
		
		if (height[i] < height[j]) { numArrayHits += 3; id[i] = j; } 
		else if (height[j] < height[i]) { numArrayHits += 3; id[j] = i; } //array hits from first if are cached
		else {
			id[i] = j; //arbitrary when heights are equal
			height[j]++;
			numArrayHits += 4;
		}
		count--;
	}
	
	public static void main(String[] args) {
		int N = StdIn.readInt();
		WeightedQuickUnionByHeightUF uf = new WeightedQuickUnionByHeightUF(N);
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