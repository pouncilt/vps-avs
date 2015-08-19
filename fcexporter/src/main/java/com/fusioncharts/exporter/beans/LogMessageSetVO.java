package com.fusioncharts.exporter.beans;

import com.fusioncharts.exporter.error.LOGMESSAGE;
import java.util.HashSet;
import java.util.Set;

public class LogMessageSetVO {
    Set<LOGMESSAGE> errorsSet = new HashSet();
    Set<LOGMESSAGE> warningSet = new HashSet();
    String otherMessages = null;

    public LogMessageSetVO() {
    }

    public void addError(LOGMESSAGE error) {
        if(this.errorsSet == null) {
            this.errorsSet = new HashSet();
        }

        this.errorsSet.add(error);
    }

    public void addWarning(LOGMESSAGE warning) {
        if(this.warningSet == null) {
            this.warningSet = new HashSet();
        }

        this.warningSet.add(warning);
    }

    public Set<LOGMESSAGE> getErrorsSet() {
        return this.errorsSet;
    }

    public String getOtherMessages() {
        return this.otherMessages;
    }

    public Set<LOGMESSAGE> getWarningSet() {
        return this.warningSet;
    }

    public void setErrorsSet(Set<LOGMESSAGE> errorsSet) {
        this.errorsSet = errorsSet;
    }

    public void setOtherMessages(String otherMessages) {
        this.otherMessages = otherMessages;
    }

    public void setWarningSet(Set<LOGMESSAGE> warningSet) {
        this.warningSet = warningSet;
    }
}