import sorts.*;

public class PlotTimes {
	private final static int numDoubles = 6; //number of times to double array size
	private final static int T = 25; //number of times to repeat each trial
	private final static int START = 128;
	
	public static void main(String[] args) {
		double[][] runTimes = new double[args.length][numDoubles];
		for (int i = 0; i < args.length; i++) {
			String alg = args[i];
			double[] times = getTimes(alg, START);
			runTimes[i] = times;
		}
		plot(args, runTimes);
	}
	
	public static double[] getTimes(String alg, int start) {
		int doubler = 0;
		int N = start;
		double[] times = new double[numDoubles];
		for (; doubler < numDoubles; doubler++, N *= 2) {
			double time = SortCompare.timeRandomInput(alg, N, T);
			times[doubler] = time;
		}
		return times;
	}
	
	public static void plot(String[] algs, double[][] runTimes) {
		int N = algs.length;
		StdDraw.setXscale(-1, START * Math.pow(2,  numDoubles - 1 ) + 10);
		StdDraw.setYscale(); //normalized runtime values
		StdDraw.setPenRadius(.01);
		double maxValue = 0;  //used to normalize all the runTimes
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < numDoubles; j++) {
				if (runTimes[i][j] > maxValue) { maxValue = runTimes[i][j]; }  
			}
		}
		for (int i = 0; i < N; i++) {
			StdDraw.setPenColor(60*i, 255 - 45 * i, 30 * i);
			StdDraw.textLeft(1, .9 - (double)i /algs.length, algs[i]);
			for (int j = 0; j < numDoubles; j++) {
				StdDraw.point(START * Math.pow(2,j), runTimes[i][j] / maxValue);
			}
		}
	}
}
