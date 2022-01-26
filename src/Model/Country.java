package Model;

/**
 * Class for the Country model.
 *
 * @author Erik Nilson
 */
public class Country {
    private int country_ID;
    private String countryName;

    /**
     * Country constructors.
     */
    public Country(int country_ID, String countryName) {
        this.country_ID = country_ID;
        this.countryName = countryName;
    }

    // populate Single parameter country
    public Country(String country) {
        this.countryName=country;
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

    /**
     * @return the country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the country name to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    @Override
    public String toString() {
        return this.getCountryName();
    }
}

