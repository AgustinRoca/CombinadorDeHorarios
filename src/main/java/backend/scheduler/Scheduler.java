package backend.scheduler;

import backend.parser.SubjectParser;
import backend.subject.Subject;
import backend.subject.SubjectPlan;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    public static List<WeekSchedule> schedule() {
        List<Subject> subjects = SubjectParser.parse("src/main/resources/subjects.txt");
        List<WeekSchedule> weekSchedules = new ArrayList<>();
        weekSchedules.add(new WeekSchedule());

        for (Subject subject : subjects) {
            List<WeekSchedule> possibleSchedules = new ArrayList<>();
            for(WeekSchedule weekSchedule : weekSchedules){
                possibleSchedules.add(new WeekSchedule(weekSchedule.getWeekSchedule(), weekSchedule.getSubjectPlans()));
            }
            weekSchedules = new ArrayList<>();
            for (SubjectPlan plan : subject.getPlans()){
                for (WeekSchedule weekSchedule : possibleSchedules) {
                    WeekSchedule weekScheduleCopy = new WeekSchedule(weekSchedule.getWeekSchedule(), weekSchedule.getSubjectPlans());
                    if(weekScheduleCopy.addIfCan(subject, plan)){
                        weekSchedules.add(weekScheduleCopy);
                    }
                }
            }
        }
        return weekSchedules;
    }
}
