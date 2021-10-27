package Model;

import java.time.LocalDateTime;
import java.util.Date;

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    //private LocalDateTime start;
    //private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointment(int appointmentId, String title, String description, String location, String type, /*LocalDateTime start, LocalDateTime end,*/ int customerId, int userId, int contactId) {
        this.appointmentId=appointmentId;
        this.title=title;
        this.description=description;
        this.location=location;
        this.type=type;
        //this.start=start;
        //this.end=end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * @return the appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId the appointmentId to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the start to get
     */
    /*
    public LocalDateTime getStart() {
        return start;
    }

     */


    /**
     * @param start the start to set
     */
    /*
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    */

    /**
     * @return the end
     */
    /*
    public LocalDateTime getEnd() {
        return end;
    }

     */

    /**
     * @param end the end to set
     */
    /*
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    */

    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = userId;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the usderId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId the contactId to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}

