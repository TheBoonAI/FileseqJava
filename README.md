# FileseqJava

A Java port of the https://github.com/sqlboy/fileseq library.

## Frame Range Shorthand

Support for:

* Standard: 1-10
* Comma Delimited: 1-10,10-20
* Chunked: 1-100x5
* Filled: 1-100y5
* Staggered: 1-100:5 (1-100x5, 2-100x5, 3-100x5, 2-100x5, 5-100x5)
* Negative frame numbers: -10-100
* Padding: #=4 padded, @=single pad

## FrameSets

A FrameSet wraps a sequence of frames in a list list container.
