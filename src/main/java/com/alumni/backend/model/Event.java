package com.alumni.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String date;
    private String location;
    private Long postedBy;

    public Event() {}

    public Long getId()                   { return id; }

    public String getName()               { return name; }
    public void setName(String name)      { this.name = name; }

    public String getDate()               { return date; }
    public void setDate(String date)      { this.date = date; }

    public String getLocation()               { return location; }
    public void setLocation(String location)  { this.location = location; }

    public Long getPostedBy()                 { return postedBy; }
    public void setPostedBy(Long postedBy)    { this.postedBy = postedBy; }
}
