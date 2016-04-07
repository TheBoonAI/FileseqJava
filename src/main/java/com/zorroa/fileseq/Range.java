package com.zorroa.fileseq;

import java.util.Objects;

/**
 * Created by chambers on 4/7/16.
 */
public class Range {

    enum Stride {
        STANDARD('x'),
        STAGGERED(':'),
        FILL('y');

        private final char symbol;

        Stride(char symbol) {
            this.symbol = symbol;
        }
    }

    private final int start;
    private final int end;
    private final int step;
    private final Stride stride;

    public Range(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
        this.stride = Stride.STANDARD;
    }

    public Range(int start, int end) {
        this(start, end, 1);
    }

    public boolean contains(int frame) {
        switch(stride) {

        }

        return false;
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

}
