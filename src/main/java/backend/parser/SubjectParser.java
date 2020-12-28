package backend.parser;

import org.joda.time.LocalTime;
import backend.subject.Subject;
import backend.subject.Period;
import backend.subject.SubjectPlan;
import backend.subject.Teacher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SubjectParser {
    private static final int CODE_LENGTH = 5;

    public static List<Subject> parse(String filePath) {
        BufferedReader reader;
        List<Subject> subjects = new LinkedList<>();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String nameLine;
            while ((nameLine = reader.readLine()) != null && !nameLine.isEmpty()) {
                nameLine = deleteStartingSpaces(nameLine);
                String subjectCode = nameLine.substring(0, CODE_LENGTH);
                String subjectName = nameLine.substring(CODE_LENGTH + 3);

                reader.readLine(); // table column titles

                List<SubjectPlan> plans = new LinkedList<>();
                String planName;
                while ((planName = reader.readLine()) != null && !planName.isEmpty()) {
                    reader.readLine(); // empty line

                    List<Period> periods = new LinkedList<>();
                    String periodLine;
                    while ((periodLine = reader.readLine()) != null && !periodLine.isEmpty()) {
                        periods.add(parsePeriod(subjectName, periodLine));
                    }

                    List<Teacher> teachers = new LinkedList<>();
                    String teacherLine;
                    while ((teacherLine = reader.readLine()) != null && !teacherLine.isEmpty()
                            && Character.isLetter(teacherLine.charAt(0))) {
                        teachers.add(parseTeacher(teacherLine));
                    }
                    if(teacherLine == null){
                        throw new IllegalArgumentException("The file is not formatted correctly");
                    }
                    String studentQtyLine = deleteStartingSpaces(teacherLine);
                    int currentQty = Integer.parseInt(studentQtyLine.substring(0, studentQtyLine.indexOf('/') - 1));
                    studentQtyLine = studentQtyLine.substring(studentQtyLine.indexOf('/') + 2);
                    Integer maxQty = null;
                    if(!studentQtyLine.equals("Ilimitado")){
                        maxQty = Integer.parseInt(studentQtyLine);
                    }
                    plans.add(new SubjectPlan(planName, periods, teachers, currentQty, maxQty));
                }
                subjects.add(new Subject(subjectCode, subjectName, plans));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    private static Teacher parseTeacher(String teacherLine) {
        String surname = teacherLine.substring(0, teacherLine.indexOf(','));
        String name = teacherLine.substring(teacherLine.indexOf(',') + 2);
        return new Teacher(name, surname);
    }

    private static Period parsePeriod(String subject, String periodLine) {
        List<String> tokens = Arrays.asList(periodLine.split(" ", 5));
        DayOfWeek dayOfWeek = getDayOfWeek(tokens.get(0));
        int startHour = Integer.parseInt(tokens.get(1).substring(0, 2));
        int startMinute = Integer.parseInt(tokens.get(1).substring(3, 5));
        LocalTime startTime = new LocalTime(startHour, startMinute);
        int endHour = Integer.parseInt(tokens.get(3).substring(0, 2));
        int endMinute = Integer.parseInt(tokens.get(3).substring(3, 5));
        LocalTime endTime = new LocalTime(endHour, endMinute);
        String classroom = tokens.get(4);
        return new Period(subject, dayOfWeek, startTime, endTime, classroom);
    }

    private static DayOfWeek getDayOfWeek(String dow) {
        switch (dow){
            case "Lunes":
                return DayOfWeek.MONDAY;
            case "Martes":
                return DayOfWeek.TUESDAY;
            case "Miércoles":
                return DayOfWeek.WEDNESDAY;
            case "Jueves":
                return DayOfWeek.THURSDAY;
            case "Viernes":
                return DayOfWeek.FRIDAY;
            case "Sábado":
                return DayOfWeek.SATURDAY;
            case "Domingo":
                return DayOfWeek.SUNDAY;
        }
        return null;
    }

    private static String deleteStartingSpaces(String s){
        int i;
        for (i = 0; Character.isWhitespace(s.charAt(i)); i++);
        return s.substring(i);
    }
}
