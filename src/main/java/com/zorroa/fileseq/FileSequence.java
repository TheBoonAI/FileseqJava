package com.zorroa.fileseq;

import com.google.common.base.Strings;

import java.util.Objects;
import java.util.regex.Matcher;

import static com.zorroa.fileseq.Fileseq.PATTERN_SPLIT;

/**
 * Created by chambers on 4/6/16.
 */
public class FileSequence {

    private  String dir;
    private  String base;
    private  String padding;
    private  String range;
    private  String ext;
    private int zfill;

    public FileSequence(String filespec) {
        /*
         * Matches two types of filespecs:
         * /foo/bar.1-100#.exr
         * /foo/bar.#.exr
         */
        String[] parts = PATTERN_SPLIT.split(filespec);
        if (parts.length == 2) {
            dir = parts[0].substring(0, parts[0].lastIndexOf("/")+1);
            base = parts[0].substring(parts[0].lastIndexOf("/")+1);
            ext = parts[1];

            Matcher m = PATTERN_SPLIT.matcher(filespec);
            if (m.find()) {
                range = m.group(1);
                padding = m.group(2);
            }
        }
        else {
            /*
             * Matches a single frame.
             */
            Matcher matcher = Fileseq.PATTERN_ON_DISK.matcher(filespec);
            if (matcher.matches() && matcher.group(3) != null) {
                dir = matcher.group(1);
                base = matcher.group(2);
                range = matcher.group(3);
                padding = Fileseq.frameToPaddingChars(matcher.group(3));
                ext = matcher.group(4);
            }
        }

        if (padding != null) {
            zfill = Fileseq.paddingToZfill(padding);
        }
    }

    public String getFrame(int frame) {
        return new StringBuilder()
                .append(dir)
                .append(base)
                .append(Strings.padStart(String.valueOf(frame), zfill, '0'))
                .append(ext)
                .toString();
    }

    public String getFrame(String frame) {
        return new StringBuilder()
                .append(dir)
                .append(base)
                .append(frame)
                .append(ext)
                .toString();
    }

    public String getFileSpec() {
        return getFileSpec(range, padding);
    }

    public String getFileSpec(String range) {
        return getFileSpec(range, padding);
    }

    public String getFileSpec(String range, String padding) {
        return new StringBuilder()
                .append(dir)
                .append(base)
                .append(range)
                .append(padding)
                .append(ext)
                .toString();
    }


    public String getDir() {
        return dir;
    }

    public FileSequence setDir(String dir) {
        this.dir = dir;
        return this;
    }

    public String getBase() {
        return base;
    }

    public FileSequence setBase(String base) {
        this.base = base;
        return this;
    }

    public String getPadding() {
        return padding;
    }

    public FileSequence setPadding(String padding) {
        this.padding = padding;
        return this;
    }

    public String getRange() {
        return range;
    }

    public FileSequence setRange(String range) {
        this.range = range == null ? "" : range;
        return this;
    }

    public String getExt() {
        return ext;
    }

    public FileSequence setExt(String ext) {
        this.ext = ext;
        return this;
    }

    public int getZfill() {
        return zfill;
    }

    public FileSequence setZfill(int zfill) {
        this.zfill = zfill;
        return this;
    }

    public boolean isValid() {
        return !(dir == null || base == null || ext == null || padding == null);
    }

    public FrameSet getFrameSet() {
        return new FrameSet(range);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FileSequence{");
        sb.append("  dir='").append(dir).append('\'');
        sb.append(", base='").append(base).append('\'');
        sb.append(", range='").append(range).append('\'');
        sb.append(", padding='").append(padding).append('\'');
        sb.append(", ext='").append(ext).append('\'');
        sb.append(", zfill='").append(zfill).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileSequence that = (FileSequence) o;
        return getZfill() == that.getZfill() &&
                Objects.equals(getDir(), that.getDir()) &&
                Objects.equals(getBase(), that.getBase()) &&
                Objects.equals(getPadding(), that.getPadding()) &&
                Objects.equals(getRange(), that.getRange()) &&
                Objects.equals(getExt(), that.getExt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDir(), getBase(), getPadding(), getRange(), getExt(), getZfill());
    }
}
