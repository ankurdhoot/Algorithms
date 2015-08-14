import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * Improvements : 
 * 		Store highest node whose height increased when inserting; Double all the x coordinates in the subtree 
 * 		of that node; set lowest node that increased height appropriately
 * 		TODO : Develop recursive version of draw();
 */

public class BSTDrawHeight<Key extends Comparable<Key>, Value> {
	private static final double UNIT = 1;
	private static final double RADIUS = .75 * UNIT;
	private Node root;
	
	public BSTDrawHeight() {
		StdDraw.setCanvasSize(1800, 900);
	}
	
	public class Node {
		private Key key;
		private Value val;
		private Node left, right;
		private int N;
		private int height;
		private int level;
		private double x;
		private double y;
		
		public Node(Key key, Value val, int N, int height, int level) {
			this.key = key;
			this.val = val;
			this.N = N;
			this.height = height;
			this.level = level;
			this.x = 0;
			this.y = 0;
		}
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int size() {
		return size(root);
	}
	
	public int size(Node x) {
		if (x == null) return 0;
		else return x.N;
	}
	
	public boolean contains(Key key) {
		return get(key) != null;
	}
	
	public Value get(Key key) {
		return get(root, key);
	}
	
	private Value get(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0) return get(x.left, key);
		if (cmp > 0) return get(x.right, key);
		else 		 return x.val;	
	}
	
	public Node getNode(Key key) {
		if (root == null) return null;
		Node x = root;
		while (true) {
			if (x == null) return null;
			int cmp = key.compareTo(x.key);
			if (cmp == 0) return x;
			if (cmp < 0) x = x.left;
			if (cmp > 0) x = x.right;
		}
	}
	
	public void put(Key key, Value val) {
		if (val == null) { delete(key); return; }
		root = put(root, key, val, 0);
		recomputeCoordinates();
		assert check();
	}
	
	private Node put(Node x, Key key, Value val, int level) {
		if (x == null) { 
			System.out.println("Adding key : " + key + " at level " + level);
			return new Node(key, val, 1, 0, level);
		}
		int cmp = key.compareTo(x.key);
		//Do something like set(x.left, x.x - 2^(height - x.left.level), x.y - 2)
		if (cmp < 0)      x.left = put(x.left, key, val, level + 1);
		else if (cmp > 0) x.right = put(x.right, key, val, level + 1);
		else 			  x.val = val;
		x.N = size(x.left) + size(x.right) + 1;
		x.height = Math.max(height(x.left), height(x.right)) + 1;   //keep track of node heights
		return x;
	}
	
	/****          METHODS ASSOCIATED WITH DRAWING              *****
	 */
	
	private void recomputeCoordinates() {
		//Method only needs to be called when height of tree increases if nodes are positioned correctly when inserting
		//horizontal distance between child and parent = 2*(height(root) - child.level))
		for (Node node : levelNodeOrder()) {
			if (node.level == height(root)) continue;  //bottom nodes will be set by parents
			if (node.level == 0) {node.x = 0; node.y = 0; }
			set(node.left, node, "left");
			set(node.right, node, "right");
		}
		System.out.println();
	}
	
	private void set(Node child, Node parent, String direction) {
		if (child == null) return;
		double distance = Math.pow(2, height(root) - child.level); //horizontal distance for child level
		if (direction == "right") { child.x = parent.x + distance * UNIT; child.y = parent.y - 2 * UNIT; }
		else if (direction == "left") { child.x = parent.x - distance * UNIT; child.y = parent.y - 2 * UNIT; }
		else {
			throw new UnsupportedOperationException("Supported directions are right or left.");
		}
	}
	
	/**
	 * Decrements the level field of all nodes rooted at node, Used in delete operations when a node is shifted up
	 * @param node
	 */
	private void decrementLevel(Node node) {
		for (Node x : levelNodeOrder(node)) {
			x.level -= 1;
		}
	}

