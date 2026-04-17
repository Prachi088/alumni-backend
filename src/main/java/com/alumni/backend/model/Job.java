package com.alumni.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    private String location;
    private Long postedBy;

    public Job() {}

    public Long getId()                   { return id; }

    public String getTitle()              { return title; }
    public void setTitle(String title)    { this.title = title; }

    public String getCompany()                { return company; }
    public void setCompany(String company)    { this.company = company; }

    public String getLocation()               { return location; }
    public void setLocation(String location)  { this.location = location; }

    public Long getPostedBy()                 { return postedBy; }
    public void setPostedBy(Long postedBy)    { this.postedBy = postedBy; }
}
