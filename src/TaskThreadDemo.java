public class TaskThreadDemo {
    public static void main(String[] args) {
        Runnable printB= new PrintChar('a', 100);
        Runnable printA = new PrintChar('b', 100);
        Runnable print100 = new PrintNum(100);

        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printB);
        Thread thread3 = new Thread(print100);

        thread1.start();
        thread2.run();
        thread3.run();

    }

}

