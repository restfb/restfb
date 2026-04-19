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
package com.restfb;

import static java.lang.String.format;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.function.Supplier;

import com.restfb.util.ObjectUtil;
import com.restfb.util.ReflectionUtils;

import lombok.Getter;

/**
 * Represents a binary file that can be uploaded to Facebook.
 * <p>
 * Normally this would be a photo or video.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author Marcel Stoer
 * @since 1.6.5
 */
public class BinaryAttachment {

  private static final String FIELD_NAME_CANNOT_BE_NULL = "Field name cannot be null.";

  @Getter
  private final String filename;

  protected byte[] data;

  private Supplier<InputStream> dataSupplier;

  private String contentType;

  @Getter
  private String fieldName;

  protected BinaryAttachment() {
    filename = "default";
  }

  /**
   * Creates a new binary attachment backed by a stream supplier.
   * 
   * @param filename
   *          The attachment's filename.
   * @param dataSupplier
   *          Provides a fresh {@link InputStream} for each access.
   * @throws IllegalArgumentException
   *           If {@code dataSupplier} is {@code null} or {@code filename} is {@code null} or blank.
   */
  protected BinaryAttachment(String filename, Supplier<InputStream> dataSupplier) {
    ObjectUtil.requireNotEmpty(filename, "Binary attachment filename cannot be blank.");
    ObjectUtil.verifyParameterPresence("dataSupplier", dataSupplier);
    this.filename = filename;
    this.dataSupplier = dataSupplier;
  }

  /**
   * Creates a new binary attachment backed by a stream supplier.
   *
   * @param filename
   *          The attachment's filename.
   * @param dataSupplier
   *          Provides a fresh {@link InputStream} for each access.
   * @param fieldName
   *          The field name the binary belongs to
   * @throws IllegalArgumentException
   *           If {@code dataSupplier} is {@code null} or {@code filename} is {@code null} or blank.
   */
  protected BinaryAttachment(String fieldName, String filename, Supplier<InputStream> dataSupplier) {
    this(filename, dataSupplier);
    ObjectUtil.requireNotEmpty(fieldName, FIELD_NAME_CANNOT_BE_NULL);

    this.fieldName = fieldName;
  }

  public boolean isFacebookReel() {
    return false;
  }

  /**
   * Creates a new binary attachment backed by a stream supplier.
   * 
   * @param filename
   *          The attachment's filename.
   * @param dataSupplier
   *          Provides a fresh {@link InputStream} for each access.
   * @param contentType
   *          The attachment's contentType.
   * @throws IllegalArgumentException
   *           If {@code dataSupplier} is {@code null}, {@code filename} is {@code null} or blank, or {@code contentType}
   *           is {@code null} or blank.
   * @since 1.6.13
   */
  protected BinaryAttachment(String filename, Supplier<InputStream> dataSupplier, String contentType) {
    this(filename, dataSupplier);
    ObjectUtil.requireNotEmpty(contentType, "ContentType cannot be null.");

    this.contentType = contentType;
  }

  /**
   * Creates a new binary attachment backed by a stream supplier.
   *
   * @param filename
   *          The attachment's filename.
   * @param dataSupplier
   *          Provides a fresh {@link InputStream} for each access.
   * @param contentType
   *          The attachment's contentType.
   * @param fieldName
   *          The field name the binary belongs to
   * @throws IllegalArgumentException
   *           If {@code dataSupplier} is {@code null}, {@code filename} is {@code null} or blank, or {@code contentType}
   *           is {@code null} or blank.
   * @since 1.6.13
   */
  protected BinaryAttachment(String fieldName, String filename, Supplier<InputStream> dataSupplier, String contentType) {
    this(filename, dataSupplier, contentType);
    ObjectUtil.requireNotEmpty(fieldName, FIELD_NAME_CANNOT_BE_NULL);

    this.fieldName = fieldName;
  }

  /**
   * Creates a new binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   * @since 1.6.17
   */
  protected BinaryAttachment(String filename, byte[] data) {
    ObjectUtil.requireNotEmpty(filename, "Binary attachment filename cannot be blank.");
    ObjectUtil.verifyParameterPresence("data", data);

    this.filename = filename;
    this.data = data;
  }

  /**
   * Creates a new binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param fieldName
   *          The field name the binary belongs to
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   * @since 1.6.17
   */
  protected BinaryAttachment(String fieldName, String filename, byte[] data) {
    this(filename, data);
    ObjectUtil.requireNotEmpty(fieldName, FIELD_NAME_CANNOT_BE_NULL);

    this.fieldName = fieldName;
  }

  /**
   * Creates a new binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param contentType
   *          The attachment's contentType.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null}, {@code filename} is {@code null} or blank, or {@code contentType} is
   *           {@code null} or blank.
   * @since 1.6.17
   */
  protected BinaryAttachment(String filename, byte[] data, String contentType) {
    this(filename, data);
    ObjectUtil.requireNotEmpty(contentType, "ContentType cannot be null.");

    this.contentType = contentType;
  }

  /**
   * Creates a new binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param contentType
   *          The attachment's contentType.
   * @param fieldName
   *          The field name the binary belongs to
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null}, {@code filename} is {@code null} or blank, or {@code contentType} is
   *           {@code null} or blank.
   * @since 1.6.17
   */
  protected BinaryAttachment(String fieldName, String filename, byte[] data, String contentType) {
    this(filename, data, contentType);
    ObjectUtil.requireNotEmpty(fieldName, FIELD_NAME_CANNOT_BE_NULL);

    this.fieldName = fieldName;
  }

