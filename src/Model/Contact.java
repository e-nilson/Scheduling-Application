package Model;

/**
 * Class for the contact model.
 *
 * @author Erik Nilson
 */
public class Contact {
    private int contact_ID;
    private String contact_Name;
    private String email;

    /**
     * Contact constructors.
     */
    public Contact(int contact_ID, String contact_Name, String email) {
        this.contact_ID = contact_ID;
        this.contact_Name = contact_Name;
        this.email = email;
    }

    /**
     * @return the contactId
     */
    public int getContact_ID() {
        return contact_ID;
    }

    /**
     * @param contact_ID the contactId to set
     */
    public void setContact_ID(int contact_ID) {
        this.contact_ID = contact_ID;
    }

    /**
     * @return the contactName
     */
    public String getContact_Name() {
        return contact_Name;
    }

    /**
     * @param contact_Name the contactName to set
     */
    public void setContact_Name(String contact_Name) {
        this.contact_Name = contact_Name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return ("Contact Name: " +(contact_Name )+ " Contact ID: " + contact_ID);
    }
}
