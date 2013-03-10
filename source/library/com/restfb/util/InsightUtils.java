/*
 * Copyright (c) 2010-2013 Mark Allen.
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

package com.restfb.util;

import static com.restfb.util.StringUtils.isBlank;
import static java.lang.String.format;
import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;

import com.restfb.FacebookClient;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;

/**
 * Utility methods to ease querying <a target="_blank"
 * href="http://developers.facebook.com/docs/reference/fql/insights/">Insight Metrics</a> over a set of dates.
 * 
 * @author Andrew Liles
 * @since 1.6.10
 */
public class InsightUtils {
  /**
   * Represents a time period for Facebook Insights queries.
   */
  public enum Period {
    DAY(60 * 60 * 24), WEEK(60 * 60 * 24 * 7), DAYS_28(60 * 60 * 24 * 28), MONTH(2592000), LIFETIME(0);

    private final int periodLength;

    private Period(int periodLength) {
      this.periodLength = periodLength;
    }

    public int getPeriodLength() {
      return periodLength;
    }
  }

  private static final TimeZone PST_TIMEZONE = TimeZone.getTimeZone("PST");
  private static final long SECONDS_IN_DAY = 60 * 60 * 24;

  /**
   * Queries Facebook via FQL for several Insights at different date points.
   * <p>
   * The output groups the result by metric and then by date, matching the input arguments. Map entries may be
   * {@code null} if Facebook does not return corresponding data, e.g. when you query a metric which is not available at
   * the chosen period. The inner {@code Map}'s {@code Object} value may be a strongly typed value for some metrics, but
   * in other cases Facebook returns a {@code JsonObject}.
   * <p>
   * Sample output, assuming 2 metrics were queried for 5 dates:
   * 
   * <pre>
   * {page_active_users = {
   * &nbsp;&nbsp;&nbsp; 2011-jan-01 = 7,
   * &nbsp;&nbsp;&nbsp; 2011-jan-02 = 26,
   * &nbsp;&nbsp;&nbsp; 2011-jan-03 = 15,
   * &nbsp;&nbsp;&nbsp; 2011-jan-04 = 10,
   * &nbsp;&nbsp;&nbsp; 2011-jan-05= 687},
   * page_tab_views_login_top_unique = {
   * &nbsp;&nbsp;&nbsp; 2011-jan-01 = {"photos":2,"app_4949752878":3,"wall":30},
   * &nbsp;&nbsp;&nbsp; 2011-jan-02 = {"app_4949752878":1,"photos":1,"app_2373072738":2,"wall":23},
   * &nbsp;&nbsp;&nbsp; 2011-jan-03 = {"app_4949752878":1,"wall":12},
   * &nbsp;&nbsp;&nbsp; 2011-jan-04 = {"photos":1,"wall":11},
   * &nbsp;&nbsp;&nbsp; 2011-jan-05 = {"app_494975287":2,"app_237307273":2,"photos":6,"wall":35,"info":6}}
   * </pre>
   * 
   * @param facebookClient
   *          The {@code FacebookClient} used to communicate with the Insights API.
   * @param pageObjectId
   *          The required object_id to query.
   * @param metrics
   *          A not null/empty set of metrics that will be queried for the given {@code period}.
   * @param period
   *          The required time period over which to query.
   * @param periodEndDates
   *          A non-null, non-empty {@code Set} in which each date should be normalized to be midnight in the PST
   *          timezone; see {@link #convertToMidnightInPacificTimeZone(Set)}.
   * @return A {@code Map} of {@code Maps}: the outer keys will be all the metrics requested that were available at the
   *         period/times requested. The inner keys will be the {@code Set} of periodEndDates.
   * @see #convertToMidnightInPacificTimeZone(Set)
   * @see #executeInsightQueriesByDate(FacebookClient, String, Set, Period, Set)
   */
  public static SortedMap<String, SortedMap<Date, Object>> executeInsightQueriesByMetricByDate(
      FacebookClient facebookClient, String pageObjectId, Set<String> metrics, Period period, Set<Date> periodEndDates) {
    SortedMap<String, SortedMap<Date, Object>> result = new TreeMap<String, SortedMap<Date, Object>>();
    SortedMap<Date, JsonArray> raw =
        executeInsightQueriesByDate(facebookClient, pageObjectId, metrics, period, periodEndDates);

    if (!raw.isEmpty()) {
      for (Date date : raw.keySet()) {
        JsonArray resultByMetric = raw.get(date);

        // [{"metric":"page_active_users","value":582},
        // {"metric":"page_tab_views_login_top_unique","value":{"wall":12,"app_4949752878":1}}]
        for (int resultIndex = 0; resultIndex < resultByMetric.length(); resultIndex++) {
          JsonObject metricResult = resultByMetric.getJsonObject(resultIndex);

          try {
            String metricName = metricResult.getString("metric");
            Object metricValue = metricResult.get("value");

            // store into output collection
            SortedMap<Date, Object> resultByDate = result.get(metricName);
            if (resultByDate == null) {
              resultByDate = new TreeMap<Date, Object>();
              result.put(metricName, resultByDate);
            }

            if (resultByDate.put(date, metricValue) != null)
              throw new IllegalStateException(format(
                "MultiQuery response has two results for metricName: %s and date: %s", metricName, date));
          } catch (JsonException e) {
            throw new FacebookJsonMappingException(format("Could not decode result for %s: %s", metricResult,
              e.getMessage()), e);
          }
        }
      }
    }

    return result;
  }

