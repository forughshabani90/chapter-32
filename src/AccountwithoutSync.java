import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountwithoutSync {
    private static Account account = new Account();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executor.execute(new AddPenyTask());
        }
        executor.shutdown();
        while (!executor.isTerminated()) {

        }
        System.out.println("what is balance?" + account.getBalance());
    }

    private static class AddPenyTask implements Runnable {
        public void run() {
            account.deposit(1);
        }
    }

    public static class Account {
        private int balance = 0;

        public int getBalance() {
            return balance;
        }

        public void deposit(int amount) {
            int newbalance = balance + amount;
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {

            }
            balance = newbalance;
        }
    }
}
