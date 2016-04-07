package com.zorroa.fileseq;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

/**
 * Created by chambers on 3/29/16.
 */
public class FileseqTests {
    private static final Logger logger = LoggerFactory.getLogger(FileseqTests.class);

    @Test
    public void testFramesToFrameRangeSimpleCompound() {
        TreeSet<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(5);
        assertEquals("0001-0003,0005",Fileseq.framesToFrameRange(set, 4));
    }

    @Test
    public void testFramesToFrameRangeSingle() {
        TreeSet<Integer> set = new TreeSet<>();
        set.add(1);
        assertEquals("0001",Fileseq.framesToFrameRange(set, 4));
    }

    @Test
    public void testFramesToFrameRangeTwoFrames() {

        TreeSet<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(5);
        assertEquals("0001,0005",Fileseq.framesToFrameRange(set, 4));
    }

    @Test
    public void testFramesToFrameRangeSequential() {

        TreeSet<Integer> set = new TreeSet<>();
        for (int i=1; i<5; i++) {
            set.add(i);
        }
        for (int i=1; i<6; i++) {
            set.add(i);
        }
        assertEquals("1-5", Fileseq.framesToFrameRange(set, 1));
    }

    @Test
    public void testFramesToFrameRangeStep() {

        TreeSet<Integer> set = new TreeSet<>();
        for (int i=1; i<10; i=i+2) {
            System.out.println(i);
            set.add(i);
        }
        for (int i=10; i<100; i=i+5) {
            System.out.println(i);
            set.add(i);
        }
        assertEquals("0001-0009x2,0010-0095x5", Fileseq.framesToFrameRange(set, 4));
    }

}
