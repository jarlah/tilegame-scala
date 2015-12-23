package com.github.jarlah.tilegame.map

import com.github.jarlah.tilegame.map.Block
import java.awt.Graphics2D
import scala.io.Source
import scala.collection.mutable.ListBuffer
import Block._
import com.github.jarlah.tilegame.objects.SimpleBlock

class BlockMap(path: String) {
  
  val reader = Source.fromURL(getClass.getResource(path)).bufferedReader()
  
  val rows = reader.readLine.toInt
  val cols = reader.readLine.toInt
  
  val blocks:Array[Array[Block]] = Array.ofDim(rows, cols)
  
  for (row <- 0 until rows) yield {
    val line = reader.readLine()
    val tokens = line.split("\\s+")
    for (col <- 0 until cols) yield {
      blocks(row)(col) = Block.fromId(tokens(col).toInt, col * blockSize, row * blockSize)
    }
  }
 
  def draw(g: Graphics2D) {
    for (rowN <- 0 until rows) yield {
      for (colN <- 0 until cols) yield {
        blocks(rowN)(colN).draw(g)
      }
    }
  }
}