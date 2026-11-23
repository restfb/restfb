
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Generates the legacy Threads models from the delomboked sources. Remove with Td* support. */
class GenerateThreadsCompatibility {

  // Only the types published under a Td* name are mirrored, even if new Th* models land later.
  private static final List<String> MODELS = List.of("ThLocation", "ThMedia", "ThMediaContainer", "ThMediaType",
    "ThPollAttachment", "ThProfile", "ThProfileBase", "ThPublicProfile", "ThQuota", "ThReply", "ThTextAttachment");

  private static final Set<String> RENAMED_MEMBERS = Set.of("ThHideStatus", "ThReplyAudience", "ThReplyConfig");
  private static final String PACKAGE_PATH = "com/restfb/types/threads";
  private static final String IDENTIFIER = "\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*";

  // Match comments and literals as whole tokens so Graph keys and string values are never renamed.
  private static final Pattern TOKENS =
      Pattern.compile("(?m)" + "(?<doc>/\\*\\*[\\s\\S]*?\\*/)|/\\*[\\s\\S]*?\\*/|//[^\\r\\n]*"
          + "|\"\"\"(?:\\\\[\\s\\S]|[^\"\\\\]|\"(?!\"\"))*+\"\"\"" + "|\"(?:\\\\.|[^\"\\\\])*+\"|'(?:\\\\.|[^'\\\\])*+'"
          + "|^(?<indent>[ \\t]*)(?<modifiers>public (?:static |abstract |final )*(?:class|enum) )" + "(?<name>"
          + IDENTIFIER + ")" + "|(?<identifier>" + IDENTIFIER + ")");

  public static void main(String[] args) throws IOException {
    Path input = Path.of(args[0]).resolve(PACKAGE_PATH);
    Path output = Files.createDirectories(Path.of(args[1]).resolve(PACKAGE_PATH));
    for (String model : MODELS) {
      String source = Files.readString(input.resolve(model + ".java"));
      Files.writeString(output.resolve(legacyName(model) + ".java"), generate(source, model));
    }
  }

  private static String generate(String source, String model) {
    source = source.replace("\r\n", "\n");
    Matcher tokens = TOKENS.matcher(source);
    StringBuilder output = new StringBuilder("// Generated for Threads compatibility. Do not edit.\n");
    int end = 0;
    boolean afterJavadoc = false;
    boolean foundModel = false;
    while (tokens.find()) {
      String gap = source.substring(end, tokens.start());
      output.append(gap);
      String typeName = tokens.group("name");
      String identifier = tokens.group("identifier");
      if (typeName != null) {
        String indent = tokens.group("indent");
        boolean topLevel = typeName.equals(model);
        foundModel |= topLevel;
        String replacement = topLevel ? model : model + "." + typeName;
        if (afterJavadoc && gap.isBlank()) {
          // Extend the existing Javadoc rather than leaving two comments on the declaration.
          int docEnd = output.length() - gap.length() - 2;
          while (Character.isWhitespace(output.charAt(docEnd - 1))) {
            docEnd--;
          }
          output.setLength(docEnd);
          output.append('\n');
        } else {
          output.append(indent).append("/**\n");
        }
        output.append(indent).append(" * @deprecated Use {@link ").append(replacement)
          .append("}. Scheduled for removal no earlier than 2028.\n").append(indent).append(" */\n").append(indent)
          .append("@Deprecated(forRemoval = true)\n").append(indent).append(tokens.group("modifiers"))
          .append(legacyName(typeName));
      } else {
        output.append(identifier == null ? tokens.group() : legacyName(identifier));
      }
      afterJavadoc = tokens.group("doc") != null;
      end = tokens.end();
    }
    if (!foundModel) {
      throw new IllegalArgumentException("No public declaration found for " + model);
    }
    return output.append(source.substring(end)).toString();
  }

  private static String legacyName(String identifier) {
    return MODELS.contains(identifier) || RENAMED_MEMBERS.contains(identifier) ? "Td" + identifier.substring(2)
        : identifier;
  }
}