  /**
   * Queries Facebook via FQL for several Insights at different date points and returns "raw" results.
   * <p>
   * A variation on {@link #executeInsightQueriesByMetricByDate(FacebookClient, String, Set, Period, Set)} , this method
   * returns the raw output from the Facebook, keying the output by date alone. The {@code JsonArray} value will contain
   * the metrics that were requested and available at the date.
   * <p>
   * Sample output, assuming 2 metrics were queried for 5 dates:
   * 
   * <pre>
   * {2011-jan-01 = [
   * &nbsp;&nbsp;&nbsp; {"metric":"page_active_users","value":7},
   * &nbsp;&nbsp;&nbsp; {"metric":"page_tab_views_login_top_unique","value":{"photos":2,"app_4949752878":3,"wall":30}}], 
   * 2011-jan-02 = [
   * &nbsp;&nbsp;&nbsp; {"metric":"page_active_users","value":26},
   * &nbsp;&nbsp;&nbsp; {"metric":"page_tab_views_login_top_unique","value":{"app_4949752878":1,"photos":1,"app_2373072738":2,"wall":23}}], 
   * 2011-jan-03 = [
   * &nbsp;&nbsp;&nbsp; {"metric":"page_active_users","value":15},
   * &nbsp;&nbsp;&nbsp; {"metric":"page_tab_views_login_top_unique","value":{"app_4949752878":1,"wall":12}}], 
   * 2011-jan-04 = [
   * &nbsp;&nbsp;&nbsp; {"metric":"page_active_users","value":10},
   * &nbsp;&nbsp;&nbsp; {"metric":"page_tab_views_login_top_unique","value":{"photos":1,"wall":11}}]
   * 2011-jan-05 = [
   * &nbsp;&nbsp;&nbsp; {"metric":"page_active_users","value":687},
   * &nbsp;&nbsp;&nbsp; {"metric":"page_tab_views_login_top_unique","value":{"app_494975287":2,"app_237307273":2,"photos":6,"wall":35,"info":6}}]
   * }
   * </pre>
   * 
   * @param facebookClient
   *          The {@code FacebookClient} used to communicate with the Insights API.
   * @param pageObjectId
   *          The required object_id to query.
   * @param metrics
   *          A not null/empty set of metrics that will be queried for the given {@code period}.
   * @param period
   *          The required time period over which to query.
   * @param periodEndDates
   *          A non-null, non-empty {@code Set} in which each date should be normalized to be midnight in the PST
   *          timezone; see {@link #convertToMidnightInPacificTimeZone(Set)}.
   * @return Insights query results, grouped by date.
   * @see #convertToMidnightInPacificTimeZone(Set)
   * @see #executeInsightQueriesByMetricByDate(FacebookClient, String, Set, Period, Set)
   */
  public static SortedMap<Date, JsonArray> executeInsightQueriesByDate(FacebookClient facebookClient,
      String pageObjectId, Set<String> metrics, Period period, Set<Date> periodEndDates) {
    if (facebookClient == null)
      throw new IllegalArgumentException("The 'facebookClient' parameter is required.");

    if (isBlank(pageObjectId))
      throw new IllegalArgumentException(
        "The 'pageObjectId' parameter should be a non-empty string, probably a positive number.");

    if (isEmpty(metrics))
      throw new IllegalArgumentException("The 'metrics' set should be non-empty.");

    if (period == null)
      throw new IllegalArgumentException("The 'period' parameter is required.");

    if (isEmpty(periodEndDates))
      throw new IllegalArgumentException("The 'periodEndDates' set should be non-empty");

    // put dates into an array where we can easily access the index of each
    // date, as these ordinal positions will be used as query identifiers
    // in the MultiQuery
    List<Date> datesByQueryIndex = new ArrayList<Date>(periodEndDates);
    // sort the list in Date order so the implementation is tolerant of a
    // chaotic periodEndDates iteration order
    Collections.sort(datesByQueryIndex);

    String baseQuery = createBaseQuery(period, pageObjectId, metrics);

    Map<String, String> fqlByQueryIndex = buildQueries(baseQuery, datesByQueryIndex);

    SortedMap<Date, JsonArray> result = new TreeMap<Date, JsonArray>();

    if (!fqlByQueryIndex.isEmpty()) {
      // request the client sends all the queries in fqlByQueryIndex to Facebook
      // in one go and have the raw JsonObject be returned
      JsonObject response = facebookClient.executeFqlMultiquery(fqlByQueryIndex, JsonObject.class);

      // transform the response into a Map
      for (Iterator<?> it = response.keys(); it.hasNext();) {
        String key = (String) it.next();
        JsonArray innerResult = (JsonArray) response.get(key);

        try {
          // resolve the map key back into a date
          int queryIndex = Integer.parseInt(key);
          Date d = datesByQueryIndex.get(queryIndex);
          if (d == null)
            throw new IllegalStateException("MultiQuery response had an unexpected key value: " + key);

          result.put(d, innerResult);
        } catch (NumberFormatException nfe) {
          throw new IllegalStateException("MultiQuery response had an unexpected key value: " + key, nfe);
        }
      }
    }
    return result;
  }

