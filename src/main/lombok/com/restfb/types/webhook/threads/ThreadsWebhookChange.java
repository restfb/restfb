/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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
package com.restfb.types.webhook.threads;

import com.restfb.types.webhook.Change;
import com.restfb.types.webhook.ChangeValueFactory;

/**
 * The object-valued {@code values} property of a
 * <a href="https://developers.facebook.com/documentation/threads/webhooks/">Threads notification</a>. Reuses
 * {@link Change}'s mapping lifecycle, but resolves {@code replies}, {@code mentions}, {@code delete}, and
 * {@code publish} in the Threads namespace. Known events map to {@link ThreadsMediaValue}; unknown object values retain
 * their JSON in {@link com.restfb.types.webhook.FallBackChangeValue}.
 */
public class ThreadsWebhookChange extends Change {

  @Override
  protected ChangeValueFactory createValueFactory() {
    return super.createValueFactory().setPlatform(ChangeValueFactory.Platform.THREADS);
  }
}
