package model;

public class Booking {
    private String firstname;
    private String lastname;
    private float totalprice;
    private boolean depositpaid;
    private Bookingdates bookingdatesObject;
    private String additionalneeds;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public float getTotalprice() {
        return totalprice;
    }

    public boolean getDepositpaid() {
        return depositpaid;
    }

    public Bookingdates getBookingdates() {
        return bookingdatesObject;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public Booking setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public Booking setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public Booking setTotalprice(float totalprice) {
        this.totalprice = totalprice;
        return this;
    }

    public Booking setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
        return this;
    }

    public Booking setBookingdates(Bookingdates bookingdatesObject) {
        this.bookingdatesObject = bookingdatesObject;
        return this;
    }

    public Booking setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
        return this;
    }
}