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
package com.restfb.types;

import static com.restfb.testutils.AssertJson.assertEquals;

import org.junit.jupiter.api.Test;

import com.restfb.DefaultJsonMapper;
import com.restfb.types.send.GamePlayButton;

class GamePlayButtonTest {

  private final DefaultJsonMapper mapper = new DefaultJsonMapper();

  @Test
  void checkGamePlayButton_basic() {
    GamePlayButton button = new GamePlayButton("Play");
    String json = mapper.toJson(button, true);
    assertEquals("{\"type\":\"game_play\",\"title\":\"Play\"}", json);
  }

  @Test
  void checkGamePlayButton_payload() {
    GamePlayButton button = new GamePlayButton("Play");
    button.setPayload("{<SERIALIZED_JSON_PAYLOAD>}");
    String json = mapper.toJson(button, true);
    assertEquals("{\"type\":\"game_play\",\"title\":\"Play\",\"payload\":\"{<SERIALIZED_JSON_PAYLOAD>}\"}", json);
  }

  @Test
  void checkGamePlayButton_GameMetadata() {
    GamePlayButton button = new GamePlayButton("Play");
    button.setPayload("{<SERIALIZED_JSON_PAYLOAD>}");
    GamePlayButton.GameMetadata metadata = new GamePlayButton.GameMetadata();
    metadata.setPlayerId("<PLAYER_ID>");
    metadata.setContextId("<CONTEXT_ID>");
    button.setGameMetadata(metadata);
    String json = mapper.toJson(button, true);
    assertEquals("{\"payload\":\"{<SERIALIZED_JSON_PAYLOAD>}\",\"game_metadata\":{\"player_id\":\"<PLAYER_ID>\",\"context_id\":\"<CONTEXT_ID>\"},\"type\":\"game_play\",\"title\":\"Play\"}", json);
  }
}
