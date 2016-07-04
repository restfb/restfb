package com.restfb.types.webhook.messaging.airline;

import com.restfb.Facebook;
import lombok.Getter;
import lombok.Setter;

public class FlightAirportItem {
    @Getter
    @Setter
    @Facebook("airport_code")
    private String airportCode;

    @Getter
    @Setter
    @Facebook
    private String city;

    @Getter
    @Setter
    @Facebook
    private String terminal;

    @Getter
    @Setter
    @Facebook
    private String gate;
}