	public void draw() {
		//set x scale, left and rightmost node are min and max respectively
		if (isEmpty()) return;
		StdDraw.setXscale(min(root).x - 1, max(root).x + 1);
		System.out.println("Setting x scale to " + (min(root).x - 1) + " , " + (max(root).x + 1));
		StdDraw.setYscale(-(height() + 1) * 2 * UNIT, 1);
		System.out.println("Setting y scale to " + -(height() + 1) * 2 * UNIT + " , " +  1);
		//set y scale
		for (Node node : nodes()) {
			System.out.println("( " + node.x + " , " + node.y + " )");
			StdDraw.setPenRadius(.01);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.circle(node.x, node.y, RADIUS);
			StdDraw.setPenColor(StdDraw.BOOK_RED);
			StdDraw.text(node.x, node.y, node.key.toString());
			drawLine(node, node.left);
			drawLine(node, node.right);
		}
	}
	
	private void drawLine(Node parent, Node child) {
		if (child == null) return;
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		StdDraw.setPenRadius(.005);
		StdDraw.line(parent.x, parent.y - RADIUS, child.x, child.y + RADIUS);
	}
	/****              END DRAWING METHODS             ******
	 */
	
	public void deleteMin() {
		if (isEmpty()) throw new NoSuchElementException("Cannot delete minimum from empty tree.");
		root = deleteMin(root);
		assert check();
		recomputeCoordinates();
	}
	
	private Node deleteMin(Node x) {
		if (x.left == null) { decrementLevel(x.right); return x.right; }
		else x.left = deleteMin(x.left);
		x.N = size(x.left) + size(x.right) + 1;
		x.height = Math.max(height(x.left), height(x.right)) + 1;  //update height field
		return x;
	}
	
	public void deleteMax() {
		if (isEmpty()) throw new NoSuchElementException("Cannot delete maximum from empty tree.");
		root = deleteMax(root);
		assert check();
		recomputeCoordinates();
	}
	
	private Node deleteMax(Node x) {
		if (x.right == null) { decrementLevel(x.left); return x.left; }
		else x.right = deleteMax(x.right);
		x.N = size(x.left) + size(x.right) + 1;
		x.height = Math.max(height(x.left), height(x.right)) + 1; //update height field
		return x;
	}
	
	public void delete(Key key) {
		System.out.println("Deleting " + key);
		root = delete(root, key);
		assert check();
		recomputeCoordinates();
	}
	
