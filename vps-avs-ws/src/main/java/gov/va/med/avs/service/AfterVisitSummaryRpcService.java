package gov.va.med.avs.service;

import gov.va.med.avs.exception.*;
import gov.va.med.avs.model.AfterVisitSummary;
import gov.va.med.avs.model.AfterVisitSummaryFactory;
import gov.va.med.avs.model.AfterVisitSummarySearchCriteria;
import gov.va.med.avs.model.AfterVisitSummarySearchRequest;
import gov.va.med.lom.foundation.service.response.BaseServiceResponse;
import gov.va.med.lom.foundation.service.response.CollectionServiceResponse;
import gov.va.med.lom.foundation.service.response.messages.Message;
import gov.va.med.lom.vistabroker.patient.data.VistaImageInfo;
import gov.va.med.lom.vistabroker.patient.data.VistaImageQueueInfo;
import gov.va.med.lom.vistabroker.security.ISecurityContext;
import gov.va.med.lom.vistabroker.security.SecurityContextFactory;
import gov.va.med.lom.vistabroker.util.FMDateUtils;
import gov.va.med.lom.vistabroker.util.VistaBrokerServiceFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tpouncil on 3/23/2015.
 */
@Service("afterVisitSummaryRpcService")
@Primary
public class AfterVisitSummaryRpcService implements AfterVisitSummaryService {
    protected String division = System.getProperty("vista.link.division");
    protected String duz = System.getProperty("vista.link.duz");
    protected String imageServerPath =  System.getProperty("vista.image.server.pdf.path");
    protected Integer retryReadAttempts = Integer.valueOf(System.getProperty("vista.image.server.retryReadAttempts", "3"));
    protected Integer retryReadWait = Integer.valueOf(System.getProperty("vista.image.server.retryReadWait", "500"));
    protected Integer pdfMaximumByteSize = Integer.valueOf(System.getProperty("vista.image.server.pdfMaximumByteSize", "0"));

    public AfterVisitSummaryRpcService() {}

    @Override
    public List<AfterVisitSummary> findAfterVisitSummaries(AfterVisitSummarySearchRequest afterVisitSummarySearchRequest) throws ConfigurationFileNotFoundException {
        if(afterVisitSummarySearchRequest == null) throw new RequiredElementNotFoundException("afterVisitSummarySearchRequest is required; but not found.");

        afterVisitSummarySearchRequest.validate();
        AfterVisitSummarySearchCriteria avsSearchCriteria = afterVisitSummarySearchRequest.getAfterVisitSummarySearchCriteria();

        List<Object> requestParams = new ArrayList<Object>();
        requestParams.add(avsSearchCriteria.getVeteranId());
        requestParams.add(Integer.toString((int) FMDateUtils.dateToFMDate(avsSearchCriteria.getStartDate())));
        requestParams.add(Integer.toString((int) FMDateUtils.dateToFMDate(avsSearchCriteria.getEndDate())));

        ISecurityContext securityContext = SecurityContextFactory.createDuzSecurityContext(division, duz);
        CollectionServiceResponse<VistaImageInfo> csr = VistaBrokerServiceFactory.getPatientVBService().retrieveVistaImageDescriptions(securityContext, requestParams);
        List<VistaImageInfo> vistaImageInfoList = (List<VistaImageInfo>) csr.getCollection();

        if(BaseServiceResponse.hasErrorMessage(csr)){
            List<String> messages = new ArrayList<String>();
            for(Message message: csr.getMessages().getErrorMessages()){
                messages.add(message.getKey());
                throw new InvalidDateFormatException(messages);
            }

            throw new AfterVisitSummariesNotFoundException(messages);
        }

        for(VistaImageInfo vistaImageInfo: vistaImageInfoList){
            if(vistaImageInfo.getSequenceNumber() == null) {
                throw new AfterVisitSummariesNotFoundException(vistaImageInfo.getSiteCode());
            } else {
                if (vistaImageInfo.getSequenceNumber() == 0) {
                    // vistaImageInfo.getSiteCode() will contain the error message from RPC call.
                    throw new AfterVisitSummariesNotFoundException(vistaImageInfo.getSiteCode());
                } else if (vistaImageInfo.getSequenceNumber() == 1) {
                    // vistaImageInfo.getSiteCode() will contain the total number returned.
                    // vistaImageInfo.setPatientDFN(avsSearchCriteria.getVeteranId());
                }
            }
            break;
        }

        return AfterVisitSummaryFactory.createAfterVisitSummaries(vistaImageInfoList);
    }

    @Override
    public void getAfterVisitSummary(String clientId, String veteranId, AfterVisitSummary afterVisitSummary) {
        afterVisitSummary.validate();
        List<String> requestParams = new ArrayList<String>();
        requestParams.add(afterVisitSummary.getId());

        ISecurityContext securityContext = SecurityContextFactory.createDuzSecurityContext(division, duz);
        CollectionServiceResponse<VistaImageQueueInfo> csr = VistaBrokerServiceFactory.getPatientVBService().retrieveVistaImages(securityContext, requestParams);
        List<VistaImageQueueInfo> vistaImageQueueInfoList = (List<VistaImageQueueInfo>) csr.getCollection();

        for(VistaImageQueueInfo vistaImageQueueInfo: vistaImageQueueInfoList) {
            if(vistaImageQueueInfo.getImageIen().equals(afterVisitSummary.getId())){
                if(vistaImageQueueInfo.isError()){
                    throw new AfterVisitSummaryNotFoundException("Encountered problem queueing up image("+vistaImageQueueInfo.getImageIen() + ") for export.");
                }

                afterVisitSummary.setBinaryPDF(getPDF(imageServerPath + afterVisitSummary.getFileName(), retryReadAttempts, retryReadWait, pdfMaximumByteSize));
            }
        }
    }

    public byte[] getPDF(String pdfPath, int retryReadAttempts, int retryReadWait, Integer pdfMaximumByteSize) {
        pdfPath = appendPdfExtension(pdfPath);
        Resource pdf = new FileSystemResource(pdfPath);
        byte[] pdfBytes = new byte[0];
        int readAttempts = 0;

        if(pdf.exists() && pdf.isReadable()) {
            try {
                pdfBytes = IOUtils.toByteArray(pdf.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            while (!pdf.exists() && readAttempts < retryReadAttempts) {
                if (readAttempts >= retryReadAttempts) {
                    break;
                }

                if (pdf.exists() && pdf.isReadable()) {
                    try {
                        pdfBytes = IOUtils.toByteArray(pdf.getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    synchronized (this) {
                        Thread.sleep(retryReadWait);
                    }
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }

                readAttempts++;
            }
        }

        if(pdfBytes.length == 0) {
            throw new AfterVisitSummaryNotFoundException("After Visit Summary not found.");
        } else if(pdfMaximumByteSize != null && pdfMaximumByteSize > 0 && pdfMaximumByteSize < pdfBytes.length) {
            throw new RequestedResourceTooLargeException("The requested PDF is too large.");
        }

        return pdfBytes;
    }

    private String appendPdfExtension(String pdfPath) {
        if (pdfPath.lastIndexOf(File.separator) > 0){
            String pdfName = pdfPath.substring(pdfPath.lastIndexOf(File.separator) + 1, pdfPath.length());
            if(!pdfName.contains(".")){
                pdfPath = pdfPath + ".pdf";
            }
        } else {
            if(!pdfPath.contains(".")){
                pdfPath = pdfPath + ".pdf";
            }
        }
        return pdfPath;
    }
}
