import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

	private static class MyRunnable implements Runnable {

		private final int[] arr;
		private final int end;
		private final int start;
		private final int threadNbr;

		MyRunnable(int[] arr, int start, int end, int threadNbr) {
			this.arr = arr;
			this.start = start;
			this.end = end;
			this.threadNbr = threadNbr;
		}

		@Override
		public void run() {
			int sum = 0;

			for (int i = start; i <= end; i++) {
				sum += arr[i];
			}
			Main.info[threadNbr] = String.format("Thread %d: from %d to %d sum is %d", threadNbr + 1, start, end, sum);
			Main.sums[threadNbr] = sum;
		}
	}

	private static final int MAX_ELEMENTS = 2_000_000;
	private static volatile String[] info;
	private static volatile int[] sums;

	public static void main(String[] args) {
		Random random = new Random();
		int arraySize;
		int threadsCount;
		int[] arr;

		if (args.length != 2) {
			System.out.println("Error");
			return;
		}
		arraySize = parseArraySize(args[0]);
		threadsCount = parseThreadsCount(args[1]);
		if (arraySize <= 0 || threadsCount <= 0 || arraySize > MAX_ELEMENTS || threadsCount > arraySize) {
			System.out.println("Error");
			return;
		}
		arr = new int[arraySize];
		for (int i = 0; i < arraySize; i++) {
			arr[i] = random.nextInt(100);
		}
		System.out.println("Sum: " + Arrays.stream(arr).sum());
		info = new String[threadsCount];
		sums = new int[threadsCount];
		try {
			startThreads(createTreads(arraySize, threadsCount, arr));
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void startThreads(ArrayList<Thread> list) throws InterruptedException {
		for (Thread thread : list) {
			thread.start();
		}
		for (Thread thread : list) {
			thread.join();
		}
		for (String s : info) {
			System.out.println(s);
		}
		System.out.println("Sum by threads: " + Arrays.stream(sums).sum());
	}

	private static ArrayList<Thread> createTreads(int arraySize, int threadsCount, int[] arr) {
		ArrayList<Thread> list = new ArrayList<>(threadsCount);
		int delim = arraySize / threadsCount;
		int start = 0;
		int end = 0;
		int	threadNbr = 0;

		while (end < (arraySize - delim)) {
			end = start + delim;
			list.add(new Thread(new MyRunnable(arr, start, end, threadNbr)));
			start = ++end;
			threadNbr++;
		}
		list.add(new Thread(new MyRunnable(arr, start, arr.length - 1, threadNbr)));
		return list;
	}

	private static int parseArraySize(String s) {
		String[] strs = s.split("=");
		int ret;

		if (strs.length != 2) {
			return -1;
		}
		if (!strs[0].equals("--arraySize")) {
			return -1;
		}
		try {
			ret = Integer.parseInt(strs[1]);
			return ret;
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private static int parseThreadsCount(String s) {
		String[] strs = s.split("=");
		int ret;

		if (strs.length != 2) {
			return -1;
		}
		if (!strs[0].equals("--threadsCount")) {
			return -1;
		}
		try {
			ret = Integer.parseInt(strs[1]);
			return ret;
		} catch (NumberFormatException e) {
			return -1;
		}
	}
}
