package frontend;

import backend.scheduler.Scheduler;
import backend.scheduler.WeekSchedule;
import backend.subject.Period;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
                table.setDefaultRenderer(table.getColumnClass(i), new TextAreaRenderer());
            }

            //add the table to the frame
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(1000, 500));
            this.add(scrollPane);

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
                    for (int i = period.getTimeInterval().getFrom().getHourOfDay() - START_TIME;
                         i < period.getTimeInterval().getTo().getHourOfDay() - START_TIME; i++) {

                        tableData[i][day.getValue()] = period.getSubjectName();
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

    static class TextAreaRenderer implements TableCellRenderer {
        private final JTextArea renderer;
        private final Color evenColor = new Color(252, 248, 202);
        Map<Integer, Integer> biggestCellPerRow = new HashMap<>(); // row -> column

        public TextAreaRenderer() {
            renderer = new JTextArea();
            renderer.setLineWrap(true);
            renderer.setWrapStyleWord(true);
            renderer.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            for (int i = 0; i < END_TIME - START_TIME; i++) {
                biggestCellPerRow.put(i, 0);
            }
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            if (isSelected) {
                renderer.setForeground(table.getSelectionForeground());
                renderer.setBackground(table.getSelectionBackground());
            } else {
                renderer.setForeground(table.getForeground());
                renderer.setBackground((row % 2 == 0) ? evenColor : table.getBackground());
            }
            renderer.setFont(table.getFont());
            renderer.setText((value == null) ? "" : value.toString());
            JPanel contentPane = new JPanel(new BorderLayout());
            contentPane.setSize(table.getColumnModel().getColumn(column).getWidth(), renderer.getPreferredSize().height);
            if((table.getRowHeight(row) < renderer.getPreferredSize().height) || (biggestCellPerRow.get(row) == column)) {
                table.setRowHeight(row, renderer.getPreferredSize().height);
                biggestCellPerRow.put(row, column);
            }
            contentPane.add(renderer);
            return contentPane;
        }
    }
}
