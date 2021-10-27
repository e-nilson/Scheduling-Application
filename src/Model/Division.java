package Model;

public class Division {
    private int divisionId;
    private String division;
    private int countryId;

    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * @return the divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
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
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

}
