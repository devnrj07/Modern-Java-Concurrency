import java.util.Arrays;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class FlowDemo {
    public static void main(String[] args) {

        // create a publisher
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // create a subscriber and register it with the publisher
        MySubscriber<String> subscriber = new MySubscriber<>();
        publisher.subscribe(subscriber);

        // publish the data item and close the publisher
        System.out.println("Publishing items...");
        String[] items = { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec" };

        Arrays.asList(items).stream().forEach(i -> publisher.submit(i));
        publisher.close();

        try {
            synchronized ("A") {
                "A".wait();

            }
        } catch (InterruptedException ex) {
            System.err.println("Execption occured " + ex);
        }
    }

    static class MySubscriber<T> implements Subscriber<T> {
        private Subscription subscription;

        @Override
        public void onSubscribe(Subscription arg0) {
            this.subscription = arg0;
            subscription.request(1);
        }

        @Override
        public void onNext(T item) {
            System.out.println("Received: " + item);
            subscription.request(1);
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
            synchronized ("A") {
                "A".notifyAll();
            }
        }

        @Override
        public void onComplete() {
            System.out.println("Done");
            synchronized ("A") {
                "A".notifyAll();
            }
        }

    }
}

/*
 * I use Object's wait() and notifyAll() methods to cause the main thread
 * (thatruns the main() method) to wait until onComplete() is finished.
 * Otherwise, you'll probably not observe any subscriber output.
 */