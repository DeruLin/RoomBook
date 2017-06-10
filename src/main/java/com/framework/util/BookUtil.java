package com.framework.util;


import com.roombook.vo.Duration;
import com.roombook.vo.DurationUI;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by deru on 2017/5/12.
 */
public class BookUtil {

    public static List<Duration> calculateRemainTime(List<Duration> used) {
        List<Duration> remain = new ArrayList<>();
        Time start = Time.valueOf("08:00:00");
        Time end = Time.valueOf("22:00:00");
        used.sort(Comparator.comparing(d -> d.startTime));
        for (Duration duration : used) {
            remain.add(new Duration(start, duration.startTime));
            start = duration.endTime;
        }
        remain.add(new Duration(start, end));
        return remain.stream().filter(x -> !x.startTime.equals(x.endTime)).collect(Collectors.toList());

    }

    public static List<DurationUI> getRemainTimeUI(List<Duration> used) {
        List<Duration> remain = calculateRemainTime(used);
        List<DurationUI> result = remain.stream()
                .map(y -> new DurationUI(y.startTime.toString().substring(0, 5), y.endTime.toString().substring(0, 5)))
                .collect(Collectors.toList());
        return result;
    }

    public static boolean isValidDuration(Duration duration, List<Duration> used) {
        List<Duration> remain = calculateRemainTime(used);
        for (Duration item : remain) {
            if ((duration.startTime.compareTo(item.startTime) >= 0 && duration.endTime.compareTo(item.endTime) <= 0))
                return true;

        }
        return false;

    }

    public static List<Duration> splitDuration(Duration duration) {
        List<Duration> result = new ArrayList<>();
        Time start = duration.startTime;
        Time end = new Time(start.getTime() + 60 * 60 * 4 * 1000);
        if (duration.endTime.getTime() - duration.startTime.getTime() <= 60 * 60 * 4 * 1000) {
            result.add(duration);
            return result;
        }
        while (end.compareTo(duration.endTime) < 0) {
            result.add(new Duration(start, end));
            start = end;
            end = new Time(end.getTime() + 60 * 60 * 4 * 1000);
        }
        result.add(new Duration(start, duration.endTime));
        return result;
    }
}
