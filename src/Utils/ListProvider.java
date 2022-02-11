package Utils;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

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
     * Finds appointments based on customer id
     */
    public static ObservableList<Appointment> getCustAppt(int customer_ID) {

        ObservableList<Appointment> custAppts = FXCollections.observableArrayList();
        AppointmentDB dBappointments = new AppointmentDB();

        for (Appointment appointments : ListProvider.getAllAppointments()){
            if (appointments.getCustomer_ID() == customer_ID){
                custAppts.add(appointments);
            }
        }
        return custAppts;
    }


    /**
     * Holds all appointment references
     */
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }

    public static void addAppointment(Appointment addAppointment) {
        allAppointments.add(addAppointment);
    }

    public static void updateAppointment(Appointment updatedAppointment) {
        allAppointments.add(updatedAppointment); }

    public static boolean deleteAppointment(Appointment selectedAppointment) {
        if (allAppointments.contains(selectedAppointment)) {
            allAppointments.remove(selectedAppointment);
            return true;
        }
        else {
            return false;
        }
    }
    // Finds appointments based on customer id
    public static ObservableList<Appointment> getApptsByCustomer(int customer_ID) {
        ObservableList<Appointment> custAppts = FXCollections.observableArrayList();
        AppointmentDB dBappointments = new AppointmentDB();

        for (Appointment appointments : ListProvider.getAllAppointments()){
            if (appointments.getCustomer_ID() == customer_ID){
                custAppts.add(appointments);
            }
        }
        return custAppts;
    }

    // Finds appointments based on user id
    public static ObservableList<Appointment> getApptsByUser(int user_ID) {
        ObservableList<Appointment> userAppts = FXCollections.observableArrayList();
        AppointmentDB appointmentDB = new AppointmentDB();

        for (Appointment appointment : ListProvider.getAllAppointments()){
            if (appointment.getUser_ID() == user_ID){
                userAppts.add(appointment);
            }
        }
        return userAppts;
    }

    /**
     * Finds appointments based on contact id
     */
    public static ObservableList<Appointment> getApptsByContact(int contactID) {
        ObservableList<Appointment> contactApptResult = FXCollections.observableArrayList();
        AppointmentDB dBappointments = new AppointmentDB();

        for (Appointment appointment : ListProvider.getAllAppointments()){
            if (appointment.getContact_ID() == contactID){
                contactApptResult.add(appointment);
            }
        }
        return contactApptResult;
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
