package frontend;

import backend.scheduler.Scheduler;
import backend.scheduler.WeekSchedule;
import backend.subject.Period;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;

public class Table extends JFrame {
    private static final int START_TIME = 8;
    private static final int END_TIME = 22;

    public Table(List<WeekSchedule> schedules)
    {
        //headers for the table
        String[] columns = new String[] {
                "Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"
        };

        //actual data for the table in a 2d array
        List<Object[][]> data = toDataArray(schedules);

        //create table with data
        int tablenumber = 1;
        for (Object[][] tableData : data) {
            JTable table = new JTable(tableData, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.getTableHeader().setReorderingAllowed(false);
            JTableHeader header = table.getTableHeader();
            header.setBackground(Color.blue);
            header.setForeground(Color.white);
            for (int i =0; i<columns.length; i++) {
                table.setDefaultRenderer(table.getColumnClass(i), new ScheduleCellRenderer());
            }

            //add the table to the frame
            this.add(new JScrollPane(table));

            this.setTitle("Horarios " + tablenumber);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();
            this.setVisible(true);
        }
    }

    private static List<Object[][]> toDataArray(List<WeekSchedule> schedules) {
        List<Object[][]> answer = new LinkedList<>();
        for (WeekSchedule schedule : schedules) {
            Object[][] tableData = new Object[END_TIME - START_TIME][];
            for (int i = 0; i < END_TIME - START_TIME; i++) {
                tableData[i] = new Object[DayOfWeek.values().length];
            }

            for (int i = START_TIME; i < END_TIME; i++) {
                tableData[i - START_TIME][0] = i + " - " + (i + 1);
            }


            for (DayOfWeek day : DayOfWeek.values()) {
                for (Period period : schedule.getWeekSchedule().get(day).getPeriods()) {
                    for (int i = period.getTimeInterval().getFrom().getHourOfDay() - START_TIME; i < period.getTimeInterval().getTo().getHourOfDay() - START_TIME; i++) {
                        String name = period.getSubjectName().replace(' ', '\n');
                        System.out.println(name);
                        tableData[i][day.getValue()] = name;
                    }
                }
            }
            answer.add(tableData);
        }
        return answer;
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new Table(Scheduler.schedule()));
    }

    static class ScheduleCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column)
        {
            if ((row % 2) == 0) {
                setBackground(Color.white);
            }
            else {
                setBackground(Color.lightGray);
            }

            return super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
        }
    }
}
