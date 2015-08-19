package gov.va.med.avs.service;

import gov.va.med.avs.model.AfterVisitSummary;
import gov.va.med.avs.model.AfterVisitSummarySearchRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("afterVisitSummaryMockService")
public class AfterVisitSummaryMockService implements AfterVisitSummaryService {
    public AfterVisitSummaryMockService() {
        super();
    }

    @Override
    public List<AfterVisitSummary> findAfterVisitSummaries(AfterVisitSummarySearchRequest afterVisitSummarySearchRequest) {
        List<AfterVisitSummary> afterVisitSummaries = new ArrayList<AfterVisitSummary>();
        afterVisitSummaries.add(new AfterVisitSummary("0", "1234", "After Visit Summary", "After Visit Summary", new Date()));
        return afterVisitSummaries;
    }

    @Override
    public void  getAfterVisitSummary(String clientId, String veteranId,AfterVisitSummary afterVisitSummary ) {
        afterVisitSummary.setBinaryPDF(getPDF());
        // new AfterVisitSummary("0", "1234", "After Visit Summary", "After Visit Summary", new Date(), getPDF());
    }

    public byte[] getPDF() {
        Resource pdf = new ClassPathResource("avs-050_18.pdf");
        byte[] pdfBytes = new byte[0];
        try{
            pdfBytes = IOUtils.toByteArray(pdf.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfBytes;
    }
}