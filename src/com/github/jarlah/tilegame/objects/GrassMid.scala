package com.github.jarlah.tilegame.objects

import java.awt.Graphics2D
import javax.imageio.ImageIO
import com.github.jarlah.tilegame.map.Block

class GrassMid(x: Int, y: Int) extends Block(x, y) {
  val path = "Tiles/grassMid.png"
  
  val image =  ImageIO.read(this.getClass.getClassLoader.getResourceAsStream(path))
  
  override def draw(g: Graphics2D) = {
    g.drawImage(image, x, y, width, height, null)
  }
}