package com.github.jarlah.tilegame.map

import com.github.jarlah.tilegame.objects.Block
import java.awt.Graphics2D

class BlockMap(path: String, width: Int, height: Int) {
  var blocks: Array[Array[Block]] = Array.ofDim(height, width)
  
  for (row <- 0 until height) yield {
    for (col <- 0 until width) yield {
      blocks(row)(col) = new Block(col * Block.blockSize, row * Block.blockSize) 
    }
  }
 
  def draw(g: Graphics2D) {
    for (row <- 0 until height) yield {
      for (col <- 0 until width) yield {
        blocks(row)(col).draw(g)
      }
    }
  }
}