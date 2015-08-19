package com.fusioncharts.exporter.encoders;

import com.fusioncharts.exporter.encoders.Encoder;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

public class JPEGEncoder implements Encoder {
    public JPEGEncoder() {
    }

    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream) throws Throwable {
        this.encode(bufferedImage, (FileImageOutputStream)fileImageOutputStream, 1.0F);
    }

    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream, float quality) throws Throwable {
        Iterator writers = ImageIO.getImageWritersByFormatName("JPEG");
        ImageWriter writer = (ImageWriter)writers.next();
        JPEGImageWriteParam params = new JPEGImageWriteParam((Locale)null);
        params.setCompressionMode(2);
        params.setCompressionQuality(quality);
        params.setProgressiveMode(0);
        params.setDestinationType(new ImageTypeSpecifier(IndexColorModel.getRGBdefault(), IndexColorModel.getRGBdefault().createCompatibleSampleModel(16, 16)));
        IIOImage image = new IIOImage(bufferedImage, (List)null, (IIOMetadata)null);
        writer.setOutput(fileImageOutputStream);
        writer.write((IIOMetadata)null, image, params);
        fileImageOutputStream.close();
    }

    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream, float quality, String format) throws Throwable {
        this.encode(bufferedImage, (FileImageOutputStream)fileImageOutputStream, quality);
    }

    public void encode(BufferedImage bufferedImage, OutputStream outputStream) throws Throwable {
        this.encode(bufferedImage, (OutputStream)outputStream, 1.0F);
    }

    public void encode(BufferedImage bufferedImage, OutputStream outputStream, float quality) throws Throwable {
        ImageOutputStream ios = null;

        try {
            Iterator e = ImageIO.getImageWritersByFormatName("JPEG");
            ImageWriter writer = (ImageWriter)e.next();
            JPEGImageWriteParam params = new JPEGImageWriteParam((Locale)null);
            params.setCompressionMode(2);
            params.setCompressionQuality(quality);
            params.setProgressiveMode(0);
            params.setDestinationType(new ImageTypeSpecifier(IndexColorModel.getRGBdefault(), IndexColorModel.getRGBdefault().createCompatibleSampleModel(16, 16)));
            IIOImage image = new IIOImage(bufferedImage, (List)null, (IIOMetadata)null);
            ios = ImageIO.createImageOutputStream(outputStream);
            writer.setOutput(ios);
            writer.write((IIOMetadata)null, image, params);
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

    public void encode(BufferedImage bufferedImage, OutputStream outputStream, float quality, String format) throws Throwable {
        this.encode(bufferedImage, (OutputStream)outputStream, quality);
    }
}