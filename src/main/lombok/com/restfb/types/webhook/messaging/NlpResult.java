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
package com.restfb.types.webhook.messaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.json.JsonObject;
import com.restfb.types.webhook.messaging.nlp.*;

public class NlpResult {

  private List<BaseNlpEntity> convertedEntities = new ArrayList<>();

  @Facebook("entities")
  private JsonObject rawEntities;

  @Facebook
  private List<NlpError> errors = new ArrayList<>();

  @JsonMapper.JsonMappingCompleted
  public void convertRawEntites(JsonMapper mapper) {
    List<String> names = (rawEntities != null) ? rawEntities.names() : Collections.<String> emptyList();
    for (String key : names) {
      if ("datetime".equals(key)) {
        List<NlpDatetime> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpDatetime.class);
        convertedEntities.addAll(list);
      } else if ("bye".equals(key)) {
        List<NlpBye> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpBye.class);
        convertedEntities.addAll(list);
      } else if ("reminder".equals(key)) {
        List<NlpReminder> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpReminder.class);
        convertedEntities.addAll(list);
      } else if ("greetings".equals(key)) {
        List<NlpGreetings> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpGreetings.class);
        convertedEntities.addAll(list);
      } else if ("amount_of_money".equals(key)) {
        List<NlpAmountOfMoney> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpAmountOfMoney.class);
        convertedEntities.addAll(list);
      } else if ("phone_number".equals(key)) {
        List<NlpPhoneNumber> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpPhoneNumber.class);
        convertedEntities.addAll(list);
      } else if ("email".equals(key)) {
        List<NlpEmail> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpEmail.class);
        convertedEntities.addAll(list);
      } else if ("distance".equals(key)) {
        List<NlpDistance> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpDistance.class);
        convertedEntities.addAll(list);
      } else if ("volume".equals(key)) {
        List<NlpVolume> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpVolume.class);
        convertedEntities.addAll(list);
      } else if ("temperature".equals(key)) {
        List<NlpTemperature> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpTemperature.class);
        convertedEntities.addAll(list);
      } else if ("quantity".equals(key)) {
        List<NlpQuantity> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpQuantity.class);
        convertedEntities.addAll(list);
      } else if ("duration".equals(key)) {
        List<NlpDuration> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpDuration.class);
        convertedEntities.addAll(list);
      } else if ("sentiment".equals(key)) {
        List<NlpSentiment> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpSentiment.class);
        convertedEntities.addAll(list);
      } else if ("url".equals(key)) {
        List<NlpUrl> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpUrl.class);
        convertedEntities.addAll(list);
      } else if ("location".equals(key)) {
        List<NlpLocation> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpLocation.class);
        convertedEntities.addAll(list);
      } else {
        List<NlpCustomWitAi> list = mapper.toJavaList(rawEntities.get(key).toString(), NlpCustomWitAi.class);
        for (NlpCustomWitAi customNlp : list) {
          customNlp.setWitAiKey(key);
        }
        convertedEntities.addAll(list);
      }
    }
  }

  /**
   * returns the complete list of all found entities.
   * 
   * @return the complete list of all found entities.
   */
  public List<BaseNlpEntity> getEntities() {
    return convertedEntities;
  }

  /**
   * returns the complete list of all found errors
   *
   * @return the complete list of all found errors
   */
  public List<NlpError> getErrors() {
    return errors;
  }

  /**
   * checks if the returned JSON contains the error field
   *
   * @return {@code true} if the NLP result found errors, {@code false} otherwise
   */
  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  /**
   * returns a subset of the found entities.
   *
   * Only entities that are of type <code>T</code> are returned. T needs to extend the {@link BaseNlpEntity}.
   *
   * @param clazz
   *          the filter class
   * @return List of entites, only the filtered elements are returned.
   */
  public <T extends BaseNlpEntity> List<T> getEntities(Class<T> clazz) {
    List<BaseNlpEntity> resultList = new ArrayList<>();
    for (BaseNlpEntity item : getEntities()) {
      if (item.getClass().equals(clazz)) {
        resultList.add(item);
      }
    }
    return (List<T>) resultList;
  }
}
