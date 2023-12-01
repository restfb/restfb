/*
 * Copyright (c) 2010-2024 Mark Allen, Norbert Bartels.
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

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

/**
 * Represents the <a href="http://developers.facebook.com/docs/api#impersonation">Account Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Account extends Page {

  private static final long serialVersionUID = 1L;

  @Facebook("tasks")
  private List<String> tasks = new ArrayList<>();

  /**
   * A list of tasks allowed to perform for this page.
   * <a href="https://developers.facebook.com/docs/pages/access-tokens#page-tasks">See tasks list here</a>
   *
   * @return A list of allowed tasks
   */
  public List<String> getTasks() {
    return unmodifiableList(tasks);
  }

  /**
   * Add a task to the task list.
   *
   * @param task
   *          the task is added to the task list
   * @return {@code true} if the task is added {@code false} instead
   */
  public boolean addTask(String task) {
    return tasks.add(task);
  }

  /**
   * Remove the task from the task list.
   *
   * @param task
   *          the task is removed from the task list
   * @return {@code true} if the task is removed {@code false} instead
   */
  public boolean removeTask(String task) {
    return tasks.remove(task);
  }

}