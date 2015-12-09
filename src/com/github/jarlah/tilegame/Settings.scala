package com.github.jarlah.tilegame

import java.awt.Dimension

trait Settings {
  val GAME_TITLE = "Tile Game"
  val GAME_WIDTH = 768; 
  val GAME_HEIGHT = 384;
  val GAME_DIMENSIONS = new Dimension(GAME_WIDTH, GAME_HEIGHT)
}