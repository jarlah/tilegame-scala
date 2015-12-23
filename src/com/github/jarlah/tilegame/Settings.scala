package com.github.jarlah.tilegame

import java.awt.Dimension
import com.github.jarlah.tilegame.map.Block

trait Settings {
  val GAME_TITLE = "Tile Game"
  val GAME_WIDTH = 23 * Block.blockSize; 
  val GAME_HEIGHT = 12 * Block.blockSize;
  val GAME_DIMENSIONS = new Dimension(GAME_WIDTH, GAME_HEIGHT)
}