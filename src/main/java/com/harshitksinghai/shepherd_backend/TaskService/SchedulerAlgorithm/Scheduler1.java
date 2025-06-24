package com.harshitksinghai.shepherd_backend.TaskService.SchedulerAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler1 {

    //// Priority

    public enum Priority {
        P1(0.50), P2(0.25), P3(0.10), P4(0.05);

        private final double weight;

        Priority(double weight) {
            this.weight = weight;
        }

        public double weight() {
            return weight;
        }
    }

    //// Task

    public record Task(String taskId, String title, Double approxHours, Double dueDays, Priority externalPriority,
            Boolean isDivisible, String parentTask, Integer order) {
    }

    public static final List<Task> taskList = List.of(
            new Task("876e5rdthvfn", "A", null, 13.0, Priority.P4, false, null, null),
            new Task("98uy7bt6fyhjm", "A1", daysToHours(1.0), 13.0, Priority.P4, false, "876e5rdthvfn", 1),
            new Task("43un8bj7hhjm", "A2", daysToHours(3.0), 13.0, Priority.P4, false, "876e5rdthvfn", 2),
            new Task("9u76y5fyhjm", "A3", daysToHours(2.0), 13.0, Priority.P4, false, "876e5rdthvfn", 3),

            new Task("trv765trbtrb", "B", null, 20.0, Priority.P4, false, null, null),
            new Task("98bu76vy5hjm", "B1", daysToHours(1.0), 20.0, Priority.P4, false, "trv765trbtrb", 1),
            new Task("98b7v6h5ghj4", "B2", daysToHours(3.0), 20.0, Priority.P4, false, "trv765trbtrb", 2),
            new Task("v56iv7h6yu67", "B3", daysToHours(2.0), 20.0, Priority.P4, false, "trv765trbtrb", 3),
            new Task("v53wbnuij7b6", "B4", daysToHours(2.0), 20.0, Priority.P4, false, "trv765trbtrb", 4),
            new Task("n8bj76hvg5hh", "B5", daysToHours(2.0), 20.0, Priority.P4, false, "trv765trbtrb", 5),

            new Task("y7t6yghyjuyg", "C", 7.5, 2.0, Priority.P4, true, null, null),

            new Task("234hjnbvnbvc", "D", null, 26.0, Priority.P4, false, null, null),
            new Task("b6vyhtrcvj7vy", "D1", daysToHours(1.0), 26.0, Priority.P4, false, "234hjnbvnbvc", 1),
            new Task("bv76yctgvgv", "D2", daysToHours(3.0), 26.0, Priority.P4, false, "234hjnbvnbvc", 2),
            new Task("c54yhj87gkjg", "D3", daysToHours(2.0), 26.0, Priority.P4, false, "234hjnbvnbvc", 3));

    //// TimeStrip

    // TimeSlot Structure
    public record TimeSlot(double startHour, double endHour, boolean isFree) {
        public double duration() {
            return endHour - startHour;
        }

        @Override
        public String toString() {
            return format(startHour) + " - " + format(endHour) + (isFree ? " [Free]" : " [Busy]");
        }

        private String format(double hour) {
            int h = (int) hour;
            int m = (int) Math.round((hour - h) * 60);
            return String.format("%02d:%02d", h, m);
        }
    }

    // Template A
    public static final List<TimeSlot> TEMPLATE_A = List.of(
            new TimeSlot(7.5, 9.0, true),
            new TimeSlot(9.0, 11.0, false),
            new TimeSlot(11.0, 13.0, true),
            new TimeSlot(13.0, 14.5, false),
            new TimeSlot(14.5, 18.5, true),
            new TimeSlot(18.5, 20.5, false),
            new TimeSlot(20.5, 21.5, true));

    // Template B
    public static final List<TimeSlot> TEMPLATE_B = List.of(
            new TimeSlot(7.5, 10.0, true),
            new TimeSlot(10.0, 12.0, false),
            new TimeSlot(12.0, 14.0, true),
            new TimeSlot(14.0, 16.0, false),
            new TimeSlot(16.0, 19.0, true),
            new TimeSlot(19.0, 21.5, false));

    // Template C
    public static final List<TimeSlot> TEMPLATE_C = List.of(
            new TimeSlot(7.5, 8.5, true),
            new TimeSlot(8.5, 11.5, false),
            new TimeSlot(11.5, 12.5, true),
            new TimeSlot(12.5, 13.5, false),
            new TimeSlot(13.5, 18.0, true),
            new TimeSlot(18.0, 21.5, false));

    // Full strip: Map<DayNumber, List<TimeSlot>>
    public static final Map<Integer, List<TimeSlot>> TIME_STRIP = new HashMap<>();

    public static int calculateMaxDueDays() {
        return taskList.stream()
                .mapToInt(t -> (int) Math.ceil(t.dueDays()))
                .max()
                .orElse(0);
    }

    public static void generateTimeStrip(int totalDays) {
        for (int day = 1; day <= totalDays; day++) {
            List<TimeSlot> selectedTemplate;
            int mod = (day - 1) % 3;

            if (mod == 0)
                selectedTemplate = TEMPLATE_A;
            else if (mod == 1)
                selectedTemplate = TEMPLATE_B;
            else
                selectedTemplate = TEMPLATE_C;

            // Deep copy to avoid modifying shared templates
            List<TimeSlot> daySlots = new ArrayList<>();
            for (TimeSlot slot : selectedTemplate) {
                daySlots.add(new TimeSlot(slot.startHour(), slot.endHour(), slot.isFree()));
            }

            TIME_STRIP.put(day, daySlots);
        }
    }

    //// ScheduledTask

    public record ScheduledTask(
            Task task,
            double internalPriority,
            double initialTaskPriority) {
    }

    ////

    public static void taskDataProvider() {
        // timeStrip
        int maxDueDays = calculateMaxDueDays();
        generateTimeStrip(maxDueDays);

        System.out.println("=== Time Strip for " + maxDueDays + " days ===");
        for (int day = 1; day <= maxDueDays; day++) {
            System.out.println("Day " + day + ":");
            for (TimeSlot slot : TIME_STRIP.get(day)) {
                System.out.println("  " + slot);
            }
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    // public static void taskDataProvider() {
    // // return data that will be used in taskScheduler method
    // }

    public static void main(String[] args) {

        System.out.println("Begin:");
        taskScheduler();
        System.out.println("End");

    }

    public static void taskScheduler() {
        // main scheduling method

        // generate time strip
        taskDataProvider();

        // taskList after splitting divisible tasks
        List<Task> divisibleTaskList = generateDivisibleTaskList(taskList);

        System.out.println("=== Task List After Splitting Divisible Tasks ===");
        printTasks(divisibleTaskList);

        // calculate initial task priority
        List<ScheduledTask> prioritizedTasks = calculatePriorities(divisibleTaskList);

        System.out.println("\n=== Task Priorities ===");
        printTaskPriorities(prioritizedTasks);

        //

    }

    // Separation of Concerns

    public static List<Task> generateDivisibleTaskList(List<Task> taskList) {
        List<Task> divisibleTaskList = new ArrayList<>();
        for (Task t : taskList) {
            if (Boolean.TRUE.equals(t.isDivisible())) {
                divisibleTaskList.addAll(splitDivisibleTask(t, 2.0));
            } else {
                divisibleTaskList.add(t);
            }
        }
        return divisibleTaskList;
    }

    public static List<ScheduledTask> calculatePriorities(List<Task> tasks) {
        List<ScheduledTask> scheduledTasks = new ArrayList<>();

        for (Task t : tasks) {
            if (t.approxHours() == null || t.dueDays() == null)
                continue;

            double dueHours = daysToHours(t.dueDays());
            double internalPriority = t.approxHours() / dueHours;
            double initialTaskPriority = 0.4 * t.externalPriority().weight() + 0.6 * internalPriority;

            scheduledTasks.add(new ScheduledTask(t, internalPriority, initialTaskPriority));
        }

        return scheduledTasks;
    }

    // more helper methods as needed

    public static void printTasks(List<Task> tasks) {
        System.out.printf("%-20s %-8s %-12s %-10s %-5s %-10s %-20s %-5s%n",
                "Task ID", "Title", "Approx (hrs)", "Due (days)", "Ext. Prio", "Divisible", "Parent Task", "Order");
        System.out
                .println("-------------------------------------------------------------------------------------------");

        for (Task t : tasks) {
            System.out.printf("%-20s %-8s %-12s %-10s %-5s %-10s %-20s %-5s%n",
                    t.taskId(),
                    t.title(),
                    t.approxHours() != null ? t.approxHours() : "-",
                    t.dueDays() != null ? t.dueDays() : "-",
                    t.externalPriority(),
                    Boolean.TRUE.equals(t.isDivisible()) ? "Yes" : "No",
                    t.parentTask() != null ? t.parentTask() : "-",
                    t.order() != null ? t.order() : "-");
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public static void printTaskPriorities(List<ScheduledTask> scheduledTasks) {
        System.out.printf("%-8s %-12s %-10s %-10s%n", "Title", "Internal", "External", "Final");
        System.out.println("---------------------------------------------");
    
        for (ScheduledTask st : scheduledTasks) {
            System.out.printf("%-8s %-12.4f %-10.2f %-10.4f%n",
                st.task().title(),
                st.internalPriority(),
                st.task().externalPriority().weight(),
                st.initialTaskPriority()
            );
        }
    }

    public static Double daysToHours(Double days) {
        return days * 8.0;
    }

    public static Double hoursToDays(Double hours) {
        if (hours == null)
            return null;
        return hours / 8.0;
    }

    public static List<Task> splitDivisibleTask(Task task, double maxPartSizeHours) {
        if (task.approxHours() == null || !Boolean.TRUE.equals(task.isDivisible()))
            return List.of();

        List<Task> parts = new ArrayList<>();
        double remaining = task.approxHours();
        int partNumber = 1;

        while (remaining > 0) {
            double partSize = Math.min(remaining, maxPartSizeHours);
            String newTaskId = task.taskId() + "-part" + partNumber;
            String newTitle = task.title() + partNumber;

            parts.add(new Task(
                    newTaskId,
                    newTitle,
                    partSize,
                    task.dueDays(),
                    task.externalPriority(),
                    false, // parts are not divisible
                    task.taskId(), // parent is original task
                    partNumber));

            remaining -= partSize;
            partNumber++;
        }

        return parts;
    }
}
