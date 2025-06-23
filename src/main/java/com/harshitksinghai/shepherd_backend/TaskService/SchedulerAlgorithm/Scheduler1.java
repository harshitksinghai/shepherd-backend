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
        Priority(double weight) { this.weight = weight; }
        public double weight()  { return weight; }
    }


    //// Task

    public record Task(String title, double approxHours, double dueDays, Priority priority) {}

    public static final List<Task> taskList = List.of(
        new Task("A1", 20, 10, Priority.P1),   // 2.5 days × 8 hrs/day = 20 hrs
        new Task("B2", 8, 5.5, Priority.P2),
        new Task("B22", 24, 8, Priority.P3),
        new Task("C3", 4, 3, Priority.P4),
        new Task("D1", 32, 12, Priority.P4)
    );

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
            new TimeSlot(20.5, 21.5, true)
    );

    // Template B
    public static final List<TimeSlot> TEMPLATE_B = List.of(
            new TimeSlot(7.5, 10.0, true),
            new TimeSlot(10.0, 12.0, false),
            new TimeSlot(12.0, 14.0, true),
            new TimeSlot(14.0, 16.0, false),
            new TimeSlot(16.0, 19.0, true),
            new TimeSlot(19.0, 21.5, false)
    );

    // Template C
    public static final List<TimeSlot> TEMPLATE_C = List.of(
            new TimeSlot(7.5, 8.5, true),
            new TimeSlot(8.5, 11.5, false),
            new TimeSlot(11.5, 12.5, true),
            new TimeSlot(12.5, 13.5, false),
            new TimeSlot(13.5, 18.0, true),
            new TimeSlot(18.0, 21.5, false)
    );

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
    
            if (mod == 0) selectedTemplate = TEMPLATE_A;
            else if (mod == 1) selectedTemplate = TEMPLATE_B;
            else selectedTemplate = TEMPLATE_C;
    
            // Deep copy to avoid modifying shared templates
            List<TimeSlot> daySlots = new ArrayList<>();
            for (TimeSlot slot : selectedTemplate) {
                daySlots.add(new TimeSlot(slot.startHour(), slot.endHour(), slot.isFree()));
            }
    
            TIME_STRIP.put(day, daySlots);
        }
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
    //     // return data that will be used in taskScheduler method
    // }


    public static void main(String[] args) {

        System.out.println("Begin:");
        taskScheduler();
        System.out.println("End");

    }

    public static void taskScheduler() {
        // main scheduling method
        taskDataProvider();
    }

    // more helper methods as needed
}
