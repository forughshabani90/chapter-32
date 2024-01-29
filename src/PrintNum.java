public class PrintNum implements Runnable {
    private int lastname;

    public PrintNum(int n) {
        lastname = n;
    }

    public void run() {
        for (int i = 1; i <= lastname; i++) {
            System.out.println(" " + i);
        }
    }
}
