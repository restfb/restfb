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

import static com.restfb.util.InsightUtils.buildQueries;
import static com.restfb.util.InsightUtils.convertToMidnightInPacificTimeZone;
import static com.restfb.util.InsightUtils.convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater;
import static com.restfb.util.InsightUtils.convertToUnixTimeOneDayLater;
import static com.restfb.util.InsightUtils.createBaseQuery;
import static com.restfb.util.InsightUtils.executeInsightQueriesByDate;
import static com.restfb.util.InsightUtils.executeInsightQueriesByMetricByDate;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.restfb.ClasspathWebRequestor;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.WebRequestor;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.util.InsightUtils.Period;

/**
 * Unit tests that exercise {@link com.restfb.util.InsightUtils}.
 * 
 * @author Andrew Liles
 */
public class InsightUtilsTest {
  private static final String JSON_RESOURCES_PREFIX = "/json/insight/";
  private static Locale DEFAULT_LOCALE;
  private static final TimeZone UTC_TIMEZONE = TimeZone.getTimeZone("Etc/UTC");
  private static final TimeZone PST_TIMEZONE = TimeZone.getTimeZone("PST");
  private static SimpleDateFormat sdfUTC;
  private static SimpleDateFormat sdfPST;
  private static final String TEST_PAGE_OBJECT = "31698190356";
  private static Date d20101204_0000pst;
  private static Date d20101205_0000pst;
  private FacebookClient defaultNoAccessTokenClient;

  @BeforeClass
  public static void beforeClass() throws ParseException {
    for (Locale locale : Locale.getAvailableLocales()) {
      if (locale.toString().equals("en_US")) {
        DEFAULT_LOCALE = locale;
        break;
      }
    }
    Assert.assertNotNull(DEFAULT_LOCALE);
    sdfUTC = newSimpleDateFormat("yyyyMMdd_HHmm", DEFAULT_LOCALE, UTC_TIMEZONE);
    sdfPST = newSimpleDateFormat("yyyyMMdd_HHmm", DEFAULT_LOCALE, PST_TIMEZONE);
    d20101204_0000pst = sdfPST.parse("20101204_0000");
    d20101205_0000pst = sdfPST.parse("20101205_0000");
  }

  @Before
  public void before() {
    defaultNoAccessTokenClient = new DefaultFacebookClient();
  }

  @Test
  public void convertToMidnightInPacificTimeZone1() throws ParseException {
    Date d20030630_0221utc = sdfUTC.parse("20030630_0221");
    Date d20030629_0000pst = sdfPST.parse("20030629_0000");

    Date actual = convertToMidnightInPacificTimeZone(d20030630_0221utc);
    assertEquals(d20030629_0000pst, actual);
  }

  @Test
  public void convertToMidnightInPacificTimeZone2() throws ParseException {
    Date d20030630_1503utc = sdfUTC.parse("20030630_1503");
    Date d20030630_0000pst = sdfPST.parse("20030630_0000");

    Date actual = convertToMidnightInPacificTimeZone(d20030630_1503utc);
    assertEquals(d20030630_0000pst, actual);
  }

  @Test
  public void convertToMidnightInPacificTimeZoneSet1() throws ParseException {
    Date d20030630_0221utc = sdfUTC.parse("20030630_0221");
    Date d20030629_0000pst = sdfPST.parse("20030629_0000");
    Date d20030630_1503utc = sdfUTC.parse("20030630_1503");
    Date d20030630_0000pst = sdfPST.parse("20030630_0000");
    Set<Date> inputs = new HashSet<Date>();
    inputs.add(d20030630_0221utc);
    inputs.add(d20030630_1503utc);

    SortedSet<Date> actuals = convertToMidnightInPacificTimeZone(inputs);
    assertEquals(2, actuals.size());
    Iterator<Date> it = actuals.iterator();
    assertEquals(d20030629_0000pst, it.next());
    assertEquals(d20030630_0000pst, it.next());
  }

