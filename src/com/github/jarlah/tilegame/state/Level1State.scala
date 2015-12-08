package com.github.jarlah.tilegame.state

import java.awt.Color
import com.github.jarlah.tilegame.entities.Player
import java.awt.Graphics2D
import com.github.jarlah.tilegame.objects.Block
import com.github.jarlah.tilegame.map.BlockMap

class Level1State(gsm: StateManager) extends State(gsm) {
  val player = new Player(300, 300, 30, 30)
  val map = new BlockMap("", 4, 4)
  
  def draw(g: Graphics2D) = {
    player.draw(g)
    map.draw(g)
  }
  
  def keyPressed(e: Int) = player.keyPressed(e)
  def keyReleased(e: Int) = player.keyReleased(e)
  def tick(delta: Double) = player.tick(delta, map.blocks)
  
  def init = {
    State.xOffset = -200
    State.yOffset = -400
  }
}