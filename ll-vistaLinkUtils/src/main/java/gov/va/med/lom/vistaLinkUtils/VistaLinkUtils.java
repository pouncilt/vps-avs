package gov.va.med.lom.vistaLinkUtils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import gov.va.med.authentication.kernel.LoginUserInfoVO;
import gov.va.med.exception.FoundationsException;
import gov.va.med.vistalink.adapter.cci.VistaLinkAppProxyConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionFactory;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkVpidConnectionSpec;
import gov.va.med.vistalink.institution.InstitutionMappingDelegate;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VistaLinkUtils {

    private static final Log log = LogFactory.getLog(VistaLinkUtils.class);
    public static final char UP = '^';
    
    public static final String VISTALINK_CONNECTION_ERROR = "vistalink.connection.error";
    public static final String VISTALINK_RPC_ERROR = "vistalink.rpc.error";
    public static final String VISTALINK_FOUNDATIONS_ERROR = "vistalink.foundations.error";
    
    private static String jndiPrefix;  // prefix used with vistalink jndi name (may vary with JEE server)
    
    /*
     * Static Initializer
     */
    static {
    
      ResourceBundle res = null;
      try {
        // try to get properties file for VistaLinkUtils
        res = ResourceBundle.getBundle("vistalinkutils");
      } catch (MissingResourceException mre1) {
        // try properties file for VistaBroker
        try {
          res = ResourceBundle.getBundle("vistabroker");
        } catch (MissingResourceException mre2) {
          //ignore
        }
      }
      if (res != null) 
        jndiPrefix =  res.getString("jndi.prefix");
      if (jndiPrefix == null)
        jndiPrefix = "";
      
    }
    
    /**
     * Obtain a VistaLinkConnection from the passed in user information.
     * 
     * IMPORTANT!!!!!   The caller is responsible for closing the connection when
     *                  RPC complete
     *                  
     * @param stationNo
     * @param userVpid
     * @param userDuz
     * @return
     * @throws VistaLinkConnectionException
     */
    public static VistaLinkConnection getConnectionFromUserInfo(String stationNo, String userVpid, String userDuz) 
        throws VistaLinkConnectionException{

        try {
            // Create a connection if necessary
            VistaLinkConnectionSpec connSpec = null;
            Context ic = new InitialContext();
            if (!StringUtils.isBlank(userVpid)) {
                connSpec = new VistaLinkVpidConnectionSpec(stationNo, userVpid);
            } else {
                connSpec = new VistaLinkDuzConnectionSpec(stationNo, userDuz);
            }
            
            return getConnectionForSpec(connSpec, ic);
            
        } catch(Exception e){
            log.error("Error obtaining VistaLink connection", e);
            throw new VistaLinkConnectionException(e);
        }

    }
    
    
    /**
     * Obtain a VistaLinkConnection from the passed in user information.
     * 
     * IMPORTANT!!!!!   The caller is responsible for closing the connection when
     *                  RPC complete
     *                  
     * @param stationNo
     * @param userVpid
     * @param userDuz
     * @return
     * @throws VistaLinkConnectionException
     */
    public static VistaLinkConnection getConnectionFromAppProxy(String stationNo, String appProxy) 
        throws VistaLinkConnectionException{

    	if(StringUtils.isBlank(stationNo)){
    		log.error("must pass in a valid stationNo");
    		throw new VistaLinkConnectionException("must pass in a valid stationNo");
    	}
    	
    	if(StringUtils.isBlank(appProxy)){
    		log.error("must pass in a valid appProxy");
    		throw new VistaLinkConnectionException("must pass in a valid appProxy");
    	}
    	
        try {
            // Create a connection if necessary
            VistaLinkConnectionSpec connSpec = null;
            Context ic = new InitialContext();
            connSpec = new VistaLinkAppProxyConnectionSpec(stationNo, appProxy);
            
            return getConnectionForSpec(connSpec, ic);
            
        } catch(Exception e){
            log.error("Error obtaining VistaLink connection", e);
            throw new VistaLinkConnectionException(e);
        }

    }
    
    
    private static VistaLinkConnection getConnectionForSpec(VistaLinkConnectionSpec connSpec, Context context)
    	throws VistaLinkConnectionException{
    	
    	VistaLinkConnection conn = null;

        try {
            String jndiName = jndiPrefix + InstitutionMappingDelegate.getJndiConnectorNameForInstitution(connSpec.getDivision());
            VistaLinkConnectionFactory cf = (VistaLinkConnectionFactory) context.lookup(jndiName);
            conn = (VistaLinkConnection) cf.getConnection(connSpec);

            conn.setTimeOut(15000);
            
        } catch(Exception e){
            log.error("Error obtaining VistaLink connection", e);
            throw new VistaLinkConnectionException(e);
        }

        return conn;
    }
    
    
    
    /**
     * Obtain a VistaLinkConnection from the passed in LoginUserInfoVO.
     * 
     * IMPORTANT!!!!!   The caller is responsible for closing the connection when
     *                  RPC complete
     *                  
     * @param user
     * @return
     * @throws VistaLinkConnectionException
     */    
    public static VistaLinkConnection getConnectionFromLoginUserInfo(LoginUserInfoVO user) 
        throws VistaLinkConnectionException {
      return getConnectionFromUserInfo(user.getLoginStationNumber(), user.getUserVpid(), user.getUserDuz());
    }
    
    
    /**
     * Call an rpc from the RpcRequest passed in.  Makes sure that the connection
     * is closed after the RPC has been called.
     * 
     * @param req
     * @param user
     * @return
     * @throws VistaLinkRpcException
     * @throws VistaLinkConnectionException
     */
    public static RpcResponse call(RpcRequest req, String stationNo, String userVpid, String userDuz) 
        throws VistaLinkRpcException, VistaLinkConnectionException{
        
        RpcResponse result = null;
        VistaLinkConnection conn = getConnectionFromUserInfo(stationNo, userVpid, userDuz);
        
        try{
        	req.setTimeOut(conn.getTimeOut() * 2);
            result = conn.executeRPC(req);
        } catch (FoundationsException e) {
            log.error("RPC Error", e);
            throw new VistaLinkRpcException(e);
        }finally{
            try{
                conn.close();
            }catch(Exception e){
                // no op
            }
        }
        //log.debug("Function call: finished successfully.");
        return result;
        
    }
    
    /**
     * Call an rpc from the RpcRequest passed in.  Makes sure that the connection
     * is closed after the RPC has been called.
     * 
     * @param req
     * @param user
     * @return
     * @throws VistaLinkRpcException
     * @throws VistaLinkConnectionException
     */    
    public static RpcResponse call(RpcRequest req, LoginUserInfoVO user) 
        throws VistaLinkRpcException, VistaLinkConnectionException {
      RpcResponse result = null;
      VistaLinkConnection conn = getConnectionFromLoginUserInfo(user);
      
      try{
        req.setTimeOut(conn.getTimeOut() * 2);
          result = conn.executeRPC(req);
      } catch (FoundationsException e) {
          log.error("RPC Error", e);
          throw new VistaLinkRpcException(e);
      }finally{
          try{
              conn.close();
          }catch(Exception e){
              // no op
          }
      }
      //log.debug("Function call: finished successfully.");
      return result;      
      
    }

}
