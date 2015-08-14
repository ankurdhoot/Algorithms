import java.util.ArrayList;
import java.util.Arrays;


public class TestClone {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Double[] a = new Double[] {1., 2., 3., 4.};
		Double[] b = a.clone();
		System.out.println(Arrays.toString(b));
		System.out.println(System.identityHashCode(b[0]));
		b[0] = 5.;
		System.out.println(a[0]);
		System.out.println(System.identityHashCode(a[0]));
		System.out.println(b[0]);
		System.out.println(System.identityHashCode(b[0]));
		
		String[] c = new String[] {"Hello", "World"};
		String[] d = c.clone();
		System.out.println(Arrays.toString(d));
		c[0] = "Goodbye";
		System.out.println(c[0]);
		System.out.println(d[0]);
		
		ArrayList[] array = new ArrayList[] {new ArrayList() , new ArrayList()};
		ArrayList[] clone = array.clone();
		for (int i = 0; i < clone.length; i ++) {
		    System.out.println(System.identityHashCode(array[i]));
		    System.out.println(System.identityHashCode(clone[i]));
		    System.out.println(System.identityHashCode(array[i].clone()));
		    System.out.println("-----");
		
		}
		ArrayList f = new ArrayList() {{add(1); add(2);}};
		ArrayList g = new ArrayList() {{add(3); add(4);}};
		ArrayList[] s = new ArrayList[] {f, g};
		ArrayList[] t = s.clone();
		s[0].set(0, 10);
		System.out.println(s[0]);
		System.out.println(t[0]);
	}

}
