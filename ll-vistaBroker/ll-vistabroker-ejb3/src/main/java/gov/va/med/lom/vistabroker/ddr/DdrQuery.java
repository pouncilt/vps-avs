package gov.va.med.lom.vistabroker.ddr;

import gov.va.med.lom.vistabroker.security.ISecurityContext;
import gov.va.med.lom.vistabroker.dao.BaseDao;

public class DdrQuery extends BaseDao {

  public static final String CAPRI_CONTEXT = "DVBA CAPRI GUI";
  //public static final String PCMM_CONTEXT = "SCMC PCMM GUI WORKSTATION";
  public static final String PCMM_CONTEXT = "VPS AVS INTERFACE";
  
  protected boolean useScLister;
  protected ISecurityContext securityContext;
  
  // CONSTRUCTORS
  public DdrQuery(ISecurityContext securityContext) {
    super();
    setSecurityContext(securityContext);
  }
  
  public DdrQuery(BaseDao baseDao) {
    super(baseDao);
  }
  
  // RPC API
  public String execute(String api, Object[] params) throws Exception {
    if ((getDefaultContext() == null) || !getDefaultContext().equals(CAPRI_CONTEXT)) {
      if (this.useScLister) {
        setDefaultContext(PCMM_CONTEXT); 
      } else {
        setDefaultContext(CAPRI_CONTEXT);
      }
    }
    setDefaultRpcName(api);
    return sCall(params);
  }

  public boolean isUseScLister() {
    return useScLister;
  }

  public void setUseScLister(boolean useScLister) {
    this.useScLister = useScLister;
  }
  
}
