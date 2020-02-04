package uk.co.cnoversea.api.dao.model;

public class TaskWithBLOBs extends Task {
    private String description;

    private String through;

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
}