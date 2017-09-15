public class Notifier extends Stream {
    DoubleObject value;
    TCFrame f;

    public Notifier(TCFrame f) {
        this.f = f;
    }

    public void putValue(DoubleObject v) {
        value = v;
    }

    public void run() {
        f.area.append(value.number + "\n");
    }
}