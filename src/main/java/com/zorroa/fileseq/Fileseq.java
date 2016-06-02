package com.zorroa.fileseq;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * Created by chambers on 4/6/16.
 */
public class Fileseq {

    /**
     * Regular expression matching a frame range and padding string.
     * Example: 1-100#
     */
    public static final Pattern PATTERN_SPLIT = Pattern.compile("([-:,xy\\d]*)([{#@}]+)");

    /**
     * Regular expression for matching a file.
     *
     */
    public static final Pattern PATTERN_ON_DISK = Pattern.compile("^(.*/)?(?:$|(.*?\\.)(-?\\d+)?(?:(\\.[^.]*$)|$))");

    /**
     * Regular expression for matching a frame range.
     */
    public static final Pattern PATTERN_RANGE = Pattern.compile("^(-?\\d+)(?:-(-?\\d+)(?:([:xy]{1})(\\d+))?)?$");

    /**
     * A fast lookup mechanism for padding characters.
     */
    private static final Map<Character, Integer> PADDING =  ImmutableMap.of('#', 4, '@', 1);

    /**
     * Returns the amount of padding for a frame number in string form.  For
     * example, 0001 would return "#".
     *
     * @param frame
     * @return
     */
    public static final String frameToPaddingChars(String frame) {
        /*
         * Count just the digits to ignore any negative signs which could throw
         * off the padding.
         */
        int padding = Math.toIntExact(frame.chars().filter(c -> Character.isDigit(c)).count());
        return Stream.generate(()-> "#").limit(padding / 4).collect(joining()) +
                Stream.generate(()-> "@").limit(padding % 4).collect(joining());
    }

    public static final int paddingToZfill(String padding) {
        return padding.chars().map(c-> PADDING.getOrDefault((char)c, 0)).sum();
    }

    public static final int frameToZfill(String frame) {
        return Math.toIntExact(frame.chars().filter(c -> Character.isDigit(c)).count());
    }

    public static String framesToFrameRange(TreeSet<Integer> frames, String padding) {
        return framesToFrameRange(frames, paddingToZfill(padding));
    }

    public static String framesToFrameRange(TreeSet<Integer> frames) {
        return framesToFrameRange(frames, 1);
    }

    public static String framesToFrameRange(TreeSet<Integer> frames, int padding) {
        List<String> result = new ArrayList();
        int start = frames.first();
        int last = start;
        int step, lastStep = 0;
        padding = Math.max(1, padding);

        for (int i : frames) {
            step = i - last;
            if (step != lastStep  && lastStep != 0 && i - start != step) {
                result.add(framesToFrameRange(start, last, lastStep, padding));
                start = i;
            }
            last = i;
            lastStep = step;
        }

        result.add(framesToFrameRange(start, last, lastStep, padding));
        return String.join(",", result);
    }

    public static String framesToFrameRange(int start, int end, int step, int padding) {
        String frame = String.join(String.valueOf(padding),"%0","d");
        if (start == end) {
            return String.format(frame, start);
        }
        else if (end-start == step) {
            return String.format(String.join(",", frame, frame), start, end);
        }
        else if (step > 1) {

            return String.format(String.join("-", frame, frame) + "x%d", start, end, step);
        }
        else {
            return String.format(String.join("-", frame, frame), start, end);
        }
    }
}
