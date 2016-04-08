package com.zorroa.fileseq;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by chambers on 4/7/16.
 */
public class FrameSetTests {

    @Test
    public void testContains() {
        FrameSet set = new FrameSet("1-10,15-100x5");
        System.out.println(set.toString());

        int[] yes = new int[] { 1, 2, 10, 15, 20, 25, 30};
        int[] no = new int[] { 11, 12, 13, 14, 16, 21, 26};

        for (int i: yes) {
            assertTrue(set.contains(i));
        }

        for (int i: no) {
            assertFalse(set.contains(i));
        }
    }

    @Test
    public void testCount() {
        FrameSet set = new FrameSet("1-10");
        assertEquals(10, set.count());

        set = new FrameSet("1-10,15-30");
        assertEquals(26, set.count());

        set = new FrameSet("1-10x2,1-10x3,1-10:2");
        assertEquals(10, set.count());

        set = new FrameSet("1-1000x10");
        assertEquals(100, set.count());

        set = new FrameSet("1-100:5,100-105");
        assertEquals(105, set.count());
    }

    @Test
    public void testIterate() {
        int[] known = new int[]{
                1, 2, 3, 4, 5,
                6, 7, 8, 9, 10,
                100, 11, 12, 13,
                14, 15
        };

        int idx = -1;
        FrameSet frameSet = new FrameSet("1-10,100,5-15");
        for (int i: frameSet) {
            assertEquals(known[++idx], i);
        }
    }
}
