package com.zorroa.fileseq;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by chambers on 4/7/16.
 */
public class FileSequenceTests {

    private static final Logger logger = LoggerFactory.getLogger(FileseqTests.class);

    @Test
    public void testStandard() {
        String spec = "/foo/bar.1-100#.exr";
        FileSequence seq = new FileSequence(spec);
        assertEquals("/foo/", seq.getDir());
        assertEquals("bar.", seq.getBase());
        assertEquals("1-100", seq.getRange());
        assertEquals("#", seq.getPadding());
        assertEquals(4, seq.getZfill());
        assertEquals(spec, seq.getFileSpec());
    }

    @Test
    public void testXSheetColumnReference() {
        String spec = "/foo/bar_1000_1.exr";
        FileSequence seq = new FileSequence(spec);
        assertEquals("/foo/", seq.getDir());
        assertEquals("bar_", seq.getBase());
        assertEquals("1000", seq.getRange());
        assertEquals("#", seq.getPadding());
        assertEquals(4, seq.getZfill());
        assertEquals("/foo/bar_1000#.exr", seq.getFileSpec());
        assertEquals(1, seq.getAttrs(new Range(1000)).get("xsheetColumn"));
    }

    @Test
    public void testOnlyPadding() {
        String spec = "/foo/bar.#.exr";
        FileSequence seq = new FileSequence(spec);
        assertEquals("/foo/", seq.getDir());
        assertEquals("bar.", seq.getBase());
        assertEquals("", seq.getRange());
        assertEquals("#", seq.getPadding());
        assertEquals(4, seq.getZfill());
        assertEquals(spec, seq.getFileSpec());
    }

    @Test
    public void testSingleFrame() {
        String spec = "/foo/bar.0100.exr";
        FileSequence seq = new FileSequence(spec);
        assertEquals("/foo/", seq.getDir());
        assertEquals("bar.", seq.getBase());
        assertEquals("0100", seq.getRange());
        assertEquals("#", seq.getPadding());
        assertEquals(4, seq.getZfill());
        assertEquals("/foo/bar.0100#.exr", seq.getFileSpec());
    }

    @Test
    public void testNotAFileSequence() {
        String[] specs = new String[] {
                "/foo/bar.exr",
                "/foo/bar212121.exr"
        };
        for (String spec: specs) {
            FileSequence seq = new FileSequence(spec);
            assertFalse(seq.isValid());
        }
    }

    @Test
    public void testSetRange() {
        FileSequence seq = new FileSequence("/foo/bar.1-100#.exr");
        seq.setRange("");
        assertEquals("/foo/", seq.getDir());
        assertEquals("bar.", seq.getBase());
        assertEquals("", seq.getRange());
        assertEquals("#", seq.getPadding());
        assertEquals(4, seq.getZfill());
        assertEquals("/foo/bar.#.exr", seq.getFileSpec());
    }
}
