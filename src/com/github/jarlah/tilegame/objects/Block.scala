package com.github.jarlah.tilegame.objects

import java.awt.Rectangle
import java.awt.Graphics2D
import java.awt.Color

class Block(x: Int, y: Int, private val blockSize: Int = 32) extends Rectangle(x,y, blockSize, blockSize) {
  def draw(g: Graphics2D) = {
    g.setColor(Color.CYAN)
    g.fillRect(x, y, width, height)
  }
}