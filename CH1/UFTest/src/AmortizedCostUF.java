
public class AmortizedCostUF {

	private UF uf;
	
	public int arrayHits() 
	{ return uf.arrayHits(); }
	
	public int find(int p) {
		return uf.find(p);
	}
	
	public void union(int p, int q) {
		uf.union(p, q);
	}
	
	public boolean connected(int p, int q) {
		return uf.connected(p,q);
	}
	
	public int count() {
		return uf.count();
	}
	
	public void parseUF(String s, int N) {
		switch (s) {
		case "QuickFindUF" : uf = new QuickFindUF(N);
							 break;
		case "QuickUnionUF" : uf = new QuickUnionUF(N);
							  break;
		case "WeightedQuickUnionUF" : uf = new WeightedQuickUnionUF(N);
									  break;
		/**case "QuickUnionPathCompressionUF" : uf = new QuickUnionPathCompressionUF(N);
											 break;
		case "WeightedQuickUnionPathCompressionUF" : uf = new WeightedQuickUnionPathCompressionUF(N);
														  break;
		case "WeightedQuickUnionByHeightUF" : uf = new WeightedQuickUnionByHeightUF(N);
												   break;
		case "WeightedQuickUnionByHeightPathCompressionUF" : uf = new WeightedQuickUnionByHeightPathCompressionUF(N);
																  break; */
		default : System.out.println("Invalid Option. Defaulting to WeightedQuickUnion");
				  uf = new WeightedQuickUnionUF(N);
				  break;
		}
	}
	
	/**
	 * Draws a red dot at (i, total/i); 
	 * @param total numerical value of total cost
	 * @param i iteration number
	 */
	public static void drawTotal(int total, int i) {
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(.01);
		StdDraw.point(i, ((double)total) / i);
	}
	
	public static void drawCurrent(int current, int i) {
		StdDraw.setPenColor(StdDraw.GRAY);
		StdDraw.setPenRadius(.01);
		StdDraw.point(i, current);
	}
	
	public static void main(String[] args) {
		AmortizedCostUF a = new AmortizedCostUF();
		String UFType = args[0];
		int N = StdIn.readInt();
		StdDraw.setXscale(0, N);
		//StdDraw.setYscale(0, 2*N); //2*N is max value possible for any UF data structure? 
		a.parseUF(UFType, N);
		int previousTotal = a.arrayHits();
		int currentTotal;
		int currentCost;  //holds array access cost for ith iteration
		for (int i = 1; !StdIn.isEmpty(); i++) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			a.union(p, q);
			currentTotal = a.arrayHits();
			currentCost = currentTotal - previousTotal;
			previousTotal = currentTotal;
			drawTotal(currentTotal, i);
			drawCurrent(currentCost, i);
		}
	}
}