  /**
   * Creates a binary attachment backed by a stream supplier.
   * 
   * @param filename
   *          The attachment's filename.
   * @param dataSupplier
   *          Provides a fresh {@link InputStream} for each access.
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code dataSupplier} is {@code null} or {@code filename} is {@code null} or blank.
   */
  public static BinaryAttachment with(String filename, Supplier<InputStream> dataSupplier) {
    return new BinaryAttachment(filename, dataSupplier);
  }

  /**
   * Creates a binary attachment backed by a stream supplier.
   *
   * @param filename
   *          The attachment's filename.
   * @param dataSupplier
   *          Provides a fresh {@link InputStream} for each access.
   * @param fieldName
   *          The field name the binary belongs to
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code dataSupplier} is {@code null} or {@code filename} is {@code null} or blank.
   */
  public static BinaryAttachment with(String fieldName, String filename, Supplier<InputStream> dataSupplier) {
    return new BinaryAttachment(fieldName, filename, dataSupplier);
  }

  /**
   * Creates a binary attachment backed by a stream supplier.
   * 
   * @param filename
   *          The attachment's filename.
   * @param dataSupplier
   *          Provides a fresh {@link InputStream} for each access.
   * @param contentType
   *          The attachment's contentType.
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code dataSupplier} is {@code null} or {@code filename} is {@code null} or blank.
   */
  public static BinaryAttachment with(String filename, Supplier<InputStream> dataSupplier, String contentType) {
    return new BinaryAttachment(filename, dataSupplier, contentType);
  }

  /**
   * Creates a binary attachment backed by a stream supplier.
   *
   * @param filename
   *          The attachment's filename.
   * @param dataSupplier
   *          Provides a fresh {@link InputStream} for each access.
   * @param fieldName
   *          The field name the binary belongs to
   * @param contentType
   *          The attachment's contentType.
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code dataSupplier} is {@code null} or {@code filename} is {@code null} or blank.
   */
  public static BinaryAttachment with(String fieldName, String filename, Supplier<InputStream> dataSupplier,
      String contentType) {
    return new BinaryAttachment(fieldName, filename, dataSupplier, contentType);
  }

  /**
   * Creates a binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   * @since 1.6.17
   */
  public static BinaryAttachment with(String filename, byte[] data) {
    return new BinaryAttachment(filename, data);
  }

  /**
   * Creates a binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param fieldName
   *          The field name the binary belongs to
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   * @since 1.6.17
   */
  public static BinaryAttachment with(String fieldName, String filename, byte[] data) {
    return new BinaryAttachment(fieldName, filename, data);
  }

  /**
   * Creates a binary attachment.
   * 
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param contentType
   *          The attachment's contentType.
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   * @since 1.6.17
   */
  public static BinaryAttachment with(String filename, byte[] data, String contentType) {
    return new BinaryAttachment(filename, data, contentType);
  }

  /**
   * Creates a binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param contentType
   *          The attachment's contentType.
   * @param fieldName
   *          The field name the binary belongs to
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   * @since 1.6.17
   */
  public static BinaryAttachment with(String fieldName, String filename, byte[] data, String contentType) {
    return new BinaryAttachment(fieldName, filename, data, contentType);
  }

  @Override
  public int hashCode() {
    return ReflectionUtils.hashCode(this);
  }

  @Override
  public boolean equals(Object that) {
    return ReflectionUtils.equals(this, that);
  }

  @Override
  public String toString() {
    return format("[filename=%s]", getFilename());
  }

  /**
   * The attachment's data.
   * 
   * @return The attachment's data.
   */
  public InputStream getData() {
    if (data != null) {
      return new ByteArrayInputStream(data);
    } else if (dataSupplier != null) {
      InputStream stream = dataSupplier.get();
      ObjectUtil.verifyParameterPresence("dataSupplier result", stream);
      return stream;
    } else {
      throw new IllegalStateException("Either the byte[] or the stream mustn't be null at this point.");
    }
  }

  /**
   * return the given content type or try to guess from stream or file name. Depending of the available data.
   * 
   * @return the content type
   */
  public String getContentType() {
    if (contentType != null) {
      return contentType;
    }

    if (dataSupplier != null) {
      try (InputStream stream = dataSupplier.get()) {
        if (stream != null) {
          contentType = URLConnection.guessContentTypeFromStream(stream);
        }
      } catch (IOException ioe) {
        // ignore exception
      }
    }

    if (data != null) {
      contentType = URLConnection.getFileNameMap().getContentTypeFor(filename);
    }

    // fallback - if we have no contenttype and cannot detect one, use 'application/octet-stream'
    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return contentType;
  }

  public boolean hasBinaryData() {
    return data != null;
  }

  /**
   * Returns the form field name used for multipart uploads.
   *
   * @return the explicit field name if set, otherwise the filename without its extension
   */
  public String getFormFieldName() {
    if (fieldName != null) {
      return fieldName;
    }

    String name = getFilename();
    if (name == null) {
      return null;
    }

    int dotIndex = name.lastIndexOf('.');
    return dotIndex > 0 ? name.substring(0, dotIndex) : name;
  }
}
