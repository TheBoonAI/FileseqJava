package com.zorroa.fileseq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chambers on 4/7/16.
 */
public class FrameSetIterator implements Iterator<Integer> {

    private List<Range> ranges = new ArrayList<>();

    private int ridx;
    private Integer current;
    private int step;

    public FrameSetIterator(Range range) {
        deAggregate(range);
        this.ridx = 0;
        init();
    }

    public FrameSetIterator (Iterable<Range> ranges) {
        for (Range range: ranges) {
            deAggregate(range);
        }
        this.ridx = 0;
        init();
    }

    private void deAggregate(Range range) {
        if (range.getStride().equals(Range.Stride.STAGGERED)) {
            for (int i = range.getStep(); i>0; i--) {
                ranges.add(new Range(range.getStart() + (range.getStep() - i), range.getEnd(), range.getStep()));
            }
        }
        else {
            ranges.add(range);
        }
    }

    private void init() {
        this.step = ranges.get(ridx).getStep();
        this.current = null;
    }

    private boolean seen(int frame) {
        /**
         * Check all previous ranges for the frame.  By definition its not possible to see
         * duplicate frames in the same range,so we don't have to check that.
         */
        for (int i=0; i<ridx; i++) {
            if (ranges.get(i).contains(frame)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasNext() {
        boolean hasNext = false;

        while(true) {
            int next = ranges.get(ridx).next(current);
            if (seen(next)) {
                current = next;
                continue;
            }

            hasNext = next <= ranges.get(ridx).getEnd();
            if (!hasNext) {
                if (ridx < ranges.size() - 1) {
                    ridx++;
                    init();
                    continue;
                }
            }
            current = next;
            break;

        }
        return hasNext;
    }

    @Override
    public Integer next() {
        return current;
    }
}
