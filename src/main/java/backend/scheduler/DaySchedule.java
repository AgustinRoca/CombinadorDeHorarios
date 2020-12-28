package backend.scheduler;

import backend.subject.Period;
import backend.subject.TimeInterval;

import java.util.LinkedList;
import java.util.List;

public class DaySchedule {
    private final List<Period> periods = new LinkedList<>();

    public boolean canAdd(TimeInterval t){
        for (Period period : periods){
            if(t.overlapsWith(period.getTimeInterval())){
                return false;
            }
        }
        return true;
    }

    public boolean addIfCan(Period p){
        TimeInterval t = new TimeInterval(p.getStart(), p.getFinish());
        if(canAdd(t)){
            periods.add(p);
            return true;
        } else {
            return false;
        }
    }

    public List<Period> getPeriods() {
        return periods;
    }
}
