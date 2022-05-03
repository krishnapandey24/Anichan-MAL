package com.omnicoder.anichan.Models;

import java.util.Map;

public class Crew {
    private String job,department,name,profile_path;
    private Double id;

    public Crew(Map<String,Object> map) {
        this.job = (String) map.get("job");
        this.department = (String) map.get("department");
        this.name = (String) map.get("name");
        this.profile_path = (String) map.get("profile_path");
        this.id = (Double) map.get("id");
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public int getId() {
        return id.intValue();
    }
}
