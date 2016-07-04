package com.restfb.types.webhook.messaging.airline;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightScheduleItem {
    @Facebook("boarding_time")
    transient private String rawBoardingTime;

    /**
     * Timestamp for boarding time.
     *
     * @return Timestamp for boarding time.
     */
    @Getter
    @Setter
    private Date boardingTime;

    @Facebook("departure_time")
    transient private String rawDepartureTime;

    /**
     * Timestamp for departure time.
     *
     * @return Timestamp for departure time.
     */
    @Getter
    @Setter
    private Date departureTime;

    @Facebook("arrival_time")
    transient private String rawArrivalTime;

    /**
     * Timestamp for arrival time.
     *
     * @return Timestamp for arrival time.
     */
    @Getter
    @Setter
    private Date arrivalTime;

    @JsonMapper.JsonMappingCompleted
    void convertTimes() throws ParseException {
        if (rawBoardingTime != null) {
            boardingTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(rawBoardingTime);
        }
        if (rawDepartureTime != null) {
            departureTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(rawDepartureTime);
        }
        if (rawArrivalTime != null) {
            arrivalTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(rawArrivalTime);
        }
    }
}
