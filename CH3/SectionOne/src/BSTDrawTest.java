import java.io.File;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;


public class BSTDrawTest {
	private BSTDraw<String, Integer> st;
	
	@Before
	public void setUp() throws Exception {
		String filename = "t3.txt";
		st = new BSTDraw<String, Integer>();
		Scanner s = new Scanner(new File(filename));
		//build recursive and nonrecursive BST
		for (int i = 0; s.hasNext(); i++) {
			String key = s.next();
			st.put(key,  i);
			StdDraw.clear();
			st.draw();
			StdDraw.show(1000);
		}
		StdDraw.show(100000);
		s.close();
	}
	
	@Test
	public void printKeys() {
		System.out.println();
		for (String key : st.keys("A", "Z")) {
			System.out.println(key);
		}
	}
	
	@Test 
	public void printNodeKeys() {
		for (BSTDraw<String, Integer>.Node node : st.nodes()) {
			System.out.println(node);
		}
	}
	
	@Test
	public void TestDraw() {
		st.draw();
	}
	
	@Test
	public void TestCoordinates() {
		for (BSTDraw<String, Integer>.Node node : st.nodes()) {
			System.out.println("key : " + node.key + " " + node.coordinates());
		}
	}
	
	

}
