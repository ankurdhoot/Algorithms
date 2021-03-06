import java.util.Random;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;


public class RedBlackBSTTest {
	private static Random rng = new Random();
	private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private RedBlackBST<String, Integer> st;
	private Scanner s;
	
	public static String generateString(Random rng, String characters, int length) {
		String s = "";
		for (int i = 0; i < length; i++) {
			s += characters.charAt(rng.nextInt(characters.length()));
			s += " ";
		}
		return s;
	}
	
	public void showSteps(String function) {
		s = new Scanner(System.in);
		System.out.println("Draw Steps in " + function + "? (Y/N)");
		String response = s.nextLine();
		if (response.equals("Y")) {
			System.out.println("Drawing intermediate steps");
			st.setDrawSteps(true);
		}
		else {
			System.out.println("Not drawing intermediate steps");
			st.setDrawSteps(false);
		}
	}

	@Before
	public void setUp() throws Exception {
        st = new RedBlackBST<String, Integer>();
        s = new Scanner(System.in);
	}
	
	@Before
	public void testPut() {
		showSteps("put()");
		String test = generateString(rng, alphabet, 20);
    	System.out.println(test);
        //String test = "L F B J A C H K G I"; 
        String[] keys = test.split(" "); 
        for (int i = 0; i < keys.length; i++) {
            st.put(keys[i], i); 
        }
        st.draw();
	}

	@Test
	public void testDeleteMin() {
		showSteps("deleteMin");
		while (!st.isEmpty()) st.deleteMin();
	}

	@Test
	public void testDeleteMax() {
		showSteps("deleteMax()");
		while (!st.isEmpty()) st.deleteMax();
	}

	@Test
	public void testDeleteKey() {
		showSteps("delete()");
		while (!st.isEmpty()) {
			int N = st.size();
			int randomKeyRank = rng.nextInt(N);
			st.delete(st.select(randomKeyRank));
		}
	}

}
