
public interface UF {
	/**
	 * 
	 * @param p
	 * @param q
	 * @return true if p and q in same set else false
	 */
	boolean connected(int p, int q);
	/**
	 * Union p and q if not already in same set
	 * @param p
	 * @param q
	 */
	void union(int p, int q);
	/**
	 * 
	 * @param p
	 * @return parent root 
	 */
	int find(int p);
	/**
	 * 
	 * @return Number of mutually disconnected components
	 */
	int count();
	/**
	 * 
	 * @return Total number of array accesses
	 */
	int arrayHits();

}
