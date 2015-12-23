package com.github.jarlah.tilegame.map

import java.awt.Rectangle
import java.awt.Graphics2D

import Block._
import com.github.jarlah.tilegame.objects.GrassCenter
import com.github.jarlah.tilegame.objects.GrassHalf
import com.github.jarlah.tilegame.objects.GrassMid
import com.github.jarlah.tilegame.objects.LiquidLava
import com.github.jarlah.tilegame.objects.SimpleBlock

object Block {
  val EMPTY = 0
  val blockSize: Int = 40
  def fromId(id: Int, x: Int, y: Int): Block = {
    id match {
      case 1 => new GrassMid(x, y)
      case 2 => new GrassCenter(x, y)
      case 3 => new GrassHalf(x, y)
      case 4 => new LiquidLava(x, y)
      case _ => new SimpleBlock(x, y, id)
    }
  }
}

class Block(x: Int, y: Int) extends Rectangle(x, y, blockSize, blockSize) {
  def draw(g: Graphics2D): Unit = ???
  
  def blocking: Boolean = true
}