  @Test
  public void convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater1() throws ParseException {
    // From http://developers.facebook.com/docs/reference/fql/insights/
    // Example: To obtain data for the 24-hour period starting on
    // September 15th at 00:00 (i.e. 12:00 midnight) and ending on
    // September 16th at 00:00 (i.e. 12:00 midnight),
    // specify 1284620400 as the end_time and 86400 as the period.

    Date d20100916_1800utc = sdfUTC.parse("20100915_1200");

    long actual = convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater(d20100916_1800utc);
    assertEquals(1284620400L, actual);
  }

  @Test
  public void convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater2() throws ParseException {
    // in this test we are still in the previous PST day - the difference is 7
    // hours from UTC to PST

    Date d20100917_0659utc = sdfUTC.parse("20100916_0659");

    long actual = convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater(d20100917_0659utc);
    assertEquals(1284620400L, actual);

    Date d20100917_0700utc = sdfUTC.parse("20100916_0700");

    actual = convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater(d20100917_0700utc);
    assertEquals(1284620400L + (60 * 60 * 24), actual);
  }

  @Test
  public void convertToUnixTimeOneDayLater1() throws ParseException {
    Object[][] testset = {
        // 3 summertime:
        { "2010-09-15", 1284620400L }, { "2010-09-16", 1284706800L }, { "2010-09-17", 1284793200L },
        // 3 wintertime:
        { "2010-12-15", 1292486400L }, { "2010-12-16", 1292572800L }, { "2010-12-17", 1292659200L },
        // 3 across the DST switch in 2011 in the US was Sunday 6 Nov
        { "2011-11-05", 1320562800L }, { "2011-11-06", 1320649200L }, { "2011-11-07", 1320739200L } };
    SimpleDateFormat sdfPST2 = newSimpleDateFormat("yyyy-MM-dd", DEFAULT_LOCALE, PST_TIMEZONE);
    for (Object[] test : testset) {
      Date d = sdfPST2.parse((String) test[0]);
      long expectedUnixDate = (Long) test[1];
      assertEquals("On date " + test[0], expectedUnixDate, convertToUnixTimeOneDayLater(d));
    }
  }

  @Test
  public void createBaseQuery0metrics() {
    Set<String> metrics = Collections.emptySet();
    assertEquals("SELECT metric, value FROM insights WHERE object_id='31698190356' AND "
        + "period=604800 AND end_time=", createBaseQuery(Period.WEEK, TEST_PAGE_OBJECT, metrics));

    // what about all empties/nulls in the list?
    metrics = new LinkedHashSet<String>();
    metrics.add(null);
    metrics.add("");
    metrics.add("");
    assertEquals("SELECT metric, value FROM insights WHERE object_id='31698190356' AND "
        + "period=604800 AND end_time=", createBaseQuery(Period.WEEK, TEST_PAGE_OBJECT, metrics));
  }

  @Test
  public void createBaseQuery1metric() {
    Set<String> metrics = Collections.singleton("page_active_users");
    assertEquals("SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
        + "('page_active_users') AND period=604800 AND end_time=",
      createBaseQuery(Period.WEEK, TEST_PAGE_OBJECT, metrics));
  }

  @Test
  public void createBaseQuery3metrics() {
    Set<String> metrics = new LinkedHashSet<String>();
    metrics.add("page_comment_removes");
    metrics.add("page_active_users");
    metrics.add("page_like_adds_source_unique");
    assertEquals("SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
        + "('page_comment_removes','page_active_users','page_like_adds_source_unique') AND period=86400 AND end_time=",
      createBaseQuery(Period.DAY, TEST_PAGE_OBJECT, metrics));
  }

