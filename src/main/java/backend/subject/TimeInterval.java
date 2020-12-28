package backend.subject;

import org.joda.time.LocalTime;

public class TimeInterval {
    private LocalTime from;
    private LocalTime to;

    public TimeInterval(LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
    }

    public LocalTime getFrom() {
        return from;
    }

    public void setFrom(LocalTime from) {
        this.from = from;
    }

    public LocalTime getTo() {
        return to;
    }

    public void setTo(LocalTime to) {
        this.to = to;
    }

    public boolean overlapsWith(TimeInterval t){
        return isBetween(t.from, from, to) || isBetween(t.to, from, to) ||
                isBetween(from, t.from, t.to) || isBetween(to, t.from, t.to)
                || to.isEqual(t.to);
    }

    private static boolean isBetween(LocalTime t, LocalTime from, LocalTime to){
        return t.isAfter(from) && t.isBefore(to);
    }
}
