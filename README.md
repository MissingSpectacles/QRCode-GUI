# QRCode GUI

## Background

A JavaFX application for reading and writing QR Code.  
Just for fun

## How to Use

For windows, launch `start-gui.cmd`.  
For non-windows, cd into `./Source Code/build/libs/` then `java -jar PDFBoxGUI-0.0-SNAPSHOT.jar`,  
or `java -jar "./Source Code/build/libs/PDFBoxGUI-0.0-SNAPSHOT.jar"`.  
Mind the quote (`"`)

## Specification

### Build Tool

[Gradle](https://gradle.org/), bundled with [IntelliJ IDEA](https://www.jetbrains.com/idea/) community edition.

### Java Version

```CMD
$ java -version
java version "1.8.0_211"
Java(TM) SE Runtime Environment (build 1.8.0_211-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.211-b12, mixed mode)
```

## Legal

Uses [ZXing](https://github.com/zxing/zxing) barcode scanning library, so most credits go for them.  
They use Apache-2.0 License, so I thought using it too.
