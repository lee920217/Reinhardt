package uk.co.cnoversea.api.dao.model;

public class TaskWithBLOBs extends Task {
    private String description;

    private String through;

    private String throughCode;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getThrough() {
        return through;
    }

    public void setThrough(String through) {
        this.through = through == null ? null : through.trim();
    }

    public String getThroughCode() {
        return throughCode;
    }

    public void setThroughCode(String throughCode) {
        this.throughCode = throughCode == null ? null : throughCode.trim();
    }
}