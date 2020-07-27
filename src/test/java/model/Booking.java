package model;

public class Booking {

    private String firstname = "";
    private String lastname = "";
    private String totalprice = "";
    private String depositpaid = "";
    private String bookingdates = "";
    private String checkin = "";
    private String checkout = "";
    private String additionalneeds = "";

    public Booking setFirstname(String firstname) {
        this.firstname = "    \"firstname\": \"" + firstname + "\",\n";
        return this;
    }

    public Booking setLastname(String lastname) {
        this.lastname = "    \"lastname\": \"" + lastname + "\",\n";
        return this;
    }

    public Booking setTotalprice(int totalprice) {
        this.totalprice = "    \"totalprice\": " + totalprice + ",\n";
        return this;
    }

    public Booking setDepositpaid(boolean depositpaid) {
        this.depositpaid = "    \"depositpaid\": " + depositpaid + ",\n";
        return this;
    }

    public Booking setBookingdates(String checkin, String checkout) {
        this.checkin = "        \"checkin\": \"" + checkin + "\",\n";
        this.checkout = "        \"checkout\": \"" + checkout + "\"\n";
        bookingdates = "    \"bookingdates\": {\n" +
                this.checkin + this.checkout +
                "    },\n";
        return this;
    }

    public Booking setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = "    \"additionalneeds\": \"" + additionalneeds + "\",\n";
        return this;
    }

    public String getJsonBooking() {
        String result = "{\n" +
                firstname +
                lastname +
                totalprice +
                depositpaid +
                bookingdates +
                additionalneeds +
                "}";
        return result.replace(",\n}", "\n}");
    }
}