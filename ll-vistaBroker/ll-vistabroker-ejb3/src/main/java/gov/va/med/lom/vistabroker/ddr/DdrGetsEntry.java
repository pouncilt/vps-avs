package gov.va.med.lom.vistabroker.ddr;

import java.util.HashMap;
import java.util.List;

import gov.va.med.lom.javaUtils.misc.StringUtils;
import gov.va.med.lom.vistabroker.dao.BaseDao;
import gov.va.med.lom.vistabroker.security.ISecurityContext;

/*
 * Calls database server at GETS^DIQ.
 */
public class DdrGetsEntry extends DdrQuery {

  public static final String DDR_GETS_ENTRY_DATA = "DDR GETS ENTRY DATA";
  public static final String SC_GETS_ENTRY_DATA = "SC GETS ENTRY DATA";
  
  private String file;
  private String iens;
  private String fields;
  private String flags;
  
  // CONSTRUCTORS
  public DdrGetsEntry(ISecurityContext securityContext) {
    super(securityContext);
  }
  
  public DdrGetsEntry(BaseDao baseDao) {
    super(baseDao);
  }  
  
  // RPC API  
  public List<String> execute(DdrParams ddrParams) throws Exception {
    this.setFile(ddrParams.getFile());
    this.setIens(ddrParams.getIens());
    this.setFields(ddrParams.getFields());
    this.setFlags(ddrParams.getFlags());
    return this.execute();
  }  
  
  public List<String> execute() throws Exception {
    if ((file == null) || (file.equals(""))) {
      throw new Exception("Must have a file");
    }
    if ((iens == null) || (iens.equals(""))) {
      throw new Exception("Must have IENS");
    }
    if ((fields == null) || (fields.equals(""))) {
      throw new Exception("Must have a field");
    }  
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("\"FILE\"", file);
    map.put("\"IENS\"", iens);
    map.put("\"FIELDS\"", fields);
    if ((flags != null) && (!flags.equals(""))) {
      map.put("\"FLAGS\"", flags);
    }
    Object[] params = {map};
    String rpc = super.isUseScLister() ? SC_GETS_ENTRY_DATA : DDR_GETS_ENTRY_DATA;
    String response = super.execute(rpc, params);
    return StringUtils.getStringList(response);
  }
  
  // Getter/Setter Methods
  public String getFile() {
    return file;
  }
  
  public void setFile(String file) {
    this.file = file;
  }
  
  public String getIens() {
    return iens;
  }
  
  public void setIens(String iens) {
    this.iens = iens;
  }
  
  public String getFields() {
    return fields;
  }
  
  public void setFields(String fields) {
    this.fields = fields;
  }
  
  public String getFlags() {
    return flags;
  }
  
  public void setFlags(String flags) {
    this.flags = flags;
  }
  
}
