import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerProducer {
    private static Buffer buffer = new Buffer();

    public static void main(String[] args) {
        ExecutorService excecutor = Executors.newFixedThreadPool(2);
        excecutor.execute(new ProducerTas());
        excecutor.execute(new ConsumerTask());
        excecutor.shutdown();
    }

    private static class ProducerTas implements Runnable {
        public void run() {
            try {
                int i = 1;
                while (true) {
                    System.out.println("producer write" + 1);
                    buffer.write(i++);
                    Thread.sleep((int) (Math.random() * 10000));
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class ConsumerTask implements Runnable {
        public void run() {
            try {
                while (true) {
                    System.out.println("tConsumer read" + buffer.read());
                    Thread.sleep((int) (Math.random() * 10000));
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class Buffer {
        private static final int CAPACITY = 1;
        private LinkedList<Integer> queue = new LinkedList<>();
        private static Lock lock = new ReentrantLock();
        private Condition notEmty = lock.newCondition();
        private static Condition notFull = lock.newCondition();

        public void write(int value) {
            lock.lock();
            try {
                while (queue.size() == CAPACITY) {
                    System.out.println("wait for notFull condition");
                    notFull.await();
                }
                queue.offer(value);
                notEmty.signal();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public int read() {
            int value = 0;
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    System.out.println("wait for notEmpty condition");
                    notEmty.wait();
                }
                value = queue.remove();
                notFull.signal();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
                return value;
            }
        }
    }
}
