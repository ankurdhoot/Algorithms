import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

//TODO
//Implement delete and deleteMax

public class RedBlackBST<Key extends Comparable<Key>, Value> {
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	private static final double RADIUS = .5;
	
	private boolean drawSteps;  //if true, intermediate steps in delete will be drawn
	private int delay;          //time to show each step; StdDraw.show(delay);
	
	private int leftmost;    //x coordinate of leftmost node
	private int rightmost;   //x coordinate of rightmost node
	
	private Key lastKey;  //latest key called with put()
	private Node root;
	
	public RedBlackBST() {
		StdDraw.setCanvasSize(1600, 900);
		lastKey = null;
	}
	private class Node {
		Key key;
		Value val;
		Node left, right;
		int N;      			//subtree count
		boolean color;          //color of parent link
		
		public Node(Key key, Value val, boolean color, int N) {
			this.key = key;
			this.val = val;
			this.color = color;
			this.N = N;
		}
	}
	
	private boolean isRed(Node x) {
		if (x == null) return false;
		return x.color == RED;
	}
	
	private int size(Node x) {
		if (x == null) return 0;
		return x.N;
	}
	
	public int size() { return size(root); }
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public Value get(Key key) { return get(root, key); }
	
	private Value get(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0) return get(x.left, key);
		if (cmp > 0) return get(x.right, key);
		else 		 return x.val;
	}
	
	public boolean contains(Key key) {
		return get(key) != null;
	}
	
	public void put(Key key, Value val) {
		root = put(root, key, val);
		root.color = BLACK;
		lastKey = key;
		assert check();
	}

	private Node put(Node h, Key key, Value val) {
		if (h == null) return new Node(key, val, RED, 1);
		
		int cmp = key.compareTo(h.key);
		if 		(cmp < 0) h.left  = put(h.left, key, val);
		else if (cmp > 0) h.right = put(h.right, key, val);
		else 			  h.val = val;
		
		if (isRed(h.right) && (!isRed(h.left)))    h = rotateLeft(h);
		if (isRed(h.left)  && isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left)  && isRed(h.right))     flipColors(h);
		h.N = size(h.left) + size(h.right) + 1;
		
		return h;
	}
	
	private Node rotateRight(Node h) {
		assert (h != null) && isRed(h.left);
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color; //equivalent to x.right.color
		h.color = RED;
		x.N = h.N;
		h.N = size(h.left) + size(h.right) + 1;
		return x;
	}
	
	private Node rotateLeft(Node h) {
		assert (h != null) && isRed(h.right);
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		x.N = h.N;
		h.N = size(h.left) + size(h.right) + 1;
		return x;
	}
	
	/********************************
	 * Needs to be changed for deletions
	 */
	private void flipColors(Node h) {
		assert (h != null) && (h.left != null) && (h.right) != null;
		assert (!isRed(h)) && isRed(h.left) && isRed(h.right) 
		    || (isRed(h)) && !isRed(h.left) && !isRed(h.right);
		h.color = !h.color;;
		h.left.color = !h.left.color;
		h.right.color = !h.right.color;
	}
	
	public int height() { return height(root); }
	
	private int height(Node x) {
		if (x == null) return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}
	
	public Key min() {
		if (isEmpty()) return null;
		return min(root).key;
	}
	
	private Node min(Node x) {
		if (x.left == null) return x;
		else 				return min(x.left);
	}
	
	public Key max() {
		if (isEmpty()) return null;
		return max(root).key;
	}
	
	private Node max(Node x) {
		if (x.right == null) return x;
		else				 return max(x.right);
	}
	
	public Key floor(Key key) {
		Node x = floor(root, key);
		if (x == null) return null;
		else           return x.key;
	}
	
	public Node floor(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0) return x;
		if (cmp < 0) return floor(x.left, key);
		Node t = floor(x.right, key);
		if (t != null) return t;
		else 		   return x;
	}
	
	public Key ceiling(Key key) {
		Node x = ceiling(root, key);
		if (x == null) return null;
		else 		   return x.key;
	}
	
	private Node ceiling(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0) return x;
		if (cmp > 0) return ceiling(x.right, key);
		Node t = ceiling(x.left, key);
		if (t != null) return t;
		else 		   return x;
	}
	
	public Key select(int k) {
		if (k < 0 || k >= size()) return null;
		Node x = select(root, k);
		return x.key;
	}
	
	private Node select(Node x, int k) {
		int t = size(x.left);
		if      (t > k) return select(x.left, k);
		else if (t < k) return select(x.right, k - t- 1);
		else            return x;
	}
	
	public int rank(Key key) {
		return rank(key, root);
	}
	
	private int rank(Key key, Node x) {
		if (x == null) return 0;
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) return rank(key, x.left);
		else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
		else              return size(x.left);
	}
	
	public Iterable<Key> keys() {
		return keys(min(), max());
	}
	
	public Iterable<Key> keys(Key lo, Key hi) {
		Queue<Key> queue = new LinkedList<Key>();
		keys(root, queue, lo, hi);
		return queue;
	}
	
	private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
		if (x == null) return;
		int cmplo = lo.compareTo(x.key);
		int cmphi = hi.compareTo(x.key);
		if (cmplo < 0) keys(x.left, queue, lo, hi);
		if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
		if (cmphi > 0) keys(x.right, queue, lo, hi);
	}
	
	public int size(Key lo, Key hi) {
		if (lo.compareTo(hi) > 0) return 0;
		if (contains(hi)) return rank(hi) - rank(lo) + 1;
		else              return rank(hi) - rank(lo);
	}
	
	private boolean check() {
        if (!isBST())            StdOut.println("Not in symmetric order");
        if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        if (!is23())             StdOut.println("Not a 2-3 tree");
        if (!isBalanced())       StdOut.println("Not balanced");
        return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
    }
	
	private boolean isBST() {
        return isBST(root, null, null);
    }
	
	private boolean isBST(Node x, Key min, Key max) {
		if (x == null) return true;
		if (min != null && x.key.compareTo(min) <= 0) return false;
		if (max != null && x.key.compareTo(max) >= 0) return false;
		return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
	}
	
	private boolean isSizeConsistent() { return isSizeConsistent(root); }
	
	 private boolean isSizeConsistent(Node x) {
         if (x == null) return true;
         if (x.N != size(x.left) + size(x.right) + 1) return false;
         return isSizeConsistent(x.left) && isSizeConsistent(x.right);
	 } 
	 
	 private boolean isRankConsistent() {
         for (int i = 0; i < size(); i++)
             if (i != rank(select(i))) return false;
         for (Key key : keys())
             if (key.compareTo(select(rank(key))) != 0) return false;
         return true;
     }
	 
	 private boolean is23() { return is23(root); }
	 
	 private boolean is23(Node x) {
		 if (x == null) return true;
		 if (isRed(x.right)) return false;
		 if (x != root && isRed(x) && isRed(x.left)) 
			 return false;
		 return is23(x.left) && is23(x.right);
	 }
	 
	 private boolean isBalanced() {
		 int black = 0;
		 Node x = root;
		 while (x != null) {
			 if (!isRed(x)) black++;
			 x = x.left;
		 }
		 return isBalanced(root, black);
	 }
	 
	 private boolean isBalanced(Node x, int black) {
		 if (x == null) return (black == 0);
		 if (!isRed(x)) black--;
		 return isBalanced(x.left, black) && isBalanced(x.right, black);
	 }
	 
	 /*************************************************************
	  * DRAWING METHODS
	  */
	 
	 public void draw() {
		 StdDraw.clear();
		 initializePlot(root);
		 draw(root, 0, 0);
		 StdDraw.show(delay);
	 }
	 
	 public void drawAndWait(Node node, int x, int y) {
		 while (!StdDraw.mousePressed()) {} 
		 draw(node, x, y, 0);
		 while (StdDraw.mousePressed()) {};
	 }
	 
	 private void draw(Node node, int x, int y, int delay) {
		 StdDraw.clear();
		 initializePlot(node);
		 draw(node, x, y);
		 StdDraw.show(delay);
	 }
	 
	 private void draw(Node node, int x, int y) {
		 if (node == null) return;
		 if (isRed(node)) { StdDraw.setPenColor(StdDraw.BOOK_RED); StdDraw.setPenRadius(.01); }
		 else 			  { StdDraw.setPenColor(StdDraw.BLACK);    StdDraw.setPenRadius(.005); }
		 StdDraw.circle(x, y, RADIUS);
		 StdDraw.text(x, y, node.key.toString());
		 int h = height(node);
		 double d =  Math.pow(2, h - 1);
		 if (isRed(node.left))  { StdDraw.setPenColor(StdDraw.BOOK_RED); StdDraw.setPenRadius(.01); }
		 else 					{ StdDraw.setPenColor(StdDraw.BLACK);    StdDraw.setPenRadius(.005); }
		 StdDraw.line(x, y - .5, x - d, y - 1.5);
		 if (isRed(node.right)) { StdDraw.setPenColor(StdDraw.BOOK_RED); StdDraw.setPenRadius(.01); }
		 else                   { StdDraw.setPenColor(StdDraw.BLACK);    StdDraw.setPenRadius(.005); }
		 StdDraw.line(x, y - .5, x + d, y - 1.5);
		 
		 draw(node.left,  (int) (x - d), y - 2);   //could use 2 * (height(x.left) - height(root)) for y coordinate?
		 draw(node.right, (int) (x + d), y - 2);
	 }
	 
	 private void computeXscale(Node node, int x) {
		 if (node == null) return;
		 if (x < leftmost)  leftmost  = x;
		 if (x > rightmost) rightmost = x;
		 int h = height(node);
		 int d = (int) Math.pow(2, h - 1);  //horizontal distance between the parent and child node
		 computeXscale(node.left,  x - d);
		 computeXscale(node.right, x + d);
	 }
	 
	 private void initializePlot(Node x) {
		 leftmost = 0; rightmost = 0;
		 computeXscale(x, 0);
		 StdDraw.setPenRadius(.005);
		 StdDraw.setYscale(-2 * height(x) - 1, 1);
		 StdDraw.setXscale(leftmost - 1, rightmost + 1);
		 StdDraw.textLeft(leftmost - .5, 0, "Last Key " + lastKey.toString());
	 }
	 
	 public void setDrawSteps(boolean b, int delay) {
		 drawSteps = b;
		 this.delay = delay;
	 }
	 
	 /********************************************************************
	  * DELETION METHODS
	  */
	 
	 public void deleteMin() {
		 if (isEmpty()) throw new NoSuchElementException("BST underflow");
		 
		 if (!isRed(root.left) && !isRed(root.right))   //ensures that flipping colors on root follows flipColors invariant
			 root.color = RED;
		 root = deleteMin(root);
		 if (!isEmpty()) root.color = BLACK;
		 if (drawSteps) draw();
		 assert check();
	 }
	 
	 private Node deleteMin(Node h) {
		 if (h.left == null)
			 return null;
		 if (drawSteps) drawAndWait(h, 0, 0);
		 if (!isRed(h.left) && !isRed(h.left.left))
			 h = moveRedLeft(h);
		 
		 h.left = deleteMin(h.left);
		 if (drawSteps) drawAndWait(h, 0, 0);
		 return balance(h);
	 }
	 
	 private Node moveRedLeft(Node h) {
		 assert (h != null);
		 assert (isRed(h) && !isRed(h.left) && !isRed(h.left.left));
		 
		 flipColors(h);
		 if (drawSteps) drawAndWait(h, 0, 0);
		 if (isRed(h.right.left)) {
			 h.right = rotateRight(h.right); 
			 if (drawSteps) drawAndWait(h, 0, 0);

			 h = rotateLeft(h);
			 if (drawSteps) drawAndWait(h, 0, 0);
			 
			 flipColors(h);
			 if (drawSteps) drawAndWait(h, 0, 0);
		 }
		 return h;
	 }
	 
	 private Node balance(Node h) {
		 assert (h != null);
		 
		 if (isRed(h.right))  {
			 h = rotateLeft(h); 
			 if (drawSteps) drawAndWait(h, 0, 0);
		 }
		 //why not check && (!isRed(h.left))

		 
		 if (isRed(h.left) && isRed(h.left.left)) {
			 h = rotateRight(h);
			 if (drawSteps) drawAndWait(h, 0, 0);
		 }
		 if (isRed(h.left) && isRed(h.right)) {
			 flipColors(h);
			 if (drawSteps) drawAndWait(h, 0, 0);
		 }
		 
		 h.N = size(h.left) + size(h.right) + 1;
		 return h;
	 }
	 
     public static void main(String[] args) { 
         RedBlackBST<String, Integer> st = new RedBlackBST<String, Integer>();
         for (int i = 0; !StdIn.isEmpty(); i++) {
             String key = StdIn.readString();
             st.put(key, i);
         }
         for (String s : st.keys())
             StdOut.println(s + " " + st.get(s));
         StdOut.println();
     }
	 
}