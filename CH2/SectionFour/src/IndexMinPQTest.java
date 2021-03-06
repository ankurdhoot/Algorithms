import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class IndexMinPQTest {
	private IndexMinPQ<String> pq;
	
	@Before
	public void setUp() {
		System.out.println("Calling setup.");
		this.pq = new IndexMinPQ<String>(10);
		pq.insert(1, "z");
		pq.insert(2, "y");
		pq.insert(3, "x");
	}

	@Test
	public void testChangeKey() {
		System.out.println("Calling testChangeKey().");
		pq.changeKey(1, "a");
		assertEquals(1, pq.minIndex());
		assertEquals("y", pq.keyOf(2));
	}

	@Test
	public void testContains() {
		assertEquals(true, pq.contains(1));
		assertEquals(true, pq.contains(2));
		assertEquals(false, pq.contains(4));
		assertEquals(false, pq.contains(0));
	}

	@Test
	public void testDelete() {
		pq.delete(3);
		assertEquals(2, pq.minIndex());
		assertEquals("y", pq.minKey());
		pq.delete(2);
		assertEquals(1, pq.minIndex());
		assertEquals("z", pq.minKey());
	}


	@Test
	public void testDelMin() {
		assertEquals(3, pq.delMin());
	}

	@Test
	public void testIsEmpty() {
		assertEquals(false, pq.isEmpty());
		pq.delMin();
		pq.delMin();
		assertEquals(false, pq.isEmpty());
		pq.delMin();
		assertEquals(true, pq.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(3, pq.size());
		pq.delMin();
		assertEquals(2, pq.size());
	}

}
