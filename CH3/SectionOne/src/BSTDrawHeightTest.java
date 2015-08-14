import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class BSTDrawHeightTest {
	private BSTDrawHeight<String, Integer> Hst;
	private BST<String, Integer> st;
	
	@Before
	public void setUp() throws Exception {
		String filename = "t2.txt";
		Hst = new BSTDrawHeight<String, Integer>();
		st = new BST<String, Integer>();
		Scanner s = new Scanner(new File(filename));
		for (int i = 0; s.hasNext(); i++) {
			String key = s.next();
			Hst.put(key,  i);
			st.put(key,  i);
			StdDraw.clear();
			Hst.draw();
			StdDraw.show(200);
		}
		StdDraw.show(5000);
		s.close();
	}

	@Test
	public void testHeight() {
		Iterator<BSTDrawHeight<String, Integer>.Node> HstIterator = Hst.nodes().iterator();
		Iterator<BST<String, Integer>.Node> stIterator = st.nodes().iterator();
		while (stIterator.hasNext()) {
			if (!HstIterator.hasNext()) {
				fail("Iterators contain different number of keys");
			}
			assertEquals("Heights unequal", st.height(stIterator.next()), Hst.height(HstIterator.next()));
		}
	}
	
	@Test
	public void testDeleteMin() {
		System.out.println("Deleting minimum");
		StdDraw.show(1000);
		StdDraw.clear();
		Hst.deleteMin();
		Hst.draw();
		StdDraw.show(1000);
	}
	
	@Test public void testDeleteMax() {
		System.out.println("Deleting maximum");
		StdDraw.show(1000);
		StdDraw.clear();
		Hst.deleteMax();
		Hst.draw();
		StdDraw.show(1000);
	}
	
	@Test 
	public void testDelete() {
		for (String key : st.keys()) {
			StdDraw.clear();
			Hst.delete(key);
			Hst.draw();
			StdDraw.show(1000);
		}
	}

}
