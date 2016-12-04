/**
 * Copyright (c) 2010-2017 Mark Allen, Norbert Bartels.
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

import static com.restfb.util.InsightUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restfb.*;
import com.restfb.json.Json;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.testutils.AssertJson;
import com.restfb.util.InsightUtils.*;

import org.assertj.core.api.Condition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    assertThat(DEFAULT_LOCALE).isNotNull();
    sdfUTC = newSimpleDateFormat("yyyyMMdd_HHmm", DEFAULT_LOCALE, UTC_TIMEZONE);
    sdfPST = newSimpleDateFormat("yyyyMMdd_HHmm", DEFAULT_LOCALE, PST_TIMEZONE);
    d20101204_0000pst = sdfPST.parse("20101204_0000");
    d20101205_0000pst = sdfPST.parse("20101205_0000");
  }

  @Before
  public void before() {
    defaultNoAccessTokenClient = new DefaultFacebookClient(Version.LATEST);
  }

  @Test
  public void convertToMidnightInPacificTimeZone1() throws ParseException {
    Date d20030630_0221utc = sdfUTC.parse("20030630_0221");
    Date d20030629_0000pst = sdfPST.parse("20030629_0000");

    Date actual = convertToMidnightInPacificTimeZone(d20030630_0221utc);
    assertThat(actual).isEqualTo(d20030629_0000pst);
  }

  @Test
  public void convertToMidnightInPacificTimeZone2() throws ParseException {
    Date d20030630_1503utc = sdfUTC.parse("20030630_1503");
    Date d20030630_0000pst = sdfPST.parse("20030630_0000");

    Date actual = convertToMidnightInPacificTimeZone(d20030630_1503utc);
    assertThat(actual).isEqualTo(d20030630_0000pst);
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
    assertThat(actuals).hasSize(2);
    Iterator<Date> it = actuals.iterator();
    assertThat(it.next()).isEqualTo(d20030629_0000pst);
    assertThat(it.next()).isEqualTo(d20030630_0000pst);
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
    assertThat(actual).isEqualTo(1284620400);
  }

  @Test
  public void convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater2() throws ParseException {
    // in this test we are still in the previous PST day - the difference is 7
    // hours from UTC to PST

    Date d20100917_0659utc = sdfUTC.parse("20100916_0659");

    long actual = convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater(d20100917_0659utc);
    assertThat(actual).isEqualTo(1284620400);

    Date d20100917_0700utc = sdfUTC.parse("20100916_0700");

    actual = convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater(d20100917_0700utc);
    assertThat(actual).isEqualTo(1284620400 + (60 * 60 * 24));
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
      assertThat(convertToUnixTimeOneDayLater(d)).isEqualTo(expectedUnixDate);
    }
  }

  @Test
  public void createBaseQuery0metrics() {
    Set<String> metrics = Collections.emptySet();
    assertThat(createBaseQuery(Period.WEEK, TEST_PAGE_OBJECT, metrics))
      .isEqualTo("SELECT metric, value FROM insights WHERE object_id='31698190356' AND period=604800 AND end_time=");

    // what about all empties/nulls in the list?
    metrics = new LinkedHashSet<String>();
    metrics.add(null);
    metrics.add("");
    metrics.add("");
    assertThat(createBaseQuery(Period.WEEK, TEST_PAGE_OBJECT, metrics))
      .isEqualTo("SELECT metric, value FROM insights WHERE object_id='31698190356' AND period=604800 AND end_time=");
  }

  @Test
  public void createBaseQuery1metric() {
    Set<String> metrics = Collections.singleton("page_active_users");
    assertThat(createBaseQuery(Period.WEEK, TEST_PAGE_OBJECT, metrics)).isEqualTo(
      "SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN ('page_active_users') AND period=604800 AND end_time=");
  }

  @Test
  public void createBaseQuery3metrics() {
    Set<String> metrics = new LinkedHashSet<String>();
    metrics.add("page_comment_removes");
    metrics.add("page_active_users");
    metrics.add("page_like_adds_source_unique");
    assertThat(createBaseQuery(Period.DAY, TEST_PAGE_OBJECT, metrics)).isEqualTo(
      "SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN ('page_comment_removes','page_active_users','page_like_adds_source_unique') AND period=86400 AND end_time=");
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
    assertThat(createBaseQuery(Period.DAY, TEST_PAGE_OBJECT, metrics))
      .isEqualTo("SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
          + "('page_comment_removes','page_like_adds_source_unique') AND period=86400 AND end_time=");
  }

  @Test
  public void buildQueries1() throws ParseException {
    long t20101205_0000 = 1291536000L;
    assertThat(convertToUnixTimeAtPacificTimeZoneMidnightOneDayLater(d20101204_0000pst)).isEqualTo(t20101205_0000);
    assertThat(d20101205_0000pst.getTime() / 1000L).isEqualTo(t20101205_0000);

    List<Date> datesByQueryIndex = new ArrayList<Date>();
    datesByQueryIndex.add(d20101204_0000pst);

    String baseQuery = "SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
        + "('page_active_users') AND period=604800 AND end_time=";

    Map<String, String> fqlByQueryIndex = buildQueries(baseQuery, datesByQueryIndex);
    assertThat(fqlByQueryIndex).hasSize(1);

    String fql = fqlByQueryIndex.values().iterator().next();

    assertThat(fql).isEqualTo(
      "SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN ('page_active_users') AND period=604800 AND end_time="
          + t20101205_0000);
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
    assertThat(utcDates).hasSize(30);

    // convert into PST and convert into a list
    List<Date> datesByQueryIndex = new ArrayList<Date>(convertToMidnightInPacificTimeZone(utcDates));
    assertThat(datesByQueryIndex).hasSize(30);

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

    String baseQuery = "SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
        + "('page_active_users','page_audio_plays') AND period=86400 AND end_time=";

    Map<String, String> fqlByQueryIndex = buildQueries(baseQuery, datesByQueryIndex);
    assertThat(fqlByQueryIndex).hasSize(30);

    String expectedInsightsPrefix = "SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
        + "('page_active_users','page_audio_plays') AND period=86400 AND end_time=";

    assertThat(fqlByQueryIndex.get("0")).isEqualTo(expectedInsightsPrefix + day0);
    assertThat(fqlByQueryIndex.get("5")).isEqualTo(expectedInsightsPrefix + day5);
    assertThat(fqlByQueryIndex.get("6")).isEqualTo(expectedInsightsPrefix + day6);
    assertThat(fqlByQueryIndex.get("7")).isEqualTo(expectedInsightsPrefix + day7);

    assertThat(fqlByQueryIndex.get("29"))
      .isEqualTo("SELECT metric, value FROM insights WHERE object_id='31698190356' AND metric IN "
          + "('page_active_users','page_audio_plays') AND period=86400 AND end_time=" + day29);
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
    SortedMap<Date, JsonArray> results = executeInsightQueriesByDate(
      createFixedResponseFacebookClient("multiResponse_2metrics_1date.json"), TEST_PAGE_OBJECT,
      toStringSet("page_fans", "page_fans_gender"), Period.DAY, Collections.singleton(d20101205_0000pst));
    assertThat(results).isNotNull();
    assertThat(results).hasSize(1);
    JsonArray ja = results.get(d20101205_0000pst);
    assertThat(ja).isNotNull();
    // not ideal that this test requires on a stable JsonArray.toString()
    String expectedJson =
        "[{\"metric\":\"page_fans\",\"value\":3777},{\"metric\":\"page_fans_gender\",\"value\":{\"U\":58,\"F\":1656,\"M\":2014}}]";
    AssertJson.assertEquals(expectedJson, ja.toString());
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

    SortedMap<Date, JsonArray> results = executeInsightQueriesByDate(
      createFixedResponseFacebookClient("multiResponse_2metrics_4dates.json"), TEST_PAGE_OBJECT,
      toStringSet("page_active_users", "page_tab_views_login_top_unique"), Period.DAY, periodEndDates);
    assertThat(results).isNotNull().hasSize(4);
    // not ideal that this test requires on a stable JsonArray.toString()

    String expectedString;
    String actualString;

    expectedString = Json
      .parse(
        "[{\"metric\":\"page_active_users\",\"value\":761},{\"metric\":\"page_tab_views_login_top_unique\",\"value\":{\"photos\":2,\"app_4949752878\":3,\"wall\":30}}]")
      .toString();
    actualString = results.get(d20030629_0000pst).toString();
    AssertJson.assertEquals(expectedString, actualString);

    expectedString = Json
      .parse(
        "[{\"metric\":\"page_active_users\",\"value\":705},{\"metric\":\"page_tab_views_login_top_unique\",\"value\":{\"app_4949752878\":1,\"photos\":1,\"app_2373072738\":2,\"wall\":23}}]")
      .toString();
    actualString = results.get(d20030630_0000pst).toString();
    AssertJson.assertEquals(expectedString, actualString);

    expectedString = Json
      .parse(
        "[{\"metric\":\"page_active_users\",\"value\":582},{\"metric\":\"page_tab_views_login_top_unique\",\"value\":{\"app_4949752878\":1,\"wall\":12}}]")
      .toString();
    actualString = results.get(d20030701_0000pst).toString();
    AssertJson.assertEquals(expectedString, actualString);

    expectedString = Json
      .parse(
        "[{\"metric\":\"page_active_users\",\"value\":125},{\"metric\":\"page_tab_views_login_top_unique\",\"value\":{\"photos\":1,\"wall\":11}}]")
      .toString();
    actualString = results.get(d20030702_0000pst).toString();
    AssertJson.assertEquals(expectedString, actualString);
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
    SortedMap<String, SortedMap<Date, Object>> results = executeInsightQueriesByMetricByDate(
      createFixedResponseFacebookClient("multiResponse_2metrics_1date.json"), TEST_PAGE_OBJECT,
      toStringSet("page_fans", "page_fans_gender"), Period.DAY, Collections.singleton(d20101205_0000pst));
    assertThat(results).isNotNull().hasSize(2);

    SortedMap<Date, Object> metricResult = results.get("page_fans");
    assertThat(metricResult).hasSize(1);
    assertThat(metricResult.get(d20101205_0000pst)).isEqualTo(3777);

    metricResult = results.get("page_fans_gender");
    assertThat(metricResult).hasSize(1);
    Object metricValue = metricResult.get(d20101205_0000pst);
    assertThat(metricValue).isInstanceOf(JsonObject.class);
    JsonObject o = (JsonObject) metricValue;
    assertThat(o.getInt("U", 0)).isEqualTo(58);
    assertThat(o.getInt("F", 0)).isEqualTo(1656);
    assertThat(o.getInt("M", 0)).isEqualTo(2014);
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

    SortedMap<String, SortedMap<Date, Object>> results = executeInsightQueriesByMetricByDate(
      createFixedResponseFacebookClient("multiResponse_2metrics_4dates.json"), TEST_PAGE_OBJECT,
      toStringSet("page_active_users", "page_tab_views_login_top_unique"), Period.DAY, periodEndDates);
    assertThat(results).isNotNull();
    assertThat(results).hasSize(2);

    SortedMap<Date, Object> metricResult = results.get("page_active_users");
    assertThat(metricResult).hasSize(4);
    // {"name":0,"fql_result_set":[{"metric":"page_active_users","value":761},{"metric":"page_tab_views_login_top_unique","value":{"wall":30,"app_4949752878":3,"photos":2}}]},
    // here we validate the date map is sorted, so the results will come out in
    // a predictable order
    Iterator<Object> itValues = metricResult.values().iterator();
    assertThat(itValues).containsExactly(761, 705, 582, 125);

    metricResult = results.get("page_tab_views_login_top_unique");
    assertThat(metricResult).hasSize(4);
    JsonObject o = (JsonObject) metricResult.get(d20030629_0000pst);
    assertThat(o.names()).hasSize(3);
    assertThat(o.getInt("photos", 0)).isEqualTo(2);
    assertThat(o.getInt("app_4949752878", 0)).isEqualTo(3);
    assertThat(o.getInt("wall", 0)).isEqualTo(30);

    o = (JsonObject) metricResult.get(d20030630_0000pst);
    assertThat(o.names()).hasSize(4);
    assertThat(o.getInt("photos", 0)).isEqualTo(1);
    assertThat(o.getInt("app_4949752878", 0)).isEqualTo(1);
    assertThat(o.getInt("app_2373072738", 0)).isEqualTo(2);
    assertThat(o.getInt("wall", 0)).isEqualTo(23);

    o = (JsonObject) metricResult.get(d20030701_0000pst);
    assertThat(o.names()).hasSize(2);
    assertThat(o.getInt("app_4949752878", 0)).isEqualTo(1);
    assertThat(o.getInt("wall", 0)).isEqualTo(12);

    o = (JsonObject) metricResult.get(d20030702_0000pst);
    assertThat(o.names()).hasSize(2);
    assertThat(o.getInt("photos", 0)).isEqualTo(1);
    assertThat(o.getInt("wall", 0)).isEqualTo(11);
  }

  @Test
  public void periodLengths() {
    assertThat(Period.DAY.getPeriodLength()).isEqualTo(86400);
    assertThat(Period.WEEK.getPeriodLength()).isEqualTo(604800);
    assertThat(Period.DAYS_28.getPeriodLength()).isEqualTo(2419200);
    assertThat(Period.MONTH.getPeriodLength()).isEqualTo(2592000);
    assertThat(Period.LIFETIME.getPeriodLength()).isEqualTo(0);
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

    Condition<String> lengthIsGreaterThanZero = new Condition<String>() {

      @Override
      public boolean matches(String s) {
        return s.length() > 0;
      }
    };

    assertThat(jsonBody).isNotNull().is(lengthIsGreaterThanZero).describedAs("path to json not found: %s %s", JSON_RESOURCES_PREFIX, pathToJson);
    return new DefaultFacebookClient(null, wr, new DefaultJsonMapper(), Version.LATEST);
  }

  private static Set<String> toStringSet(String... metrics) {
    return new LinkedHashSet<String>(Arrays.asList(metrics));
  }
}