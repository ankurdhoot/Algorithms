import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class BST<Key extends Comparable<Key>, Value> {
	
	private Node root;
	
	public class Node {
		private Key key;
		private Value val;
		private Node left, right;
		private int N;
		
		public Node(Key key, Value val, int N) {
			this.key = key;
			this.val = val;
			this.N = N;
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
		root = put(root, key, val);
		assert check();
	}
	
	private Node put(Node x, Key key, Value val) {
		if (x == null) return new Node(key, val, 1);
		int cmp = key.compareTo(x.key);
		if (cmp < 0)      x.left = put(x.left, key, val);
		else if (cmp > 0) x.right = put(x.right, key, val);
		else 			  x.val = val;
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	public void deleteMin() {
		if (isEmpty()) throw new NoSuchElementException("Cannot delete minimum from empty tree.");
		root = deleteMin(root);
		assert check();
	}
	
	private Node deleteMin(Node x) {
		if (x.left == null) return x.right;
		else x.left = deleteMin(x.left);
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	public void deleteMax() {
		if (isEmpty()) throw new NoSuchElementException("Cannot delete maximum from empty tree.");
		root = deleteMax(root);
		assert check();
	}
	
	private Node deleteMax(Node x) {
		if (x.right == null) return x.left;
		else x.right = deleteMax(x.right);
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	public void delete(Key key) {
		root = delete(root, key);
		assert check();
	}
	
	private Node delete(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0)      x.left = delete(x.left, key);
		else if (cmp > 0) x.right = delete(x.right, key);
		else {
			if (x.right == null) return x.left;
			if (x.left == null)  return x.right;
			Node t = x;
			x = min(t.right);
			x.right = deleteMin(t.right);
			x.left = t.left;
		}
		x.N = size(x.left) + size(x.right) + 1;
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
		Node t = ceiling(x.left, key);
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
		if (x == null) return -1;
		return 1 + Math.max(height(x.left), height(x.right));
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
	
	private boolean check() {
		if (!isBST()) System.out.println("Not in symmetric order");
		if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
		if (!isRankConsistent()) System.out.println("Ranks not consistent");
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
		BST<String, Integer> st = new BST<String, Integer>();
		Scanner s = new Scanner(System.in);
		for (int i = 0; s.hasNext(); i++) {
			String key = s.next();
			st.put(key, i);
		}
		
		for (String key : st.levelOrder()) 
			System.out.println(key + " " + st.get(key));
		System.out.println();
		
		for (String key : st.keys()) 
			System.out.println(key + " " + st.get(key));
		
		s.close();
	}
}
