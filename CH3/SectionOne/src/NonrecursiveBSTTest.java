import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;


public class NonrecursiveBSTTest {
	private NonrecursiveBST<String, Integer> Nst;
	private BST<String, Integer> st;
	
	@Before
	public void setUp() throws Exception {
		String filename = "t1.txt";
		Nst = new NonrecursiveBST<String, Integer>();
		st = new BST<String, Integer>();
		Scanner s = new Scanner(new File(filename));
		//build recursive and nonrecursive BST
		for (int i = 0; s.hasNext(); i++) {
			String key = s.next();
			Nst.put(key,  i);
			st.put(key,  i);
		}
		s.close();
	}
	
	@Test
	public void printKeys() {
		for (String key : Nst.keys("A", "Z")) {
			System.out.println(key);
		}
		System.out.println();
		for (String key : st.keys("A", "Z")) {
			System.out.println(key);
		}
	}

	@Test
	public void testIterators() {
		Iterator<String> NstIterator = Nst.keys().iterator();
		Iterator<String> stIterator = st.keys().iterator();
		while (stIterator.hasNext()) {
			if (!NstIterator.hasNext()) {
				fail("Iterators contain different number of keys");
			}
			assertEquals("Keys unequal", stIterator.next(), NstIterator.next());
		}
	}
	
	@Test 
	public void testSize() {
		Iterator<String> NstIterator = Nst.keys().iterator();
		Iterator<String> stIterator = st.keys().iterator();
		while (stIterator.hasNext()) {
			if (!NstIterator.hasNext()) {
				fail("Iterators contain different number of keys");
			}
			assertEquals("Sizes unequal", st.size(st.getNode(stIterator.next())), Nst.size(Nst.getNode(NstIterator.next())));
		}
	}
	
	@Test 
	public void testGet() {
		Iterator<String> NstIterator = Nst.keys().iterator();
		Iterator<String> stIterator = st.keys().iterator();
		while (stIterator.hasNext()) {
			if (!NstIterator.hasNext()) {
				fail("Iterators contain different number of keys");
			}
			assertEquals("Got different Node values", st.get(stIterator.next()), Nst.get(NstIterator.next()));
		}
	} 
	
	@Test 
	public void testMax() {
		assertEquals("Got different max ", st.max(), Nst.max());
	}
	
	@Test 
	public void testMin() {
		assertEquals("Got different min ", st.min(), Nst.min());
	}
	
	@Test 
	public void testFloor() {
		for (char s = 'A'; s <= 'Z'; s++) {
			String t = String.valueOf(s);
			assertEquals("Got different floor values ", st.floor(t), Nst.floor(t));
		}
	}
	
	@Test 
	public void testCeiling() {
		System.out.println(st.ceiling("B"));
		for (char s = 'A'; s <= 'Z'; s++) {
			String t = String.valueOf(s);
			assertEquals("Got different ceiling values for Key " + t, st.ceiling(t), Nst.ceiling(t));
		}
	}
	
	@Test
	public void testRank() {
		Iterator<String> NstIterator = Nst.keys().iterator();
		Iterator<String> stIterator = st.keys().iterator();
		while (stIterator.hasNext()) {
			if (!NstIterator.hasNext()) {
				fail("Iterators contain different number of keys");
			}
			assertEquals("Got different rank values ", st.rank(stIterator.next()), Nst.rank(NstIterator.next()));
		}
	}
	
	@Test
	public void testSelect() {
		int N = st.size();
		for (int i = 0; i <= N; i++) {  //no key should be of rank N
			assertEquals("Got different select values ", st.select(i), Nst.select(i));
		}
	}
	
	

}
