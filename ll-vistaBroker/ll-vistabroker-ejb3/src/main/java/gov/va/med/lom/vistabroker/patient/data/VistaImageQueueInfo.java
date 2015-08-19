package gov.va.med.lom.vistabroker.patient.data;

import java.io.Serializable;

/**
 * Created by tpouncil on 3/23/2015.
 */
public class VistaImageQueueInfo  implements Serializable {
    private String imageIen;
    private String nextImageIenInQueue;
    private String imageFileName;

    private Boolean isError = false;

    public VistaImageQueueInfo(String imageIen, String nextImageIenInQueue, String imageFileName) {
        this.imageIen = imageIen;
        this.nextImageIenInQueue = nextImageIenInQueue;
        this.imageFileName = imageFileName;
    }

    public String getImageIen() {
        return imageIen;
    }

    public void setImageIen(String imageIen) {
        this.imageIen = imageIen;
    }

    public String getNextImageIenInQueue() {
        return nextImageIenInQueue;
    }

    public void setNextImageIenInQueue(String nextImageIenInQueue) {
        this.nextImageIenInQueue = nextImageIenInQueue;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Boolean isError() {
        return (nextImageIenInQueue == null || nextImageIenInQueue.trim().equals("0"))? true: false;
    }

    @Override
    public String toString() {
        return "VistaImageQueueInfo{" +
                "imageIen='" + imageIen + '\'' +
                ", nextImageIenInQueue='" + nextImageIenInQueue + '\'' +
                ", imageFileName='" + imageFileName + '\'' +
                ", isError=" + isError +
                '}';
    }
}
