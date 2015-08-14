
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class AmortizedCostUFTest {
	private static String file = "largeUF.txt";
	private final static List<String> UFTypes = Arrays.asList("QuickFindUF", "QuickUnionUF", "WeightedQuickUnionUF",
			 									"QuickUnionPathCompressionUF", "WeightedQuickUnionPathCompressionUF", 
			 									"WeightedQuickUnionByHeightUF", "WeightedQuickUnionByHeightPathCompressionUF");
	
	private final static List<String> Linearithmic = Arrays.asList("WeightedQuickUnionUF","QuickUnionPathCompressionUF", 
											"WeightedQuickUnionPathCompressionUF",  "WeightedQuickUnionByHeightUF",											 "WeightedQuickUnionByHeightPathCompressionUF");
	
	public Scanner getInput(String filename) {
		try {
			return new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void test(String UFType) {
		AmortizedCostUF a = new AmortizedCostUF();
		Scanner input;
		if ((input = getInput("data\\" + file)) != null) {
			int N = input.nextInt();
			a.parseUF(UFType, N);
			int cost = a.arrayHits();
			a.setPrevCost(cost);
			a.add(0, cost);
			for (int i = 1; input.hasNext(); i++) {
				int p = input.nextInt();
				int q = input.nextInt();
				if (a.connected(p,q)) { a.update(i); }
				else {
					a.union(p, q);
					a.update(i);
					//System.out.println(p + " " + q);
				}
			}
			//a.plot(UFType, file);  //uncomment for plotting
			//StdDraw.save("graphics\\" + UFType + file + ".jpg");
			System.out.println("Amortized cost for " + UFType + " on " + file + " = " + ((double) a.getTotalCost()) / a.size());
			System.out.println(a.count() + " components");
		}
	}
	
	public static void main(String[] args) {
			AmortizedCostUFTest a = new AmortizedCostUFTest();
			for (String UFType : Linearithmic) {
				a.test(UFType);
			}
			
	}

}
