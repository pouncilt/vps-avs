package gov.va.med.lom.avs.client.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gov.va.med.lom.avs.model.EncounterCache;
import gov.va.med.lom.avs.service.ServiceFactory;
import gov.va.med.lom.avs.service.SheetService;

public class EncounterCacheThread extends Thread {

  private SheetService sheetService;
  private EncounterCache encounterCache;
  
  private static final Log log = LogFactory.getLog(EncounterCacheThread.class);
  
  public EncounterCacheThread() {}
    
  public EncounterCacheThread(EncounterCache encounterCache) {
    this.encounterCache = encounterCache;
  }
  
  public void run() {
    try {
      this.sheetService = ServiceFactory.getSheetService();
      this.sheetService.updateEncounterCache(encounterCache);
    } catch(Exception e) {
      log.error("Error persisting encounter cache", e);
    }
  }
  
}
