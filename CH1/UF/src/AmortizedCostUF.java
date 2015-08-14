import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class AmortizedCostUF {
	private final static List<String> UFTypes = Arrays.asList("QuickFindUF", "QuickUnionUF", "WeightedQuickUnionUF",
											 "WeightedQuickUnionPathCompressionUF");
	private int prevCost = 0;
	private int totalCost = 0;
	private int largestCost = 0;
	private UF uf;
	private Scanner s;  //used to read from standard input
	private List<Integer> costs;
	
	public void add(int i, int cost) 
	{ costs.add(i, cost); }
	
	public void setPrevCost(int cost) 
	{ prevCost = cost; }
	
	public int size() 
	{ return costs.size(); }
	
	public int getTotalCost()
	{ return totalCost; }
	
	public AmortizedCostUF() {
		costs = new ArrayList<>();  //automatically makes <Integer>
		s = new Scanner(System.in);		
	}
	
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
	
	/**
	 * Creates appropriate UF object based on input string
	 * Could be updated to return the UF object 
	 * @param s
	 * @param N
	 */
	public void parseUF(String s, int N) {
		switch (s) {
		case "QuickFindUF" : uf = new QuickFindUF(N);
							 break;
		case "QuickUnionUF" : uf = new QuickUnionUF(N);
							  break;
		case "WeightedQuickUnionUF" : uf = new WeightedQuickUnionUF(N);
									  break;
		case "QuickUnionPathCompressionUF" : uf = new QuickUnionPathCompressionUF(N);
											 break;
		case "WeightedQuickUnionPathCompressionUF" : uf = new WeightedQuickUnionPathCompressionUF(N);
														  break;
		case "WeightedQuickUnionByHeightUF" : uf = new WeightedQuickUnionByHeightUF(N);
												   break;
		case "WeightedQuickUnionByHeightPathCompressionUF" : uf = new WeightedQuickUnionByHeightPathCompressionUF(N);
																  break;
		default : System.out.println("Invalid Option. Defaulting to WeightedQuickUnion with PathCompression");
				  uf = new WeightedQuickUnionPathCompressionUF(N);
				  break;
		}
	}
	
	/**
	 * Draws a red dot at (i, total/i); 
	 * @param total numerical value of total cost
	 * @param i iteration number
	 */
	public void drawTotal(int i) {
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(.01);
		StdDraw.point(i, ((double)totalCost) / i);
	}
	
	public void drawCurrent(int i) {
		StdDraw.setPenColor(StdDraw.GRAY);
		StdDraw.setPenRadius(.02);
		StdDraw.point(i, costs.get(i));
	}
	
	public void plot() {
		StdDraw.clear();
		int xMax = costs.size();
		StdDraw.setXscale(-20, xMax + 15);
		StdDraw.setYscale(-10, largestCost + 20);
		StdDraw.text(0,0,"0");
		StdDraw.text(0, largestCost, "" + largestCost, 90);
		StdDraw.text(xMax,0, "" + xMax);
		double sum = 0;
		int i;
		for (i = 0; i < xMax; i++) {
			StdDraw.setPenRadius(.01);
			StdDraw.setPenColor(StdDraw.RED);
			sum += costs.get(i);
			StdDraw.point(i, sum / i);  //average cost through i iterations
			StdDraw.setPenColor(StdDraw.GRAY);
			StdDraw.point(i, costs.get(i));
		}
		StdDraw.text(0, totalCost / i, "" + (int) sum/i, 90);
	}
	
	public void plot(String UFType, String filename) {
		plot();
		int x = costs.size() / 2;
		StdDraw.text(x, largestCost + 1, UFType + " " + filename);
	}
	
	
	/**
	 * Repeatedly asks user for input file until valid file name given.
	 * @return Scanner object of input file
	 */
	public Scanner getInput() {
		boolean isFile = false;
		Scanner input = null;
		while (!isFile) {
			System.out.println("Enter text file located in data folder you would like to use: (include ending) ");
			String filename = s.next();
			File f = new File("data\\" + filename);
			try {
				input = new Scanner(f);
				isFile = true;
			} catch (FileNotFoundException e) {
				System.out.println("Unable to find file. Please try again.");
			}
		}
		return input;
	}
	
	/**
	 * 
	 * @return String name of union find data structure to be used
	 */
	public String getDataStructure() {
		String UFType = null;
		do {
			System.out.println("Enter Union Find Data Structure you would like to use: ");
			UFType = s.next();
		} while (!UFTypes.contains(UFType));
		return UFType;
	}
	
	/**
	 * Updates the costs[] array to include ith cost
	 * Updates largestCost to be largest single iteration cost encountered so far
	 * @param i
	 */
	public void update(int i) {
		totalCost = arrayHits();
		costs.add(i, totalCost - prevCost);
		prevCost = totalCost;
		largestCost = Math.max(largestCost, costs.get(i));
	}
	
	public static void main(String[] args) {
		AmortizedCostUF a = new AmortizedCostUF();
		String UFType = a.getDataStructure();
		Scanner input = a.getInput();
		//a.s.close(); //close the scanner reading from standard input
		int N = input.nextInt();
		a.parseUF(UFType, N); //creates appropriate UF object
		a.setPrevCost(a.arrayHits());
		a.add(0, a.prevCost);
		for (int i = 1; input.hasNext(); i++) {
			int p = input.nextInt();
			int q = input.nextInt();
			if (a.connected(p,q)) { a.update(i); }
			else {
				a.union(p, q);
				a.update(i);
				System.out.println(p + " " + q);
			}
		}
		a.plot();
		System.out.println(a.count() + " components");
	}
	
	/*public static void main(String[] args) {
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
	}*/
	
	
}
