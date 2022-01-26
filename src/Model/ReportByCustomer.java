package Model;

/**
 * Class for the report by customer model.
 *
 * @author Erik Nilson
 */
public class ReportByCustomer {

    private String month;
    private String type;
    private int total;

    /**
     * Report by customer constructors.
     */
    public ReportByCustomer(String month, String type, int total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /**
     * Getter for the month.
     *
     * @return the appointmentId.
     */
    public String getMonth() {
        return month;
    }

    /**
     * Getter for the type.
     *
     * @return the appointmentId.
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for the total.
     *
     * @return the appointmentId.
     */
    public int getTotal() {
        return total;
    }
}
