package Model;

import java.sql.Timestamp;

/**
 * Class for the appointments model.
 *
 * @author Erik Nilson
 */
public class Appointment {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private int contact_ID;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customer_ID;
    private int user_ID;

    /**
     * Appointment constructors.
     */
    public Appointment(int appointment_ID, String title, String description, String location, String type, Timestamp start, Timestamp end, int contact_ID, int customer_ID, int user_ID) {
        this.appointment_ID=appointment_ID;
        this.title=title;
        this.description=description;
        this.location=location;
        this.contact_ID=contact_ID;
        this.type=type;
        this.start=start;
        this.end=end;
        this.customer_ID=customer_ID;
        this.user_ID=user_ID;
    }

    public Appointment(int appointmentId, String title, String description, String type, Timestamp start, Timestamp end, int customerId) {
    }

    /**
     * Getter for appointment ID.
     *
     * @return the appointmentId.
     */
    public int getAppointment_ID() {
        return appointment_ID;
    }

    /**
     * Setter for appointment ID.
     *
     * @param appointment_ID the appointmentId to set
     */
    public void setAppointment_ID(int appointment_ID) {
        this.appointment_ID = appointment_ID;
    }

    /**
     * Getter for title.
     *
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the title.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description.
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the location.
     *
     * @return the location to get.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for the location.
     *
     * @param location the location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for contact ID.
     *
     * @return the contactId to get.
     */
    public int getContact_ID() {
        return contact_ID;
    }

    /**
     * Setter for contact ID.
     *
     * @param contact_ID the contactId to set
     */
    public void setContact_ID(int contact_ID) {
        this.contact_ID = contact_ID;
    }

    /**
     * Getter for the type.
     *
     * @return the type to get.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for the type.
     *
     * @param type the type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for the start.
     *
     * @return the start to get.
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * Setter for the start.
     *
     * @param start the start to set.
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * Getter for the end.
     *
     * @return the end getter.
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * Setter for the end.
     *
     * @param end the end to set.
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * Getter for the customer ID.
     *
     * @return the customerID to get.
     */
    public int getCustomer_ID() {
        return customer_ID;
    }

    /**
     * Setter for the customer ID.
     * @param customer_ID the customerId to set.
     */
    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = user_ID;
    }

    /**
     * Getter for the User ID.
     *
     * @return the userID to get.
     */
    public int getUser_ID() {
        return user_ID;
    }

    /**
     * Setter for the user ID.
     *
     * @param user_ID the usderId to set.
     */
    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }
}

