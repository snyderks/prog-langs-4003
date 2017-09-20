import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CollectorStream extends Stream {
    ArrayList<Stream> producers;
    Notifier notifier;
    TCFrame frame;

    CollectorStream(Notifier n, TCFrame t) {
        notifier = n;
        frame = t;
        producers = new ArrayList<Stream>();
    }

    // Adds a producer to the collection.
    public void add(Stream s) {
        producers.add(s);
    }

    synchronized public Object next() {
        DoubleObject counter = new DoubleObject(0);
        for (Stream s : producers) {
            // Sum the producers
            counter = new DoubleObject(((Subscriber) s.next()).stock_value + counter.number);
        }
        notifier.putValue(counter);
        return notifier;
    }
}
