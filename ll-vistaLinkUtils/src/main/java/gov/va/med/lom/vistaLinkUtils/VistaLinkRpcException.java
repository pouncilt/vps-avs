package gov.va.med.lom.vistaLinkUtils;

public class VistaLinkRpcException extends Exception {

    public VistaLinkRpcException(){
        super();
    }
    
    public VistaLinkRpcException(Exception e){
        super(e);
    }
    
    public VistaLinkRpcException(String msg, Exception e){
        super(msg,e);
    }
    
    public VistaLinkRpcException(String msg){
        super(msg);
    }
    
}
