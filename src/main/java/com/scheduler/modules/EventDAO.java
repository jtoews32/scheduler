package com.scheduler.modules;

/*
 events: [
    { id: '1', resourceId: 'a', start: '2020-02-06', end: '2020-02-08', title: 'event 1' },
    { id: '2', resourceId: 'a', start: '2020-02-07T09:00:00', end: '2020-02-07T14:00:00', title: 'event 2' },
  ],
*/
import javax.persistence.*; 
import java.sql.Timestamp;

@Entity
public class EventDAO { 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ResourceId", nullable = false, insertable = true, updatable = true, length = 150)
    private String resourceId;
    public String getResourceId() {
        return resourceId;
    }
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Column(name = "Start", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    private Timestamp start;
    public Timestamp getStart() {
        return start;
    }
    public void setStart(Timestamp start) {
        this.start = start;
    }

    @Column(name = "End", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    private Timestamp end;
    public Timestamp getEnd() {
        return end;
    }
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    @Column(name = "Title", nullable = true, insertable = true, updatable = true, length = 150)
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "Duration", insertable = true, updatable = true)
    private int duration;
    public int getDuration() {

        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "EventDAO [id=" + id + ", resourceId=" + resourceId + ", start=" + start + ", end=" + end + ", title="
                + title + ", duration=" + duration + "]";
    } 
}