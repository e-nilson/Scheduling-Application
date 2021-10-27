package Utils;

import Model.Appointment;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


// handles all lists, users, appointments, etc. Processing takes place on client side
public abstract class ListProvider {

    //holds user references
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    public static void addUser(User user){
        allUsers.add(user);
    }

    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }


    //holds customer references
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void addCustomer(Customer customer){
        allCustomers.add(customer);
    }

    public static boolean deleteCustomer(Customer selectedCustomer) {
        if (allCustomers.contains(selectedCustomer)) {
            allCustomers.remove(selectedCustomer);
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean updateCustomer(Customer selectedCustomer) {
        if(allCustomers.contains(selectedCustomer)){
            allCustomers.remove(selectedCustomer);
            return true;
        } else {
            return false;
        }
    }

    //holds appointment references
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

}
