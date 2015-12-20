package com.github.jarlah.tilegame.objects

import java.awt.Rectangle
import java.awt.Graphics2D
import java.awt.Color
import com.github.jarlah.tilegame.state.StateManager
import com.github.jarlah.tilegame.state.State

object Block {
  val EMPTY = 0
  val blockSize: Int = 32
}

import Block._

class Block(x: Int, y: Int, id: Int) extends Rectangle(x, y, blockSize, blockSize) {
  def draw(g: Graphics2D) = {
    if (id == 1) {
      g.setColor(Color.DARK_GRAY)
    }
    if (id == 4) {
      g.setColor(Color.RED)
    }
    if (id == 5) {
      g.setColor(Color.BLUE)
    }
    
    if (id != 0) {
      g.fillRect(x - State.xOffset.toInt, y - State.yOffset.toInt, width, height)
    }
  }
  
  def blocking = id == 1
}

