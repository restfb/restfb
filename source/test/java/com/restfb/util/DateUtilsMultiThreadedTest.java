package com.restfb.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsMultiThreadedTest {

  static class DateAsLongGenerator {
    private final AtomicLong counter = new AtomicLong();

    public long getLongDate() throws InterruptedException {
      long res = 0L;
      long yearIncrement = 1900 + counter.incrementAndGet();
      for (int day = 10; day <= 30; day++) {
        Thread.sleep(10);
        String date = day + "/01/" + yearIncrement;
        res = DateUtils.toDateFromShortFormat(date).getTime();
      }
      return res;
    }
  }

  private void test(final int threadCount) throws InterruptedException, ExecutionException {
    final DateAsLongGenerator domainObject = new DateAsLongGenerator();

    Callable<Long> task = new Callable<Long>() {
      @Override
      public Long call() throws InterruptedException {
        return domainObject.getLongDate();
      }
    };

    List<Callable<Long>> tasks = Collections.nCopies(threadCount, task);

    // create thread pool and calc dates
    ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
    List<Future<Long>> futures = executorService.invokeAll(tasks);
    List<Long> resultList = new ArrayList<Long>(futures.size());
    for (Future<Long> future : futures) {
      resultList.add(future.get());
    }

    // first and simple assert
    Assert.assertEquals(threadCount, futures.size());

    // generate list with expected values
    List<Long> expectedList = new ArrayList<Long>(threadCount);
    for (long i = 1; i <= threadCount; i++) {
      long yearIncrement = 1900 + i;
      String date = "30/01/" + yearIncrement;
      expectedList.add(DateUtils.toDateFromShortFormat(date).getTime());
    }

    // prepare collection comparison
    Collections.sort(resultList);
    Assert.assertEquals(expectedList, resultList);
  }

  @Test
  public void test01() throws InterruptedException, ExecutionException {
    test(1);
  }

  @Test
  public void test02() throws InterruptedException, ExecutionException {
    test(2);
  }

  @Test
  public void test04() throws InterruptedException, ExecutionException {
    test(4);
  }

  @Test
  public void test08() throws InterruptedException, ExecutionException {
    test(8);
  }

  @Test
  public void test16() throws InterruptedException, ExecutionException {
    test(16);
  }

  @Test
  public void test32() throws InterruptedException, ExecutionException {
    test(32);
  }

  @Test
  public void test64() throws InterruptedException, ExecutionException {
    test(64);
  }
}