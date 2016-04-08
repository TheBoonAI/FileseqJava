package com.zorroa.fileseq;

import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * Created by chambers on 4/7/16.
 */
public class Range implements Iterable<Integer> {

    enum Stride {
        STANDARD('x'),
        STAGGERED(':'),
        FILL('y');

        private final char symbol;

        Stride(char symbol) {
            this.symbol = symbol;
        }

        public char getSymbol() {
            return symbol;
        }

        public static Stride get(char c) {
            for(Stride s: Stride.values()) {
                if (s.getSymbol() == c) {
                    return s;
                }
            }
            throw new IllegalArgumentException("Invalid stride character `" + c + "`");
        }
    }

    private final int start;
    private final int end;
    private final int step;
    private Stride stride;

    public Range(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
        this.stride = Stride.STANDARD;
    }

    public Range(int start, int end, int step, Stride stride) {
        this.start = start;
        this.end = end;
        this.step = step;
        this.stride = stride;
    }

    public Range(int start, int end) {
        this(start, end, 1);
    }

    public Range(String range) {
        if (range.contains(",")) {
            throw new IllegalArgumentException("Frame ranges cannot have commas, try FrameSet.");
        }
        Matcher m = Fileseq.PATTERN_RANGE.matcher(range);
        if (m.matches()) {
            this.start = Integer.valueOf(m.group(1));
            this.end = m.group(2) == null ? this.start : Integer.valueOf(m.group(2));
            this.stride = m.group(3) == null ? Stride.STANDARD : Stride.get(m.group(3).charAt(0));
            this.step = m.group(4) == null ? 1 : Integer.valueOf(m.group(4));
        }
        else {
            throw new IllegalArgumentException("Failed to parse frame range: '" + range + "'");
        }
    }

    public boolean contains(int frame) {
        switch(stride) {
            case STANDARD:
                return (frame >=start && frame <=end && (frame % step) - (start % step) == 0);
            case STAGGERED:
                return (frame >= start && frame <= end);
            case FILL:
                return (frame >start && frame <=end && (frame % step) - (start % step) != 0);
        }

        return false;
    }

    public int next(Integer frame) {
        if (frame == null) {
            return start;
        }

        switch(stride) {
            case STANDARD:
                return frame + step;
            case FILL:
                int next = frame + 1;
                return next % step == 0 ? next + 1 : next;
            default:
                throw new IllegalArgumentException("Invalid stride: " + stride);
        }
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getStep() {
        return step;
    }

    public Stride getStride() {
        return stride;
    }

    public int size() {
        switch(stride) {
            case STANDARD:
                return (end - start + 1) / step;
            case STAGGERED:
                return (end - start + 1);
            case FILL:
                return (end - start + 1) - ((end - start + 1) / step);
            default:
                throw new IllegalArgumentException("Invalid stride: " + stride);
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new FrameSetIterator(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return start == range.start &&
                end == range.end &&
                step == range.step &&
                stride == range.stride;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, step, stride);
    }

    @Override
    public String toString() {
        if (stride.getSymbol() == 'x' && step == 1) {
            return String.format("%d-%d", start, end);
        }
        else {
            return String.format("%d-%d%s%d", start, end, stride.getSymbol(), step);
        }
    }
}
