package com.restfb.types.webhook.messaging.airline;

import com.restfb.Facebook;
import lombok.Getter;
import lombok.Setter;

public class AirlineFieldItem {
    @Getter
    @Setter
    @Facebook
    private String label;

    @Getter
    @Setter
    @Facebook
    private String value;
}
