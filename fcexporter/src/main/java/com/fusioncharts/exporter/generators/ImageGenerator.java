package com.fusioncharts.exporter.generators;

import com.fusioncharts.exporter.beans.ChartMetadata;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class ImageGenerator {
    private static Logger logger = null;

    static {
        logger = Logger.getLogger(ImageGenerator.class.getName());
    }

    public ImageGenerator() {
    }

    public static BufferedImage getChartImage(String data, ChartMetadata metadata) {
        logger.info("Creating the Chart image");
        boolean width = false;
        boolean height = false;
        String bgcolor = "";
        int var19 = metadata.getWidth();
        int var20 = metadata.getHeight();
        if(var19 == 0 || var20 == 0) {
            logger.severe("Image width/height not provided.");
        }

        bgcolor = metadata.getBgColor();
        if(bgcolor == null || bgcolor == "" || bgcolor == null) {
            bgcolor = "FFFFFF";
        }

        Color bgColor = new Color(Integer.parseInt(bgcolor, 16));
        if(data == null) {
            logger.severe("Image Data not supplied.");
        }

        BufferedImage chart = null;

        try {
            String[] rows = new String[var20 + 1];
            rows = data.split(";");
            chart = new BufferedImage(var19, var20, 5);
            Graphics2D e = chart.createGraphics();
            e.setColor(bgColor);
            e.fillRect(0, 0, var19, var20);
            boolean ri = false;

            for(int i = 0; i < rows.length; ++i) {
                String[] pixels = rows[i].split(",");
                int var21 = 0;

                for(int j = 0; j < pixels.length; ++j) {
                    String[] clrs = pixels[j].split("_");
                    String c = clrs[0];
                    int r = Integer.parseInt(clrs[1]);
                    if(c != null && c.length() > 0 && c != "") {
                        if(c.length() < 6) {
                            StringBuffer k = new StringBuffer(c);

                            for(int p = c.length() + 1; p <= 6; ++p) {
                                k.insert(0, "0");
                            }

                            c = k.toString();
                        }

                        for(int var22 = 1; var22 <= r; ++var22) {
                            e.setColor(new Color(Integer.parseInt(c, 16)));
                            e.fillRect(var21, i, 1, 1);
                            ++var21;
                        }
                    } else {
                        var21 += r;
                    }
                }
            }

            logger.info("Image created successfully");
        } catch (Exception var18) {
            logger.severe("Image data is not in proper format:" + var18.toString());
        }

        return chart;
    }
}