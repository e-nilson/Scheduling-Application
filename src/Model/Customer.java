package Model;

import Utils.ListProvider;

/**
 * Class for the Customer model.
 *
 * @author Erik Nilson
 */
public class Customer extends ListProvider {

    private int customer_ID; //Auto-gen
    private String customer_Name;
    private String address;
    private String postal_Code;
    private String phone;
    private int division_ID;

    /**
     * Customer constructors.
     */
    public Customer(int customer_ID, String customerName, String address, String postalCode, String phone, int divisionID) {
        this.customer_ID = customer_ID;
        this.customer_Name = customerName;
        this.address = address;
        this.postal_Code = postalCode;
        this.phone = phone;
        this.division_ID = divisionID;
    }


    /**
     * @return the customer ID
     */
    public int getCustomer_ID() {
        return customer_ID;
    }

    /**
     * @param customer_ID the customer ID to set
     */
    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    /**
     * @return the customer name
     */
    public String getCustomer_Name() {
        return customer_Name;
    }

    /**
     * @param customer_Name the customer name to set
     */
    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }

    /**
     * @return the customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the customer address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the customer postal code
     */
    public String getPostal_Code() {
        return postal_Code;
    }

    /**
     * @param postal_Code the customer postal code to set
     */
    public void setPostal_Code(String postal_Code) {
        this.postal_Code = postal_Code;
    }

    /**
     * @return the customer phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the customer phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the customer division ID
     */
    public int getDivision_ID() {
        return division_ID;
    }

    /**
     * @param division_ID the customer division ID to set
     */
    public void setDivision_ID(int division_ID) {
        this.division_ID = division_ID;
    }

    /**
     * Converts hashmap to string for the country objects.
     *
     * Credit to Mark Kinkead combobox webinar.
     */
    @Override
    public String toString() {
        return ((customer_Name));
    }
}
