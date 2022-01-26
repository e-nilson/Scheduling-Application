package Model;

/**
 * Class for the Division model.
 *
 * @author Erik Nilson
 */
public class Division {
    private int division_ID;
    private String division;
    private int country_ID;

    //Constructor for Add Customer combobox
    public Division(Integer division_ID) {
        this.division_ID=division_ID;
    }

    /**
     * Division constructors.
     */
    public Division(int division_ID, String division, int country_ID) {
        this.division_ID = division_ID;
        this.division = division;
        this.country_ID = country_ID;
    }

    /**
     * @return the divisionId
     */
    public int getDivision_ID() {
        return division_ID;
    }

    /**
     * @param division_ID the divisionId to set
     */
    public void setDivision_ID(int division_ID) {
        this.division_ID = division_ID;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return the countryId
     */
    public int getCountry_ID() {
        return country_ID;
    }

    /**
     * @param country_ID the countryId to set
     */
    public void setCountry_ID(int country_ID) {
        this.country_ID = country_ID;
    }

    @Override
    public String toString() {
        return division;
    }
}
