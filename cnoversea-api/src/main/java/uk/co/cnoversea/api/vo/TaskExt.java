package uk.co.cnoversea.api.vo;

import uk.co.cnoversea.api.dao.model.TaskWithBLOBs;

public class TaskExt extends TaskWithBLOBs {

    private String startLongtitude;

    private String startLatitude;

    private String targetLongtitude;

    private String targetLatitude;

    public String getStartLongtitude() {
        return startLongtitude;
    }

    public void setStartLongtitude(String startLongtitude) {
        this.startLongtitude = startLongtitude;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getTargetLongtitude() {
        return targetLongtitude;
    }

    public void setTargetLongtitude(String targetLongtitude) {
        this.targetLongtitude = targetLongtitude;
    }

    public String getTargetLatitude() {
        return targetLatitude;
    }

    public void setTargetLatitude(String targetLatitude) {
        this.targetLatitude = targetLatitude;
    }
}