  @Test
  public void createBaseQuery4metrics() {
    // are null/empty metrics removed from the list?
    Set<String> metrics = new LinkedHashSet<String>();
    metrics.add("");
    metrics.add("page_comment_removes");
    metrics.add("");
    metrics.add("page_like_adds_source_unique");
    metrics.add(null);
    assertEquals("SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
        + "('page_comment_removes','page_like_adds_source_unique') AND period=86400 AND end_time=",
      createBaseQuery(Period.DAY, TEST_PAGE_OBJECT, metrics));
  }

  @Test
  public void buildQueries1() throws ParseException {
    long t20101205_0000 = 1291536000L;
    assertEquals(t20101205_0000, convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater(d20101204_0000pst));
    assertEquals(t20101205_0000, d20101205_0000pst.getTime() / 1000L);

    List<Date> datesByQueryIndex = new ArrayList<Date>();
    datesByQueryIndex.add(d20101204_0000pst);

    String baseQuery =
        "SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
            + "('page_active_users') AND period=604800 AND end_time=";

    Map<String, String> fqlByQueryIndex = buildQueries(baseQuery, datesByQueryIndex);
    assertEquals(1, fqlByQueryIndex.size());

    String fql = fqlByQueryIndex.values().iterator().next();
    Assert
      .assertEquals(
        "SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN ('page_active_users') AND period=604800 AND end_time="
            + t20101205_0000, fql);
  }

