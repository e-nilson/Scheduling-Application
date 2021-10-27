package Model;

import Utils.ListProvider;

import java.time.LocalDateTime;

public class Customer extends ListProvider {
    /*
    private int customerId; //Auto-gen
    private String customerName;
    private String address;
    //private String country;
    //private String state;
    private String postalCode;
    private String phone;
    private int divisionId; //Auto-gen

    public Customer(int customerId, String customerName, String address, String postalCode, String phone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        //this.country = country;
        //this.state = state;
        this.postalCode = postalCode;
        this.phone = phone;
    }

     */

    private int customerId; //Auto-gen
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;

    public Customer(int customerId, String customerName, String address, String postalCode, String phone/*, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionId*/) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /*
    public void setCountry(String country) {
        this.address = country;
    }

    public String getCountry() {
        return country;
    }

    public void setState(String state) {
        this.address = state;
    }

    public String getState() {
        return state;
    }

     */

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }


    @Override
    /**Convert hashmap to String for Country objects in combobox (credit to Mr. Kinkead Webinar on Combo Boxes)*/
    public String toString() {

        return ((customerName));
    }

}
