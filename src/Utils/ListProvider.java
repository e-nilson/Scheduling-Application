package Utils;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Handles lists used throughout the application.
 *
 * @author Erik Nilson
 */
public abstract class ListProvider {

    /**
     * Holds all user references
     */
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }

    public static void addUser(User user){
        allUsers.add(user);
    }

    /**
     * Holds all customer references
     */
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void addCustomer(Customer customer){
        allCustomers.add(customer);
    }

    public static void updateCustomer(Customer updateCustomer) {
        allCustomers.add(updateCustomer);
    }

    public static boolean deleteCustomer(Customer selectedCustomer) {
        if (allCustomers.contains(selectedCustomer)) {
            allCustomers.remove(selectedCustomer);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Holds all appointment references
     */
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }

    public static void addAppointment(Appointment appointment) {
        allAppointments.add(appointment);
    }

    public static boolean deleteAppointment(Appointment selectedAppointment) {
        if (allAppointments.contains(selectedAppointment)) {
            allAppointments.remove(selectedAppointment);
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean updateAppointment(Appointment selectedAppointment) {
        if(allAppointments.contains(selectedAppointment)){
            allAppointments.remove(selectedAppointment);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Holds all country references
     */
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    public static ObservableList<Country> getAllCountries(){
        return allCountries;
    }

    public static void addCountry(Country country) { allCountries.add(country);
    }

    /**
     * Holds all division references
     */
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    public static ObservableList<Division> getAllDivisions(){
        return allDivisions;
    }


    public static void addDivision(Division division) {allDivisions.add(division);
    }

    /**
     * Holds all contact references
     */
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    public static ObservableList<Contact> getAllContacts(){
        return allContacts;
    }

    public static void addContact(Contact contact) {allContacts.add(contact);
    }
}
