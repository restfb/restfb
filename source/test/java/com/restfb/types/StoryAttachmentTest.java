/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.StoryAttachment.Image;
import com.restfb.types.StoryAttachment.Media;
import com.restfb.types.StoryAttachment.Target;

import org.junit.Test;

import java.util.List;

public class StoryAttachmentTest extends AbstractJsonMapperTests {

  @Test
  public void checkImage() {
    StoryAttachment attachment =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/story-attachment"), StoryAttachment.class);
    assertNotNull(attachment.getMedia());
    Image image = attachment.getMedia().getImage();
    assertEquals(45, image.getHeight().intValue());
    assertEquals(175, image.getWidth().intValue());
  }


  @Test
  public void checkV2_2_with_photo_attachment() {
    StoryAttachment attachment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/attachment_photo"), StoryAttachment.class);
    assertEquals("https://www.facebook.com/229204807192138/photos/p.972460529533225/972460529533225/?type=3", attachment.getUrl());
    assertEquals("photo", attachment.getType());
    assertEquals(null, attachment.getTitle());
    assertEquals(null, attachment.getDescription());
    Media media = attachment.getMedia();
    assertNotNull(media);
    Image image = media.getImage();
    assertNotNull(image);
    assertEquals("https://scontent.xx.fbcdn.net/v/t1.0-9/14067576_972460529533225_2677071112954696357_n.jpg?oh=73826da052167394cd8ae9924cc2faee&oe=584DBFBA", image.getSrc());
    assertEquals(199, image.getWidth().intValue());
    assertEquals(60, image.getHeight().intValue());
    Target target = attachment.getTarget();
    assertNotNull(target);
    assertEquals("972460529533225", target.getId());
    assertEquals("https://www.facebook.com/229204807192138/photos/p.972460529533225/972460529533225/?type=3", target.getUrl());
  }

  @Test
  public void checkV2_2_with_link_attachment() {
    StoryAttachment attachment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/attachment_link"), StoryAttachment.class);
    assertEquals("https://www.facebook.com/l.php?u=https%3A%2F%2Fwww.nextinpact.com%2Fnews%2F100994-microsoft-revoit-completement-distribution-mises-a-jour-windows-et-net.htm&h=JAQFvmiuY&s=1&enc=AZNOBmM_KQ7wHJJdkqy875uaZkEu1JQXONmOJGZsLodr5r3OGosdXwv_UpPyYXszc5ZtApz-pkTe9ueXOa7UuO3j-4JSSjyjDhY65y2TT5IQmw", attachment.getUrl());
    assertEquals("share", attachment.getType());
    assertEquals("Microsoft revoit complètement la distribution des mises à jour Windows et .NET", attachment.getTitle());
    assertEquals("Chamboulement chez Microsoft. L'éditeur annonce que les mises à jour des versions supportées de Windows (dès 7 SP1) et de .NET bénéficieront des mises à jour cumulatives inaugurées avec Windows 10, qui s'enrichissent chaque mois de nouveaux correctifs. Explications.", attachment.getDescription());
    Media media = attachment.getMedia();
    assertNotNull(media);
    Image image = media.getImage();
    assertNotNull(image);
    assertEquals("https://external.xx.fbcdn.net/safe_image.php?d=AQBv0avL6ZzrGQS6&w=720&h=720&url=http%3A%2F%2Fcdn2.nextinpact.com%2Fimages%2Fbd%2Fwide-linked-media%2F12112.jpg&cfs=1", image.getSrc());
    assertEquals(619, image.getWidth().intValue());
    assertEquals(619, image.getHeight().intValue());
    Target target = attachment.getTarget();
    assertNotNull(target);
    assertEquals(null, target.getId());
    assertEquals("https://www.facebook.com/l.php?u=https%3A%2F%2Fwww.nextinpact.com%2Fnews%2F100994-microsoft-revoit-completement-distribution-mises-a-jour-windows-et-net.htm&h=JAQFvmiuY&s=1&enc=AZNOBmM_KQ7wHJJdkqy875uaZkEu1JQXONmOJGZsLodr5r3OGosdXwv_UpPyYXszc5ZtApz-pkTe9ueXOa7UuO3j-4JSSjyjDhY65y2TT5IQmw", target.getUrl());
  }

  @Test
  public void checkV2_2_with_video_attachment() {
    StoryAttachment attachment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/attachment_video"), StoryAttachment.class);
    assertEquals("https://www.facebook.com/ad.bmdev/videos/855858167880284/", attachment.getUrl());
    assertEquals("video_inline", attachment.getType());
    assertEquals(null, attachment.getTitle());
    assertEquals(null, attachment.getDescription());
    Media media = attachment.getMedia();
    assertNotNull(media);
    Image image = media.getImage();
    assertNotNull(image);
    assertEquals("https://scontent.xx.fbcdn.net/v/t15.0-10/14030314_855858414546926_571166639_n.jpg?oh=dd9c39f6b8e3264adf3c300f30c2e806&oe=5855F22C", image.getSrc());
    assertEquals(716, image.getWidth().intValue());
    assertEquals(404, image.getHeight().intValue());
    Target target = attachment.getTarget();
    assertNotNull(target);
    assertEquals("855858167880284", target.getId());
    assertEquals("https://www.facebook.com/ad.bmdev/videos/855858167880284/", target.getUrl());
  }

  @Test
  public void checkSubAttachments() {
    StoryAttachment attachment =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/story-attachment"), StoryAttachment.class);
    assertNotNull(attachment.getSubAttachments());
    List<StoryAttachment> subAttachments = attachment.getSubAttachments().getData();
    assertEquals(3, subAttachments.size());
  }

}
