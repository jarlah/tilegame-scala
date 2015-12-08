package com.github.jarlah.tilegame.state

import java.awt.Color
import com.github.jarlah.tilegame.entities.Player
import java.awt.Graphics2D
import com.github.jarlah.tilegame.objects.Block

class Level1State(gsm: StateManager) extends State(gsm) {
  val player = new Player(GAME_WIDTH / 2, GAME_HEIGHT / 2, 30, 30)
  
  val blocks: Array[Block] = Array[Block](
    new Block(100, 100),
    new Block(200, 200),
    new Block(300, 300)
  )
  
  def draw(g: Graphics2D) = {
    player.draw(g)
    blocks.map(b => b.draw(g))
  }
  
  def keyPressed(e: Int) = player.keyPressed(e)
  def keyReleased(e: Int) = player.keyReleased(e)
  def tick(delta: Double) = player.tick(delta)
  
  def init = Nil
}