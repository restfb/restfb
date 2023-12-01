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
/**
 * This class is taken with friendly permission to use it 
 * from <a href="http://javaspecialists.co.za/archive/Issue098.html">javaspecialists.co.za/archive/Issue098.html</a> (section 'New SoftHashMap')
 */
package com.restfb.util;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.*;

public class SoftHashMap<K, V> extends AbstractMap<K, V>implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  /** The internal HashMap that will hold the SoftReference. */
  private final Map<K, SoftReference<V>> hash = new HashMap<>();

  private final Map<SoftReference<V>, K> reverseLookup = new HashMap<>();

  /** Reference queue for cleared SoftReference objects. */
  private final ReferenceQueue<V> queue = new ReferenceQueue<>();

  @Override
  public V get(Object key) {
    expungeStaleEntries();
    V result = null;
    // We get the SoftReference represented by that key
    SoftReference<V> softRef = hash.get(key);
    if (softRef != null) {
      // From the SoftReference we get the value, which can be
      // null if it has been garbage collected
      result = softRef.get();
      if (result == null) {
        // If the value has been garbage collected, remove the
        // entry from the HashMap.
        hash.remove(key);
        reverseLookup.remove(softRef);
      }
    }
    return result;
  }

  private void expungeStaleEntries() {
    Reference<? extends V> sv;
    while ((sv = queue.poll()) != null) {
      hash.remove(reverseLookup.remove(sv));
    }
  }

  @Override
  public V put(K key, V value) {
    expungeStaleEntries();
    SoftReference<V> softRef = new SoftReference<>(value, queue);
    reverseLookup.put(softRef, key);
    SoftReference<V> result = hash.put(key, softRef);
    if (result == null) {
      return null;
    }
    reverseLookup.remove(result);
    return result.get();
  }

  @Override
  public V remove(Object key) {
    expungeStaleEntries();
    return Optional.ofNullable(hash.remove(key)).map(SoftReference::get).orElse(null);
  }

  @Override
  public void clear() {
    hash.clear();
    reverseLookup.clear();
  }

  @Override
  public int size() {
    expungeStaleEntries();
    return hash.size();
  }

  /**
   * Returns a copy of the key/values in the map at the point of calling. However, setValue still sets the value in the
   * actual SoftHashMap.
   */
  @Override
  public Set<Entry<K, V>> entrySet() {
    expungeStaleEntries();
    Set<Entry<K, V>> result = new LinkedHashSet<>();
    for (final Entry<K, SoftReference<V>> entry : hash.entrySet()) {
      final V value = entry.getValue().get();
      if (value != null) {
        result.add(new Entry<K, V>() {
          @Override
          public K getKey() {
            return entry.getKey();
          }

          @Override
          public V getValue() {
            return value;
          }

          @Override
          public V setValue(V v) {
            entry.setValue(new SoftReference<>(v, queue));
            return value;
          }
        });
      }
    }
    return result;
  }
}
