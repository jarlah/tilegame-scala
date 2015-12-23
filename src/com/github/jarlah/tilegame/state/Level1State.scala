package com.github.jarlah.tilegame.state

import java.awt.Color
import com.github.jarlah.tilegame.entities.Player
import java.awt.Graphics2D
import com.github.jarlah.tilegame.map.Block
import com.github.jarlah.tilegame.map.BlockMap
import javax.imageio.ImageIO
import com.github.jarlah.tilegame.Settings

class Level1State(gsm: StateManager) extends State(gsm) with Settings {
  val player = new Player(2 * Block.blockSize, 2 * Block.blockSize, Block.blockSize, Block.blockSize)
  
  val map = new BlockMap("/Maps/level1.map")
  
  val bg = ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Tiles/background.jpg"))
  
  def draw(g: Graphics2D) = {
    g.drawImage(bg, 0, 0, GAME_WIDTH, GAME_HEIGHT, null)
    map.draw(g)
    player.draw(g)
  }
  
  def keyPressed(e: Int) = player.keyPressed(e)
  
  def keyReleased(e: Int) = player.keyReleased(e)
  
  def tick(delta: Double) = player.tick(delta, map.blocks)
  
  def init = Nil
}