  static Map<String, String> buildQueries(String baseQuery, List<Date> datesByQueryIndex) {
    Map<String, String> fqlByQueryIndex = new LinkedHashMap<String, String>();
    for (int queryIndex = 0; queryIndex < datesByQueryIndex.size(); queryIndex++) {
      Date d = datesByQueryIndex.get(queryIndex);
      String query = baseQuery + convertToUnixTimeOneDayLater(d);
      fqlByQueryIndex.put(String.valueOf(queryIndex), query);
    }
    return fqlByQueryIndex;
  }

  static String createBaseQuery(Period period, String pageObjectId, Set<String> metrics) {
    StringBuilder q = new StringBuilder();
    q.append("SELECT metric, value ");
    q.append("FROM insights ");
    q.append("WHERE object_id='");
    q.append(pageObjectId);
    q.append('\'');

    String metricInList = buildMetricInList(metrics);
    if (!isBlank(metricInList)) {
      q.append(" AND metric IN (");
      q.append(metricInList);
      q.append(")");
    }

    q.append(" AND period=");
    q.append(period.getPeriodLength());
    q.append(" AND end_time=");
    return q.toString();
  }

  static String buildMetricInList(Set<String> metrics) {
    StringBuilder in = new StringBuilder();
    if (!isEmpty(metrics)) {
      int metricCount = 0;
      for (String metric : metrics) {
        if (!isBlank(metric)) {
          if (metricCount > 0)
            in.append(',');

          in.append('\'');
          in.append(metric.trim());
          in.append('\'');
          metricCount++;
        }
      }
    }
    return in.toString();
  }

