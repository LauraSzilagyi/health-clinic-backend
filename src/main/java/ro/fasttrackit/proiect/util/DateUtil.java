package ro.fasttrackit.proiect.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

public class DateUtil {

    private DateUtil() {
    }

    public static LocalDate getMondayOfWeek() {
        LocalDate monday = LocalDate.now();
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        return monday;
    }

    public static LocalDate getSundayOfWeek() {
        LocalDate sunday = LocalDate.now();
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }
        return sunday;
    }

    public static List<LocalDate> get30DaysDate() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(30);
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(startDate::plusDays)
                .toList();
    }

    public static List<LocalDate> getWeekDays() {

        LocalDate startDate = getMondayOfWeek();
        LocalDate endDate = getSundayOfWeek();
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween - 1)
                .mapToObj(startDate::plusDays)
                .toList();
    }
}