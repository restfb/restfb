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
package com.restfb.types.send;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/messenger-platform/reference/buttons/game-play">Game
 * Play Button</a> type
 */
public class GamePlayButton extends AbstractButton {

  @Facebook
  private String payload;

  @Facebook("game_metadata")
  private GameMetadata gameMetadata;

  public GamePlayButton(String title) {
    super(title);
    setType("game_play");
  }

  public void setGameMetadata(GameMetadata gameMetadata) {
    this.gameMetadata = gameMetadata;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public static class GameMetadata extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    @Facebook("player_id")
    private String playerId;

    @Setter
    @Getter
    @Facebook("context_id")
    private String contextId;

  }
}