  /**
   * Slides this date back to midnight in the PST timezone fit for the Facebook Query Language.
   * <p>
   * More details are available at <a target="_blank" href="http://developers.facebook.com/docs/reference/fql/insights"
   * >http://developers.facebook.com/docs/reference/fql/insights</a>.
   * 
   * @param date
   *          The date to slide back.
   * @return A midnight-PST version of the provided {@code date}.
   * @see #convertToMidnightInPacificTimeZone(Set)
   */
  public static Date convertToMidnightInPacificTimeZone(Date date) {
    if (date == null)
      throw new IllegalArgumentException("The 'date' parameter is required.");

    Set<Date> convertedDates = convertToMidnightInPacificTimeZone(singleton(date));

    if (convertedDates.size() != 1)
      throw new IllegalStateException("Internal error, expected 1 date.");

    return convertedDates.iterator().next();
  }

  /**
   * Slides these dates back to midnight in the PST timezone fit for the Facebook Query Language.
   * <p>
   * More details are available at <a target="_blank" href="http://developers.facebook.com/docs/reference/fql/insights"
   * >http://developers.facebook.com/docs/reference/fql/insights</a>.
   * 
   * @param dates
   *          The dates to slide back.
   * @return Midnight-PST versions of the provided {@code dates}.
   * @see #convertToMidnightInPacificTimeZone(Date)
   */
  public static SortedSet<Date> convertToMidnightInPacificTimeZone(Set<Date> dates) {
    Calendar calendar = Calendar.getInstance(PST_TIMEZONE);
    SortedSet<Date> convertedDates = new TreeSet<Date>();
    for (Date d : dates) {
      calendar.setTime(d);
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      convertedDates.add(calendar.getTime());
    }
    return convertedDates;
  }

  /**
   * Converts into a "unix time", which means convert into the number of seconds (NOT milliseconds) from the Epoch fit
   * for the Facebook Query Language. Notice that if you want data for September 15th then you need to present to
   * Facebook the NEXT DAY, ie. the upper exclusive limit of your date range. So beyond all the sliding to midnight code
   * you see in {@link #convertToMidnightInPacificTimeZone(Date)}, we need to go further and slide this input date
   * forward one day.
   * 
   * In retrospect, this should have been implemented via the Facebook end_time_date() function.
   * 
   * @param date
   *          The date to convert.
   * @return Unix time representation of the given {@code date}.
   */
  static long convertToUnixTimeOneDayLater(Date date) {
    long time = date.getTime() / 1000L;
    // note we cannot use a Daylight sensitive Calendar here since that would
    // adjust the time incorrectly over the DST junction
    time += SECONDS_IN_DAY;
    return time;
  }

  /**
   * Slides this time back to midnight in the PST timezone and converts into a "unix time", which means convert into the
   * number of seconds (NOT milliseconds) from the Epoch fit for the Facebook Query Language. Notice that if you want
   * data for September 15th then you need to present to Facebook the NEXT DAY, ie. the upper exclusive limit of your
   * date range. So beyond all the sliding to midnight issue, we need to go further and slide this input date forward
   * one day.
   * 
   * @param date
   *          The date to convert.
   * @return Midnight-PST Unix time representation of the given {@code date}.
   * @see #convertToMidnightInPacificTimeZone(Date)
   */
  static long convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater(Date date) {
    return convertToUnixTimeOneDayLater(convertToMidnightInPacificTimeZone(date));
  }

  /**
   * Is the provided {@code collection} {@code null} or empty?
   * 
   * @param collection
   *          The collection to check.
   * @return {@code true} if {@code collection} is {@code null} or empty, {@code false} otherwise.
   */
  static <T extends Object> boolean isEmpty(Collection<T> collection) {
    return collection == null || collection.isEmpty();
  }
}