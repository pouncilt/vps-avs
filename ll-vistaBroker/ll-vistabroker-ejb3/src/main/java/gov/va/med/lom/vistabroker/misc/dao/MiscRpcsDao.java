package gov.va.med.lom.vistabroker.misc.dao;

import gov.va.med.lom.javaUtils.misc.StringUtils;
import gov.va.med.lom.vistabroker.dao.BaseDao;
import gov.va.med.lom.vistabroker.ddr.DdrQuery;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;

public class MiscRpcsDao extends BaseDao {
  
  /*
   * CONSTRUCTORS
   */ 
  public MiscRpcsDao() {
    super();
  }
  
  public MiscRpcsDao(BaseDao baseDao) {
    super(baseDao);
  }
  
  /*
   * RPC API
   */ 
    // use %DT to validate and convert a string to Fileman format (accepts T, T-1, NOW, etc.)
    public double strToFMDateTime(String str) throws Exception {
      //setDefaultContext("OR CPRS GUI CHART");
      //setDefaultRpcName("ORWU DT");
      
      setDefaultContext("VPS AVS INTERFACE");
      setDefaultRpcName("VPS DT");
      return StringUtils.toDouble(sCall(str), 0.0);
    }
    
    // use %DT to validate & convert a string to Fileman format, accepts %DT flags
/*    public double validDateTimeStr(String str, String flags) throws Exception {
      setDefaultContext("OR CPRS GUI CHART");
      setDefaultRpcName("ORWU VALDT");
      String[] params = {str, flags};
      return StringUtils.toDouble(sCall(params), 0.0);
    }  */  
    
    public double fmNow() throws Exception {
      return strToFMDateTime("NOW");
    }
    
    public int fmToday() throws Exception {
      return (int)fmNow();
    }
    
    // returns the external name of the IEN within a file
 /*   public String externalName(String ien, double fileNumber) throws Exception {
      setDefaultContext("OR CPRS GUI CHART");
      setDefaultRpcName("ORWU EXTNAME");
      String[] params = {ien, String.valueOf(fileNumber)};
      return sCall(params);
    }*/
    
    public String getVariableValue(String arg) throws Exception {
      //setDefaultContext("OR CPRS GUI CHART"); //DdrQuery.PCMM_CONTEXT
      setDefaultContext("VPS AVS INTERFACE"); //DdrQuery.PCMM_CONTEXT
      RpcRequest req = null;
      req = RpcRequestFactory.getRpcRequest();
      //req.setRpcName("XWB GET VARIABLE VALUE");
      req.setRpcName("VPS GET VARIABLE VALUE");
      req.getParams().setParam(1, "ref", arg);
      return sCall(req);      
    }
  
}