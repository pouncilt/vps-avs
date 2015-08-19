package gov.va.med.avs.model;

import gov.va.med.lom.vistabroker.patient.data.VistaImageInfo;
import gov.va.med.lom.vistabroker.util.FMDateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tpouncil on 3/23/2015.
 */
public class AfterVisitSummaryFactory {
    public static List<AfterVisitSummary> createAfterVisitSummaries(List<VistaImageInfo> vistaImageInfoList) {
        List<AfterVisitSummary> afterVisitSummaries = new ArrayList<AfterVisitSummary>();

        for(VistaImageInfo vistaImageInfo: vistaImageInfoList){

            afterVisitSummaries.add(new AfterVisitSummary(
                    vistaImageInfo.getImageIen(),
                    vistaImageInfo.getPatientDFN(),
                    vistaImageInfo.getFilename(),
                    vistaImageInfo.getCapturedBy(),
                    vistaImageInfo.getImageSavedDateTimeStamp()));
        }

        return afterVisitSummaries;
    }
}
