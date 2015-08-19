package gov.va.med.lom.javaBroker.rpc.admin;

import gov.va.med.lom.javaBroker.rpc.AbstractRpc;
import gov.va.med.lom.javaBroker.rpc.BrokerException;
import gov.va.med.lom.javaBroker.rpc.RpcBroker;
import gov.va.med.lom.javaBroker.util.DateUtils;
import gov.va.med.lom.javaBroker.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SurgeryScheduleRpc extends AbstractRpc{

	
	/** Creates a new instance of EquipmentFetch */
    public SurgeryScheduleRpc() {
        super();
    }
    
    public SurgeryScheduleRpc(RpcBroker rpcBroker) throws BrokerException {
        super(rpcBroker);
    }
	
/*    @SuppressWarnings("unchecked")
	public synchronized List<SurgeryScheduleItem> fetchCases(Calendar date) throws BrokerException {
        
        if(!setContext("R1SRL OR SCHEDULE VIEWER"))
            return null;
        
        ArrayList<String> arr = null;
        
        try{
        	String start = String.valueOf(DateUtils.formatDate(date.getTime(), DateUtils.ENGLISH_DATE_FORMAT));
        	Object[] params = {start};
        	arr = lCall("R1SRL GET SDATA", params);
        }catch(Exception e){
        	return null;
        }
        
        List<SurgeryScheduleItem> items = new ArrayList<SurgeryScheduleItem>();
        SurgeryScheduleItem item;
        
        for (int i = 0; i < arr.size(); i++){
            String x = (String)arr.get(i);
            System.out.println(x);
            item = new SurgeryScheduleItem();
            item.setOperatingRoom(StringUtils.piece(x, 1));
            item.setPatientName(StringUtils.piece(x, 2));
            item.setLast4(StringUtils.piece(x, 3));
            item.setPatientLocation(StringUtils.piece(x, 4));
            item.setPrincipleProcedure(StringUtils.piece(x, 5));
            item.setOtherProcedure(StringUtils.piece(x, 6));
            item.setScheduledStartTime(StringUtils.piece(x, 7));
            item.setEstmatedCompletionTime(StringUtils.piece(x, 8));
            item.setCaseLength(StringUtils.piece(x, 9));
            item.setOperationEndTime(StringUtils.piece(x, 10));
            item.setStatus(StringUtils.piece(x, 11));
            //StringUtils.piece(x, 12);  piece 12 appears to never get set
            item.setTimeInOr(StringUtils.piece(x, 13));
            item.setInductionCompletionTime(StringUtils.piece(x, 14));
            item.setActualBeginTime(StringUtils.piece(x, 15));
            item.setActualEndTime(StringUtils.piece(x, 16));
            item.setTimeOutOfOr(StringUtils.piece(x, 17));
            item.setOrOccupancyTime(StringUtils.piece(x, 18));
            item.setSurgeonName(StringUtils.piece(x, 19));
            item.setAttendingName(StringUtils.piece(x, 20));
            item.setAnesthesiologistName(StringUtils.piece(x, 21));
            item.setAnesthesiologistSupervisor(StringUtils.piece(x, 22));
            item.setCirculationNurseName(StringUtils.piece(x, 23));
            item.setScrubNurseName(StringUtils.piece(x, 24));
            item.setIen(StringUtils.piece(x, 25));
            item.setConcurrentProcedure(StringUtils.piece(x, 26));
            item.setCaseScheduleType(StringUtils.piece(x, 27));
            item.setSpecialty(StringUtils.piece(x, 28));
            item.setAverageCaseLength(StringUtils.piece(x, 30));
            item.setPatientDisposition(StringUtils.piece(x, 31));
            item.setCaseScheduleOrder(StringUtils.piece(x, 32));
            items.add(item);
        }
        
        return items;
    }*/
    
/*    public synchronized List<SurgeryRequestItem> fetchRequests(Calendar startDate, Calendar endDate) throws BrokerException {
      
      if(!setContext("R1SRL OR SCHEDULE VIEWER"))
          return null;
      
      ArrayList<String> arr = null;
      
      try{
        String start = String.valueOf(DateUtils.formatDate(startDate.getTime(), DateUtils.ENGLISH_DATE_FORMAT));
        String end = String.valueOf(DateUtils.formatDate(endDate.getTime(), DateUtils.ENGLISH_DATE_FORMAT));
        Object[] params = {start, end};
        arr = lCall("R1SRL GET REQDATA", params);
      } catch(Exception e){
        return null;
      }    
      List<SurgeryRequestItem> items = new ArrayList<SurgeryRequestItem>();
      SurgeryRequestItem item;
      
      for (int i = 0; i < arr.size(); i++){
          String x = (String)arr.get(i);
          System.out.println(x);
          item = new SurgeryRequestItem();
          item.setOperationDate(StringUtils.piece(x, 1));
          item.setIen(StringUtils.piece(x, 2));
          item.setPatientDfn(StringUtils.piece(x, 3));
          item.setPatientName(StringUtils.piece(x, 4));
          item.setPatientLast4(StringUtils.piece(x, 5));
          item.setWardLocation(StringUtils.piece(x, 6));
          item.setSpecialty(StringUtils.piece(x, 7));
          item.setPrincipleProcedure(StringUtils.piece(x, 8));
          item.setPlannedProcedureCode(StringUtils.piece(x, 9));
          item.setOtherProcedure(StringUtils.piece(x, 10));
          item.setEstCaseLength(StringUtils.piece(x, 11));
          item.setCancellationDate(StringUtils.piece(x, 12));
          items.add(item);
      }
      
    
      return items;
    }*/
}
