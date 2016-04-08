package com.zorroa.fileseq;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by chambers on 4/7/16.
 */
public class RangeTests {

    @Test
    public void testContainsStandard() {
        Range range = new Range(33, 100, 5);

        int[] no = new int[] {34, 35, 36, 37 };
        int[] yes = new int[] {33, 38, 43, 48 };

        for (int i: no) {
            assertFalse(range.contains(i));
        }

        for (int i: yes) {
            assertTrue(range.contains(i));
        }
    }

    @Test
    public void testContainsFill() {
        Range range = new Range(33, 100, 5, Range.Stride.FILL);
        System.out.println(range.toString());

        int[] yes = new int[] {34, 35, 36, 37 };
        int[] no = new int[] {33, 38, 43, 48 };

        for (int i: no) {
            assertFalse("Was true: " + i, range.contains(i));
        }

        for (int i: yes) {
            assertTrue("Was false: " + i, range.contains(i));
        }
    }

    @Test
    public void testInitByString() {
        Range range = new Range("1-10");
        assertEquals(1, range.getStart());
        assertEquals(10, range.getEnd());
        assertEquals(1, range.getStart());
        assertEquals(Range.Stride.STANDARD, range.getStride());
    }

    @Test
    public void testSize() {
        Range range = new Range("1-10");
        assertEquals(10, range.size());

        range = new Range("1-10x5");
        assertEquals(2, range.size());

        range = new Range("1-10y5");
        assertEquals(8, range.size());

        range = new Range("1-10:2");
        assertEquals(10, range.size());
    }
}
