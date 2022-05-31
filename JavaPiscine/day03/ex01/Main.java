public class Main {

	private static class PC {
		private boolean isSaid = false;
		private int count;

		PC(int count) {
			this.count = count;
		}

		public synchronized void egg() {
			for (int i = 0; i < count; i++) {
				while (isSaid) {
					try {
						wait();
					} catch (InterruptedException e) {
						System.out.println(e.getMessage());
						return;
					}
				}
				System.out.println("Egg");
				isSaid = true;
				notify();
			}
		}

		public synchronized void hen() {
			for (int i = 0; i < count; i++) {
				while (!isSaid) {
					try {
						wait();
					} catch (InterruptedException e) {
						System.out.println(e.getMessage());
						return;
					}
				}
				System.out.println("Hen");
				isSaid = false;
				notify();
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
		PC pc = new PC(count);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				pc.egg();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				pc.hen();
			}
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
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