	private Node delete(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0)      x.left = delete(x.left, key);
		else if (cmp > 0) x.right = delete(x.right, key);
		else {
			if (x.right == null) {
				decrementLevel(x.left);   //decrease level of subtree rooted at x.left since that subtree is shifting up
				return x.left;
			}
			if (x.left == null)  {
				decrementLevel(x.right);
				return x.right;
			}
			Node t = x;
			x = min(t.right);
			x.x = t.x;
			x.y = t.y;
			x.level = t.level;
			x.right = deleteMin(t.right);
			x.left = t.left;
		}
		x.N = size(x.left) + size(x.right) + 1;
		x.height = Math.max(height(x.left), height(x.right)) + 1;
		return x;
	}
	
	public Key min() {
		if (isEmpty()) return null;
		return min(root).key;
	}
	
	private Node min(Node x) {
		if (x.left == null) return x;
		else      return min(x.left);
	}
	
	public Key max() {
		if (isEmpty()) return null;
		return max(root).key;
	}
	
	private Node max(Node x) {
		if (x.right == null) return x;
		else    return (max(x.right));
	}
	
	public Key floor(Key key) {
		Node x = floor(root, key);
		if (x == null) return null;
		else return x.key;
	}
	
	private Node floor(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key); 
		if (cmp == 0) return x;
		else if (cmp < 0) return floor(x.left, key);
		Node t = floor(x.right, key);
		if (t != null) return t;
		return x;
	}
	
	public Key ceiling(Key key) {
		Node x = ceiling(root, key);
		if (x == null) return null;
		else return x.key;
	}
	
	private Node ceiling(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0) return x;
		else if (cmp > 0) return ceiling(x.right, key);
		Node t = floor(x.left, key);
		if (t != null) return t;
		return x;
	}
	
	public Key select(int k) {
		if (k < 0 || k >= size()) return null;
		Node x = select(root, k);
		return x.key;
	}
	
	private Node select(Node x, int k) {
		if (x == null) return null;
		int t = size(x.left);
		if      (t > k) return select(x.left, k);
		else if (t < k) return select(x.right, k-t-1);
		else 			return x;
	}
	
	public int rank(Key key) {
		return rank(root, key);
	}
	
	private int rank(Node x, Key key) {
		if (x == null) return 0;
		int cmp = key.compareTo(x.key);
		if (cmp == 0) return size(x.left);
		if (cmp > 0) return size(x.left) + 1 + rank(x.right, key);
		else 		 return rank(x.left, key);
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
	
	public int height() {
		return height(root);
	}
	
	public int height(Node x) {
		if (x == null) return -1; //necessary if inserting already existing key with no children
		return x.height;
	}
	
	public Iterable<Key> levelOrder() {
		Queue<Key> keys = new LinkedList<Key>();
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node x = queue.remove();
			if (x == null) continue;
			keys.add(x.key);
			queue.add(x.left);
			queue.add(x.right);
		}
		return keys;
	}
	
	public Iterable<Node> levelNodeOrder() {
		return levelNodeOrder(root);
	}
	/**
	 * Level order traversal of nodes
	 * @return
	 */
	private Iterable<Node> levelNodeOrder(Node node) {
		Queue<Node> nodes = new LinkedList<Node>();
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(node);
		while (!queue.isEmpty()) {
			Node x = queue.remove();
			if (x == null) continue;
			nodes.add(x);
			queue.add(x.left);
			queue.add(x.right);
		}
		return nodes;
	}
	
	
	public Iterable<Node> nodes() {
		Queue<Node> queue = new LinkedList<Node>();
		Stack<Node> stack = new Stack<Node>();
		Node x = root;
		while (!(x == null) || !stack.isEmpty()) {
			if (x == null) {
				x = stack.pop();
				queue.add(x);
				x = x.right;
			}
			else {
				stack.push(x);
				x = x.left;
			}
		}
		return queue;
	}
	
	private boolean check() {
		if (root == null) return true;
		if (!isBST()) System.out.println("Not in symmetric order");
		if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
		if (!isRankConsistent()) System.out.println("Ranks not consistent");
		if (!isHeightConsistent()) System.out.println("Heights not consistent");
		if (!isLevelConsistent()) System.out.println("Levels not consistent");
		return isBST() && isSizeConsistent() && isRankConsistent();
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
	
	private boolean isHeightConsistent() {
		return isHeightConsistent(root);
	}
	
	private boolean isHeightConsistent(Node x) {
		if (x == null) return true;
		if (height(x) != Math.max(height(x.left), height(x.right)) + 1) return false;
		return isHeightConsistent(x.left) && isHeightConsistent(x.right);
	}
	
	private boolean isLevelConsistent() {
		return isLevelConsistent(root);
	}
	
	private boolean isLevelConsistent(Node x) {
		if (x.left == null && x.right == null) return true;
		if (x.left == null) {
			if (x.right.level != x.level + 1) return false;
			return isLevelConsistent(x.right);
		}
		if (x.right == null) {
			if (x.left.level != x.level + 1) return false;
			return isLevelConsistent(x.left);
		}
		else {
			if ((x.right.level != x.level + 1) || (x.left.level != x.level + 1)) return false;
			return isLevelConsistent(x.left) && isLevelConsistent(x.right);
		}
	}
	private boolean isSizeConsistent() {
		return isSizeConsistent(root);
	}
	
	private boolean isSizeConsistent(Node x) {
		if (x == null) return true;
		if (x.N != size(x.left) + size(x.right) + 1) return false;
		return isSizeConsistent(x.left) && isSizeConsistent(x.right);
	}
	
	private boolean isRankConsistent() {
		for (int i = 0; i < size(); i++) {
			if (i != rank(select(i))) return false;
		}
		for (Key key : keys()) 
			if (key.compareTo(select(rank(key))) != 0) return false;
		return true;
	}	
	
	public static void main(String[] args) {
		BSTDrawHeight<String, Integer> st = new BSTDrawHeight<String, Integer>();
		Scanner s = new Scanner(System.in);
		for (int i = 0; s.hasNext(); i++) {
			String key = s.next();
			st.put(key, i);
			StdDraw.clear();
			st.draw();
			StdDraw.show(1000);
		}
		
		for (String key : st.levelOrder()) 
			System.out.println(key + " " + st.get(key));
		System.out.println();
		
		for (String key : st.keys()) 
			System.out.println(key + " " + st.get(key));
		StdDraw.show();
		s.close();
	}
}