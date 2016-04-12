package com.zorroa.fileseq;

import com.google.common.base.Splitter;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by chambers on 3/29/16.
 */
public class FrameSet implements Iterable<Integer> {

    private LinkedHashSet<Range> ranges = new LinkedHashSet<>();

    public FrameSet(TreeSet<Integer> frames) {
        this(Fileseq.framesToFrameRange(frames));
    }

    public FrameSet(String range) {
        if (range != null) {
            for (String part : Splitter.on(",").split(range)) {
                ranges.add(new Range(part));
            }
        }
    }

    /**
     * Return true if given frame is in this frame set;
     *
     * @param frame
     * @return
     */
    public boolean contains(int frame) {
        return ranges.stream().anyMatch(r->r.contains(frame));
    }

    /**
     * Returns the number unique frames in the frameset.   This function
     * executes in linear time.
     *
     * @return
     */
    public int count() {
        int count = 0;
        for (int i: this) {
            ++count;
        }
        return count;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new FrameSetIterator(ranges);
    }

    @Override
    public String toString() {
        return ranges.stream().map(r->r.toString()).collect(Collectors.joining(","));
    }

}