  @Test
  public void prepareQueries30() throws Exception {
    // produce a set of days in UTC timezone from 1st Nov 9am to 30th Nov 9am
    // inclusive
    Date d20101101_0900utc = sdfUTC.parse("20101101_0900");
    Calendar c = new GregorianCalendar();
    c.setTimeZone(UTC_TIMEZONE);
    c.setTime(d20101101_0900utc);
    Set<Date> utcDates = new TreeSet<Date>();
    for (int dayNum = 1; dayNum <= 30; dayNum++) {
      utcDates.add(c.getTime());
      c.add(Calendar.DAY_OF_MONTH, 1);
    }
    assertEquals(30, utcDates.size());

    // convert into PST and convert into a list
    List<Date> datesByQueryIndex = new ArrayList<Date>(convertToMidnightInPacificTimeZone(utcDates));
    assertEquals(30, datesByQueryIndex.size());

    // Mon Nov 01 00:00:00 2010 PST
    long day0 = sdfPST.parse("20101101_0000").getTime() / 1000L + 60 * 60 * 24;
    // Sat Nov 06 00:00:00 2010 PST
    long day5 = sdfPST.parse("20101106_0000").getTime() / 1000L + 60 * 60 * 24;

    // Sun Nov 07 00:00:00 2010 PST
    // //////////// NOTE 7 Nov 2010 was when Summer time turned into Winter time
    long day6 = sdfPST.parse("20101107_0000").getTime() / 1000L + 60 * 60 * 24;

    // Mon Nov 08 00:00:00 2010 PST
    long day7 = sdfPST.parse("20101108_0000").getTime() / 1000L + 60 * 60 * 24;
    // Tue Nov 30 00:00:00 2010 PST
    long day29 = sdfPST.parse("20101130_0000").getTime() / 1000L + 60 * 60 * 24;

    String baseQuery =
        "SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
            + "('page_active_users','page_audio_plays') AND period=86400 AND end_time=";

    Map<String, String> fqlByQueryIndex = buildQueries(baseQuery, datesByQueryIndex);
    assertEquals(30, fqlByQueryIndex.size());

    assertEquals("SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
        + "('page_active_users','page_audio_plays') AND period=86400 AND end_time=" + day0, fqlByQueryIndex.get("0"));
    assertEquals("SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
        + "('page_active_users','page_audio_plays') AND period=86400 AND end_time=" + day5, fqlByQueryIndex.get("5"));
    assertEquals("SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
        + "('page_active_users','page_audio_plays') AND period=86400 AND end_time=" + day6, fqlByQueryIndex.get("6"));
    assertEquals("SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
        + "('page_active_users','page_audio_plays') AND period=86400 AND end_time=" + day7, fqlByQueryIndex.get("7"));
    assertEquals("SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
        + "('page_active_users','page_audio_plays') AND period=86400 AND end_time=" + day29, fqlByQueryIndex.get("29"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void executeInsightQueriesByDate_badArgs1() {
    executeInsightQueriesByDate(null, TEST_PAGE_OBJECT, Collections.singleton("page_active_users"), Period.DAY,
      Collections.singleton(d20101205_0000pst));
  }

  @Test(expected = IllegalArgumentException.class)
  public void executeInsightQueriesByDate_badArgs2() {
    executeInsightQueriesByDate(defaultNoAccessTokenClient, "", Collections.singleton("page_active_users"), Period.DAY,
      Collections.singleton(d20101205_0000pst));
  }

  @Test(expected = IllegalArgumentException.class)
  public void executeInsightQueriesByDate_badArgs3() {
    executeInsightQueriesByDate(defaultNoAccessTokenClient, TEST_PAGE_OBJECT,
      Collections.singleton("page_active_users"), null, Collections.singleton(d20101205_0000pst));
  }

  @Test(expected = IllegalArgumentException.class)
  public void executeInsightQueriesByDate_badArgs4() {
    executeInsightQueriesByDate(defaultNoAccessTokenClient, TEST_PAGE_OBJECT,
      Collections.singleton("page_active_users"), Period.DAY, new HashSet<Date>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void executeInsightQueriesByDate_badArgs5() {
    executeInsightQueriesByDate(defaultNoAccessTokenClient, TEST_PAGE_OBJECT, new HashSet<String>(), Period.DAY,
      Collections.singleton(d20101205_0000pst));
  }

  @Test
  public void executeInsightQueriesByDate1() throws IOException {
    // note that the query that is passed to the FacebookClient WebRequestor is
    // ignored,
    // so arguments of executeInsightQueriesByDate:
    // (String pageObjectId, Set<String> metrics, Period period)
    // are effectively ignored. In this test we are validating the
    // WebRequestor's json
    // is properly processed
    SortedMap<Date, JsonArray> results =
        executeInsightQueriesByDate(createFixedResponseFacebookClient("multiResponse_2metrics_1date.json"),
          TEST_PAGE_OBJECT, toStringSet("page_fans", "page_fans_gender"), Period.DAY,
          Collections.singleton(d20101205_0000pst));
    Assert.assertNotNull(results);
    assertEquals(1, results.size());
    JsonArray ja = results.get(d20101205_0000pst);
    Assert.assertNotNull(ja);
    // not ideal that this test requires on a stable JsonArray.toString()
    assertEquals(
      "[{\"metric\":\"page_fans\",\"value\":3777},{\"metric\":\"page_fans_gender\",\"value\":{\"U\":58,\"F\":1656,\"M\":2014}}]",
      ja.toString());
  }

  @Test
  public void executeInsightQueriesByDate2() throws IOException, ParseException {
    // note that the query that is passed to the FacebookClient WebRequestor is
    // ignored,
    // so arguments of executeInsightQueriesByDate:
    // (String pageObjectId, Set<String> metrics, Period period)
    // are effectively ignored. In this test we are validating the
    // WebRequestor's json
    // is properly processed

    Date d20030629_0000pst = sdfPST.parse("20030629_0000");
    Date d20030630_0000pst = sdfPST.parse("20030630_0000");
    Date d20030701_0000pst = sdfPST.parse("20030701_0000");
    Date d20030702_0000pst = sdfPST.parse("20030702_0000");
    // intentionally using (chaotic) HashSet to ensure implementation is
    // tolerant of that collection
    Set<Date> periodEndDates = new HashSet<Date>();
    periodEndDates.add(d20030629_0000pst);
    periodEndDates.add(d20030630_0000pst);
    periodEndDates.add(d20030701_0000pst);
    periodEndDates.add(d20030702_0000pst);

    SortedMap<Date, JsonArray> results =
        executeInsightQueriesByDate(createFixedResponseFacebookClient("multiResponse_2metrics_4dates.json"),
          TEST_PAGE_OBJECT, toStringSet("page_active_users", "page_tab_views_login_top_unique"), Period.DAY,
          periodEndDates);
    Assert.assertNotNull(results);
    // System.out.println(results);
    assertEquals(4, results.size());
    // not ideal that this test requires on a stable JsonArray.toString()
    assertEquals(
      "[{\"metric\":\"page_active_users\",\"value\":761},{\"metric\":\"page_tab_views_login_top_unique\",\"value\":{\"photos\":2,\"app_4949752878\":3,\"wall\":30}}]",
      results.get(d20030629_0000pst).toString());
    assertEquals(
      "[{\"metric\":\"page_active_users\",\"value\":705},{\"metric\":\"page_tab_views_login_top_unique\",\"value\":{\"app_4949752878\":1,\"photos\":1,\"app_2373072738\":2,\"wall\":23}}]",
      results.get(d20030630_0000pst).toString());
    assertEquals(
      "[{\"metric\":\"page_active_users\",\"value\":582},{\"metric\":\"page_tab_views_login_top_unique\",\"value\":{\"app_4949752878\":1,\"wall\":12}}]",
      results.get(d20030701_0000pst).toString());
    assertEquals(
      "[{\"metric\":\"page_active_users\",\"value\":125},{\"metric\":\"page_tab_views_login_top_unique\",\"value\":{\"photos\":1,\"wall\":11}}]",
      results.get(d20030702_0000pst).toString());
  }

  @Test
  public void executeInsightQueriesByMetricByDate1() throws IOException {
    // note that the query that is passed to the FacebookClient WebRequestor is
    // ignored,
    // so arguments of executeInsightQueriesByDate:
    // (String pageObjectId, Set<String> metrics, Period period)
    // are effectively ignored. In this test we are validating the
    // WebRequestor's json
    // is properly processed
    SortedMap<String, SortedMap<Date, Object>> results =
        executeInsightQueriesByMetricByDate(createFixedResponseFacebookClient("multiResponse_2metrics_1date.json"),
          TEST_PAGE_OBJECT, toStringSet("page_fans", "page_fans_gender"), Period.DAY,
          Collections.singleton(d20101205_0000pst));
    Assert.assertNotNull(results);
    assertEquals(2, results.size());

    SortedMap<Date, Object> metricResult = results.get("page_fans");
    assertEquals(1, metricResult.size());
    assertEquals(3777, metricResult.get(d20101205_0000pst));

    metricResult = results.get("page_fans_gender");
    assertEquals(1, metricResult.size());
    Object metricValue = metricResult.get(d20101205_0000pst);
    Assert.assertTrue(metricValue instanceof JsonObject);
    JsonObject o = (JsonObject) metricValue;
    assertEquals(58, o.getInt("U"));
    assertEquals(1656, o.getInt("F"));
    assertEquals(2014, o.getInt("M"));
  }

  @Test
  public void executeInsightQueriesByMetricByDate2() throws IOException, ParseException {
    // note that the query that is passed to the FacebookClient WebRequestor is
    // ignored,
    // so arguments of executeInsightQueriesByDate:
    // (String pageObjectId, Set<String> metrics, Period period)
    // are effectively ignored. In this test we are validating the
    // WebRequestor's json
    // is properly processed
    Date d20030629_0000pst = sdfPST.parse("20030629_0000");
    Date d20030630_0000pst = sdfPST.parse("20030630_0000");
    Date d20030701_0000pst = sdfPST.parse("20030701_0000");
    Date d20030702_0000pst = sdfPST.parse("20030702_0000");
    // intentionally using (chaotic) HashSet to ensure implementation is
    // tolerant of that collection
    Set<Date> periodEndDates = new HashSet<Date>();
    periodEndDates.add(d20030629_0000pst);
    periodEndDates.add(d20030630_0000pst);
    periodEndDates.add(d20030701_0000pst);
    periodEndDates.add(d20030702_0000pst);

    SortedMap<String, SortedMap<Date, Object>> results =
        executeInsightQueriesByMetricByDate(createFixedResponseFacebookClient("multiResponse_2metrics_4dates.json"),
          TEST_PAGE_OBJECT, toStringSet("page_active_users", "page_tab_views_login_top_unique"), Period.DAY,
          periodEndDates);
    Assert.assertNotNull(results);
    assertEquals(2, results.size());

    SortedMap<Date, Object> metricResult = results.get("page_active_users");
    assertEquals(4, metricResult.size());
    // {"name":0,"fql_result_set":[{"metric":"page_active_users","value":761},{"metric":"page_tab_views_login_top_unique","value":{"wall":30,"app_4949752878":3,"photos":2}}]},
    // here we validate the date map is sorted, so the results will come out in
    // a predictable order
    Iterator<Object> itValues = metricResult.values().iterator();
    assertEquals(761, itValues.next());
    assertEquals(705, itValues.next());
    assertEquals(582, itValues.next());
    assertEquals(125, itValues.next());

    metricResult = results.get("page_tab_views_login_top_unique");
    assertEquals(4, metricResult.size());
    JsonObject o = (JsonObject) metricResult.get(d20030629_0000pst);
    assertEquals(3, o.length());
    assertEquals(2, o.getInt("photos"));
    assertEquals(3, o.getInt("app_4949752878"));
    assertEquals(30, o.getInt("wall"));

    o = (JsonObject) metricResult.get(d20030630_0000pst);
    assertEquals(4, o.length());
    assertEquals(1, o.getInt("photos"));
    assertEquals(1, o.getInt("app_4949752878"));
    assertEquals(2, o.getInt("app_2373072738"));
    assertEquals(23, o.getInt("wall"));

    o = (JsonObject) metricResult.get(d20030701_0000pst);
    assertEquals(2, o.length());
    assertEquals(1, o.getInt("app_4949752878"));
    assertEquals(12, o.getInt("wall"));

    o = (JsonObject) metricResult.get(d20030702_0000pst);
    assertEquals(2, o.length());
    assertEquals(1, o.getInt("photos"));
    assertEquals(11, o.getInt("wall"));
  }

  @Test
  public void periodLengths() {
    assertEquals(86400, Period.DAY.getPeriodLength());
    assertEquals(604800, Period.WEEK.getPeriodLength());
    assertEquals(2419200, Period.DAYS_28.getPeriodLength());
    assertEquals(2592000, Period.MONTH.getPeriodLength());
    assertEquals(0, Period.LIFETIME.getPeriodLength());
  }

  /**
   * As there is no easy constructor for making a SimpleDateFormat specifying both a Locale and Timezone a utility is
   * provided here
   * 
   * @param pattern
   * @param locale
   * @param timezone
   * @return
   */
  public static SimpleDateFormat newSimpleDateFormat(String pattern, Locale locale, TimeZone timezone) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
    sdf.setTimeZone(timezone);
    return sdf;
  }

  private static FacebookClient createFixedResponseFacebookClient(String pathToJson) throws IOException {
    WebRequestor wr = new ClasspathWebRequestor(JSON_RESOURCES_PREFIX + pathToJson);
    String jsonBody = wr.executeGet(null).getBody();
    Assert.assertTrue("path to json not found:" + JSON_RESOURCES_PREFIX + pathToJson,
      (jsonBody != null) && (jsonBody.length() > 0));
    return new DefaultFacebookClient(null, wr, new DefaultJsonMapper());
  }

  private static Set<String> toStringSet(String... metrics) {
    return new LinkedHashSet<String>(Arrays.asList(metrics));
  }
}