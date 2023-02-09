import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sequence {
    private static final Logger log = LoggerFactory.getLogger(Sequence.class);
    public static final int LAST = 10;
    public static final int FIRST = 1;

    private int number;
    private boolean firstStarted = true;
    private boolean switched = true;

    public static void main(String[] args) throws InterruptedException {
        var sequenceNumber = new Sequence();
        sequenceNumber.start();
    }

    private void start() throws InterruptedException {
        var thread1 = new Thread(this::generateFirstSequence);
        var thread2 = new Thread(this::generateSecondSequence);


        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    private synchronized void generateFirstSequence() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (!firstStarted) {
                    wait();
                }
                if (number == LAST) {
                    switched = false;
                }
                if (number == FIRST) {
                    switched = true;
                }
                reverseSeq(switched);
                log.info(String.valueOf(number));
                sleep(1);
                firstStarted = false;
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized void generateSecondSequence() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (firstStarted) {
                    wait();
                }
                log.info(String.valueOf(number));
                sleep(1);
                firstStarted = true;
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void reverseSeq(boolean switched) {
        if (switched) {
            number++;
        } else {
            number--;
        }
    }
}
