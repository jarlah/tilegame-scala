package com.github.jarlah.tilegame.objects

import java.awt.Rectangle
import java.awt.Graphics2D
import java.awt.Color
import com.github.jarlah.tilegame.state.StateManager
import com.github.jarlah.tilegame.state.State

class Block(x: Int, y: Int) extends Rectangle(x, y, Block.blockSize, Block.blockSize) {
  def draw(g: Graphics2D) = {
    g.setColor(Color.CYAN)
    g.fillRect(x - State.xOffset.toInt, y - State.yOffset.toInt, width, height)
  }
}

object Block {
  val blockSize: Int = 64
}