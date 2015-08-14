
public class Knuth {
	public static double[][] shuffle(int M, int N) {
		double[][] stats = new double[M][M];
		for (int trial = 0; trial < N; trial++) {
			int[] a = new int[M];
			for (int i = 0; i < a.length; i++) {
				a[i] = i;
			}
			for (int j = 0; j < a.length; j++) {
				int r = j + StdRandom.uniform(a.length - j);
				int temp = a[j];
				a[j] = a[r];
				a[r] = temp;
			}
			for (int k = 0; k < a.length; k++) {
				int s = a[k];
				stats[s][k]++;
			}
		}
		return stats;
	}
	
	public static double[][] badShuffle(int M, int N) {
		double[][] stats = new double[M][M];
		for (int trial = 0; trial < N; trial++) {
			int[] a = new int[M];
			for (int i = 0; i < a.length; i++) {
				a[i] = i;
			}
			for (int j = 0; j < a.length; j++) {
				int r = StdRandom.uniform(a.length);
				int temp = a[j];
				a[j] = a[r];
				a[r] = temp;
			}
			for (int k = 0; k < a.length; k++) {
				int s = a[k];
				stats[s][k]++;
			}
		}
		return stats;
	}
	
	public static void print(double[][] stats, int N) {
		for (int i = 0; i < stats.length; i++) {
			for (int j = 0; j < stats.length; j++) {
				System.out.printf("%6.4f ", stats[i][j]/N);
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		int M = Integer.parseInt(args[0]);
		int N = Integer.parseInt(args[1]);
		double[][] stats = shuffle(M,N);
		System.out.println("Good Stats: ");
		print(stats, N);
		double[][] badStats = badShuffle(M,N);
		System.out.println("Bad Stats: ");
		print(badStats, N);
	}
}
