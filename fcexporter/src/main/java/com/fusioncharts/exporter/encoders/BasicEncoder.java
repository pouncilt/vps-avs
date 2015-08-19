package com.fusioncharts.exporter.encoders;

import com.fusioncharts.exporter.encoders.Encoder;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

public class BasicEncoder implements Encoder {
    String defaultFormat = "JPEG";

    public BasicEncoder() {
    }

    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream) throws Throwable {
        this.encode(bufferedImage, (FileImageOutputStream)fileImageOutputStream, 1.0F);
    }

    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream, float quality) throws Throwable {
        this.encode(bufferedImage, (FileImageOutputStream)fileImageOutputStream, quality, this.defaultFormat);
    }

    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream, float quality, String format) throws Throwable {
        Iterator writers = ImageIO.getImageWritersByFormatName(format);
        ImageWriter writer = (ImageWriter)writers.next();
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        writer.setOutput(fileImageOutputStream);
        writer.write((IIOMetadata)null, new IIOImage(bufferedImage, (List)null, (IIOMetadata)null), iwp);
        fileImageOutputStream.close();
    }

    public void encode(BufferedImage bufferedImage, OutputStream outputStream) throws Throwable {
        this.encode(bufferedImage, (OutputStream)outputStream, 1.0F);
    }

    public void encode(BufferedImage bufferedImage, OutputStream outputStream, float quality) throws Throwable {
        this.encode(bufferedImage, (OutputStream)outputStream, quality, this.defaultFormat);
    }

    public void encode(BufferedImage bufferedImage, OutputStream outputStream, float quality, String format) throws Throwable {
        ImageOutputStream ios = null;

        try {
            Iterator e = ImageIO.getImageWritersByFormatName(format);
            ImageWriter writer = (ImageWriter)e.next();
            ImageWriteParam iwp = writer.getDefaultWriteParam();
            ios = ImageIO.createImageOutputStream(outputStream);
            writer.setOutput(ios);
            writer.write((IIOMetadata)null, new IIOImage(bufferedImage, (List)null, (IIOMetadata)null), iwp);
            ios.close();
        } catch (IllegalArgumentException var9) {
            if(ios != null) {
                ios.close();
            }

            throw new Throwable();
        } catch (IOException var10) {
            if(ios != null) {
                ios.close();
            }

            throw new Throwable();
        }
    }

    public void encode(BufferedImage bufferedImage, Writer out, float quality, String format) throws Throwable {
        ImageOutputStream ios = null;

        try {
            Iterator e = ImageIO.getImageWritersByFormatName(format);
            ImageWriter writer = (ImageWriter)e.next();
            ImageWriteParam iwp = writer.getDefaultWriteParam();
            ios = ImageIO.createImageOutputStream(out);
            writer.setOutput(ios);
            writer.write((IIOMetadata)null, new IIOImage(bufferedImage, (List)null, (IIOMetadata)null), iwp);
            ios.close();
        } catch (IllegalArgumentException var9) {
            if(ios != null) {
                ios.close();
            }

            throw new Throwable();
        } catch (IOException var10) {
            if(ios != null) {
                ios.close();
            }

            throw new Throwable();
        }
    }
}