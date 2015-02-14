/*
 * Copyright (c) 2010-2014 Mark Allen.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.restfb.types;

import com.restfb.Facebook;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="https://github.com/maczam">Maczam</a>
 */
public class Summary implements Serializable {
    @Getter
    @Setter
    @Facebook("total_count")
    private Long totalCount;
    @Getter
    @Setter
    @Facebook("date_start")
    private String dateStart;
    @Getter
    @Setter
    @Facebook("date_stop")
    private String dateStop;
    @Getter
    @Setter
    @Facebook("time_start")
    private Long timeStart;
    @Getter
    @Setter
    @Facebook("time_stop")
    private Long timeStop;
    @Getter
    @Setter
    @Facebook("clicks")
    private Integer clicks;
    @Getter
    @Setter
    @Facebook("unique_clicks")
    private Integer uniqueClicks;
    @Getter
    @Setter
    @Facebook("cpc")
    private Double cpc;
    @Getter
    @Setter
    @Facebook("cpm")
    private Double cpm;
    @Getter
    @Setter
    @Facebook("cpp")
    private Double cpp;
    @Getter
    @Setter
    @Facebook("ctr")
    private Double ctr;
    @Getter
    @Setter
    @Facebook("unique_ctr")
    private Double uniqueCtr;
    @Getter
    @Setter
    @Facebook("spend")
    private Double spend;
    @Getter
    @Setter
    @Facebook("reach")
    private Integer reach;
    @Getter
    @Setter
    @Facebook("impressions")
    private Integer impressions;
    @Getter
    @Setter
    @Facebook("unique_impressions")
    private Integer uniqueImpressions;
    @Getter
    @Setter
    @Facebook("total_actions")
    private Integer totalActions;
    @Getter
    @Setter
    @Facebook("total_unique_actions")
    private Integer totalUniqueActions;
    @Getter
    @Setter
    @Facebook
    private List<ActionType> actions;
}
