package gov.va.med.lom.vistaLinkUtils.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gov.va.med.exception.FoundationsException;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;
import gov.va.med.vistalink.rpc.RpcResponse;

import gov.va.med.lom.vistaLinkUtils.*;

public class TestVistaLinkUtils {
  
  private static final Log log = LogFactory.getLog(TestVistaLinkUtils.class);
  
  public static void main(String[] args) throws Exception {
   
    String stationNo = "605";
    String userDuz = "9276";
    
    RpcResponse rpcResponse = null; 
    RpcRequest req = null;
      
    try {
      req = RpcRequestFactory.getRpcRequest();
      //req.setRpcContext("OR CPRS GUI CHART");
      req.setRpcContext("VPS AVS INTERFACE");
      //req.setRpcName("ORWU USERINFO");
      req.setRpcName("VPS USERINFO");

      rpcResponse = VistaLinkUtils.call(req, stationNo, null, userDuz);
      
      String y = rpcResponse.getResults();
        
      System.out.println(y);
                        
    } catch (FoundationsException e){
      throw new VistaLinkRpcException(VistaLinkUtils.VISTALINK_FOUNDATIONS_ERROR, e);
    } catch (VistaLinkRpcException e) {
      throw new VistaLinkRpcException(VistaLinkUtils.VISTALINK_RPC_ERROR, e);
    } catch (VistaLinkConnectionException e) {
      throw new VistaLinkConnectionException(VistaLinkUtils.VISTALINK_CONNECTION_ERROR, e);
    }            
    
  }
 
}
