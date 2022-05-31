public class Main {

	private static class MyRunnable implements Runnable {

		private int count;
		private String message;

		MyRunnable(int count, String message) {
			this.count = count;
			this.message = message;
		}

		@Override
		public void run() {
			for (int i = 0; i < count; i++) {
				System.out.println(message);
				System.out.flush();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		int count;

		if (args.length != 1) {
			System.out.println("Error");
			return;
		}
		count = parseCount(args[0]);
		if (count <= 0) {
			System.err.println("Error");
		}
		Thread t1 = new Thread(new MyRunnable(count, "Hen"));
		Thread t2 = new Thread(new MyRunnable(count, "Egg"));
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		for (int i = 0; i < count; i++) {
			System.out.println("Human");
		}
	}

	private static int parseCount(String arg) {
		String[] strs = arg.split("=");
		int ret;

		if (strs.length != 2) {
			return -1;
		}
		if (!strs[0].equals("--count")) {
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
