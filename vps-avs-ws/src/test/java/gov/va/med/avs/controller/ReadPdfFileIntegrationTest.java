package gov.va.med.avs.controller;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by tpouncil on 3/24/2015.
 */
public class ReadPdfFileIntegrationTest {

    @Test
    public void testReadingPdfFileOutNetwork() {
        byte[] pdfBinary = getPDF("\\\\VPS-DEV01\\C$\\IMAGES\\GCC\\ZZ000000000039.pdf");

        assertNotNull(pdfBinary);
        assertTrue(pdfBinary.length > 0);
    }

    public byte[] getPDF(String pdfPath) {
        Resource pdf = new FileSystemResource(pdfPath);
        byte[] pdfBytes = new byte[0];
        try{
            pdfBytes = IOUtils.toByteArray(pdf.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfBytes;
    }
}
