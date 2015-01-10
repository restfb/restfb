/*
 * Copyright (c) 2010-2015 Norbert Bartels
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
import com.restfb.JsonMapper.JsonMappingCompleted;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * DeAuth type, returned on deauthorization callback
 * 
 * @since 1.6.16
 */
public class DeAuth implements Serializable {

  @Getter @Setter
  @Facebook
  private String algorithm;

  @Facebook("issued_at")
  private String rawIssuedAt;
  
  @Getter @Setter
  private Date issuedAt;

  @Getter @Setter
  @Facebook("profile_id")
  private String profileId;

  @Getter @Setter
  @Facebook("user_id")
  private String userId;

  @JsonMappingCompleted
  void convertTime() {
      issuedAt = toDateFromLongFormat(rawIssuedAt);
  }
}
