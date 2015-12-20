package com.github.jarlah.tilegame.state

import java.awt.Color
import com.github.jarlah.tilegame.entities.Player
import java.awt.Graphics2D
import com.github.jarlah.tilegame.objects.Block
import com.github.jarlah.tilegame.map.BlockMap

class Level1State(gsm: StateManager) extends State(gsm) {
  val player = new Player(2 * Block.blockSize, 2 * Block.blockSize, 20, 20)
  
  val map = new BlockMap("/Maps/level1.map")
  
  def draw(g: Graphics2D) = {
    map.draw(g)
    player.draw(g)
  }
  
  def keyPressed(e: Int) = player.keyPressed(e)
  
  def keyReleased(e: Int) = player.keyReleased(e)
  
  def tick(delta: Double) = player.tick(delta, map.blocks)
  
  def init = Nil
}