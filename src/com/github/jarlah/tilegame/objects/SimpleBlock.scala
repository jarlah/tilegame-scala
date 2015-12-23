package com.github.jarlah.tilegame.objects

import java.awt.Color
import java.awt.Graphics2D
import com.github.jarlah.tilegame.state.State
import com.github.jarlah.tilegame.map.Block

class SimpleBlock(x: Int, y: Int, id: Int) extends Block(x, y) {
  override def draw(g: Graphics2D) = {
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
  
  override def blocking = id == 1
}