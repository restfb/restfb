/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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

  @Deprecated
  @Facebook("perms")
  private List<String> perms = new ArrayList<>();

  @Facebook("tasks")
  private List<String> tasks = new ArrayList<>();

  /**
   * A list of permissions the user has for this page.
   * <a href="https://developers.facebook.com/docs/facebook-login/access-tokens/#pagetokens">See roles list here</a>
   * 
   * @return A list of permissions the user has for this page.
   * @deprecated since graph api v3.1, use getTasks() instead
   */
  @Deprecated
  public List<String> getPerms() {
    return unmodifiableList(perms);
  }

  /**
   * add a permission to the permission list.
   * 
   * @param permission
   */
  @Deprecated
  public void addPerm(String permission) {
    perms.add(permission);
  }

  /**
   * remove the permission from the permission list.
   * 
   * @param permission
   * @return
   */
  @Deprecated
  public boolean removePerm(String permission) {
    return perms.remove(permission);
  }

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
   */
  public void addTask(String task) {
    tasks.add(task);
  }

  /**
   * Remove the task from the task list.
   *
   * @param task
   * @return
   */
  public boolean removeTask(String task) {
    return tasks.remove(task);
  }

}