package com.fusioncharts.exporter.encoders;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import javax.imageio.stream.FileImageOutputStream;

public interface Encoder {
    void encode(BufferedImage var1, FileImageOutputStream var2) throws Throwable;

    void encode(BufferedImage var1, FileImageOutputStream var2, float var3) throws Throwable;

    void encode(BufferedImage var1, FileImageOutputStream var2, float var3, String var4) throws Throwable;

    void encode(BufferedImage var1, OutputStream var2) throws Throwable;

    void encode(BufferedImage var1, OutputStream var2, float var3) throws Throwable;

    void encode(BufferedImage var1, OutputStream var2, float var3, String var4) throws Throwable;
}