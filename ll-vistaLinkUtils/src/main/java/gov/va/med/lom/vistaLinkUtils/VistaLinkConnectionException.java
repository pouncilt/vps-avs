package gov.va.med.lom.vistaLinkUtils;

public class VistaLinkConnectionException extends Exception {

    public VistaLinkConnectionException(){
        super();
    }
    
    public VistaLinkConnectionException(Exception e){
        super(e);
    }
    
    public VistaLinkConnectionException(String msg, Exception e){
        super(msg,e);
    }
    
    public VistaLinkConnectionException(String msg){
        super(msg);
    }
    
}
