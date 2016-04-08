# FileseqJava

A Java port of the https://github.com/sqlboy/fileseq library.

## Frame Range Shorthand

Support for:

* Standard: 1-10
* Comma Delimited: 1-10,10-20
* Chunked: 1-100x5
* Filled: 1-100y5
* Staggered: 1-100:3 (1-100x3, 1-100x2, 1-100)
* Negative frame numbers: -10-100
* Padding: #=4 padded, @=single pad

## FrameSets

A FrameSet wraps a sequence of frames in a list list container.
