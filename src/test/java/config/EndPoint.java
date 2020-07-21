package config;

public interface EndPoint {
    String AUTH = "/auth";
    String BOOKING = "/booking";
    String SINGLE_BOOKING = "/booking/{bookingId}";
    String PING = "/ping";

}
