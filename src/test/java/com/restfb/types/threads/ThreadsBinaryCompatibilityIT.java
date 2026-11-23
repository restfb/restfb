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
package com.restfb.types.threads;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.tools.ToolProvider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ThreadsBinaryCompatibilityIT {

  @TempDir
  Path workDirectory;

  @Test
  void runsAConsumerCompiledAgainstThePublishedJarAndReadsItsSerializedMedia() throws Exception {
    Path baseline = Path.of(System.getProperty("threadsCompatBaselineJar"));
    Path current = Path.of(System.getProperty("threadsCompatCurrentJar"));
    Path consumer = workDirectory.resolve("ThreadsLegacyConsumer.java");
    try (InputStream source = getClass().getResourceAsStream("/threads-compat/ThreadsLegacyConsumer.java")) {
      Files.copy(source, consumer);
    }

    ByteArrayOutputStream diagnostics = new ByteArrayOutputStream();
    int status = ToolProvider.getSystemJavaCompiler().run(null, diagnostics, diagnostics, "--release", "11",
      "-classpath", baseline.toString(), "-d", workDirectory.toString(), consumer.toString());
    assertEquals(0, status, () -> diagnostics.toString(StandardCharsets.UTF_8));

    // Use isolated loaders so neither run can accidentally use RestFB classes from the test classpath.
    try (URLClassLoader oldLoader = loadConsumerWith(baseline); URLClassLoader newLoader = loadConsumerWith(current)) {
      Class<?> oldConsumer = oldLoader.loadClass("ThreadsLegacyConsumer");
      Class<?> newConsumer = newLoader.loadClass("ThreadsLegacyConsumer");

      byte[] serialized = (byte[]) oldConsumer.getMethod("writeMedia").invoke(null);
      assertEquals("100:TEXT_POST:IMAGE:NOT_HUSHED",
        newConsumer.getMethod("readMedia", byte[].class).invoke(null, serialized));
      assertEquals("100", newConsumer.getMethod("readWebhookMedia").invoke(null));
    }
  }

  private URLClassLoader loadConsumerWith(Path restfbJar) throws Exception {
    return new URLClassLoader(new URL[] { workDirectory.toUri().toURL(), restfbJar.toUri().toURL() },
      ClassLoader.getPlatformClassLoader());
  }
}
