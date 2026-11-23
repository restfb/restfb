import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.restfb.DefaultJsonMapper;
import com.restfb.types.threads.TdMedia;
import com.restfb.types.threads.TdMediaType;
import com.restfb.types.threads.TdReply;
import com.restfb.types.webhook.ChangeValueFactory;
import com.restfb.types.webhook.threads.ThreadsMediaValue;

// Compiled once, against the published 2026.10.0 JAR, by ThreadsBinaryCompatibilityIT.
public class ThreadsLegacyConsumer {

  public static byte[] writeMedia() throws IOException {
    TdMedia media = new TdMedia();
    media.setId("100");
    media.setMediaType(TdMediaType.TEXT_POST);
    TdMedia child = new TdMedia();
    child.setMediaType(TdMediaType.IMAGE);
    media.setChildren(List.of(child));
    TdReply root = new TdReply();
    root.setHideStatus(TdReply.TdHideStatus.NOT_HUSHED);
    media.setRootPost(root);

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
      out.writeObject(media);
    }
    return bytes.toByteArray();
  }

  public static String readMedia(byte[] bytes) throws IOException, ClassNotFoundException {
    try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
      TdMedia media = (TdMedia) in.readObject();
      return media.getId() + ":" + media.getMediaType() + ":" + media.getChildren().get(0).getMediaType() + ":"
          + media.getRootPost().getHideStatus();
    }
  }

  public static String readWebhookMedia() {
    ThreadsMediaValue value =
        (ThreadsMediaValue) new ChangeValueFactory().setPlatform(ChangeValueFactory.Platform.THREADS)
          .setField("mentions").setValue("{\"id\":\"100\"}").buildWithMapper(new DefaultJsonMapper());
    TdMedia media = value.getMedia();
    value.setMedia(new TdMedia());
    value.setMedia(media);
    return value.getMedia().getId();
  }
}
