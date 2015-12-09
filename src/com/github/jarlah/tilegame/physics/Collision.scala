package com.github.jarlah.tilegame.physics

import java.awt.Point
import com.github.jarlah.tilegame.objects.Block

object Collision {
  def playerBlock(p: Point, b: Block) = b.blocking && b.contains(p)
}