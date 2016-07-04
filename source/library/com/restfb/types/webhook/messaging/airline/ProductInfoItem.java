package com.restfb.types.webhook.messaging.airline;

import com.restfb.Facebook;
import lombok.Getter;
import lombok.Setter;

public class ProductInfoItem {
    @Getter
    @Setter
    @Facebook
    private String title;

    @Getter
    @Setter
    @Facebook
    private String value;
}
