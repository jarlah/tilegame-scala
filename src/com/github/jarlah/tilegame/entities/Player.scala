package com.github.jarlah.tilegame.entities

import java.awt.Rectangle
import java.awt.Graphics2D
import java.awt.Color
import com.github.jarlah.tilegame.Settings
import java.awt.event.KeyEvent
import com.github.jarlah.tilegame.state.StateManager
import com.github.jarlah.tilegame.objects.Block
import com.github.jarlah.tilegame.physics.Collision
import java.awt.Point
import com.github.jarlah.tilegame.state.State
import com.github.jarlah.tilegame.state.State._

class Player(var x: Double, var y: Double, width: Int, height: Int) extends Settings {  
  // Movement
  var right = false
  var left = false
  var jumping = false
  var falling = false
  // Move
  var moveSpeed = 2.5
  // Jumping
  var jumpSpeed = 4D
  var currentJumpSpeed = jumpSpeed
  // Falling
  var maxFallSpeed = 5D
  var currentFallSpeed = 0.1D
  
  var topCollision = false
  
  var rightTopTile: Point =    null
  var rightBottomTile: Point = null
  var leftTopTile: Point =     null
  var leftBottomTile: Point =  null
  var topLeftTile: Point = 	  null
  var topRightTile: Point = 	  null
  var bottomLeftTile: Point =  null
  var bottomRightTile: Point = null
  
  val padding = 2
  
  def tick(delta: Double, blocks: Array[Array[Block]]) = {
    rightTopTile =        new Point((x + xOffset + width + 2).toInt,     (y + yOffset + 2).toInt)
    rightBottomTile =     new Point((x + xOffset + width + 2).toInt,     (y + yOffset + height - 1).toInt)
    
    leftTopTile =         new Point((x + xOffset - 4).toInt,             (y + yOffset + 2).toInt)
    leftBottomTile =      new Point((x + xOffset - 1).toInt,             (y + yOffset + height - 1).toInt)
    
    topLeftTile = 	      new Point((x + xOffset + 1).toInt,             (y + yOffset - 4).toInt)
    topRightTile = 	      new Point((x + xOffset + width - 1).toInt,     (y + yOffset - 4).toInt)
    
    bottomLeftTile =      new Point((x + xOffset + 2).toInt,             (y + yOffset + height + 1).toInt)
    bottomRightTile =     new Point((x + xOffset + width - 1).toInt,     (y + yOffset + height + 1).toInt)
    
    blocks.map { block =>
      block.map { b =>
        if (b.blocking) {
          if (b.contains(rightTopTile) || b.contains(rightBottomTile)) {
            right = false;
          }
          if (b.contains(leftTopTile) || b.contains(leftBottomTile)) {
            left = false;
          }
          if (b.contains(topLeftTile) || b.contains(topRightTile)) {
            jumping = false;
            falling = true;
          }
          if (b.contains(bottomLeftTile) || b.contains(bottomRightTile)) {
        	   y = b.getY - height - State.yOffset
        	  falling = false
            topCollision = true
          }
        } else {
          if (!topCollision && !jumping) {
            falling = true;
            jumping = false
          }
        }
      }
    }
    
    topCollision = false
    
    if (right) State.xOffset += moveSpeed * delta

    if (left) State.xOffset -= moveSpeed * delta
    
    if (jumping) {
      State.yOffset -= currentJumpSpeed * delta
      currentJumpSpeed -= 0.1D
      if (currentJumpSpeed <= 0) {
        currentJumpSpeed = jumpSpeed
        jumping = false
        falling = true
      }
    }
    
    if (falling) {
      State.yOffset += currentFallSpeed * delta
      if (currentFallSpeed < maxFallSpeed) {
        currentFallSpeed += 0.1
      }
    }
    
    if (!falling) {
      currentFallSpeed = 0.1
    }
    
    if (!jumping) {
      currentJumpSpeed = jumpSpeed
    }
  }
  
  def draw(g: Graphics2D) = {
   g.setColor(Color.BLACK)
   g.fillRect(x.asInstanceOf[Int], y.asInstanceOf[Int], width, height)
   g.setColor(Color.RED)
   g.fillRect(rightTopTile.x, rightTopTile.y, width, height)
   g.fillRect(rightBottomTile.x, rightBottomTile.y, width, height)
   g.fillRect(leftTopTile.x, leftTopTile.y, width, height)
   g.fillRect(leftBottomTile.x, leftBottomTile.y, width, height)
   g.fillRect(topLeftTile.x, topLeftTile.y, width, height)
   g.fillRect(topRightTile.x, topRightTile.y, width, height)
   g.fillRect(bottomLeftTile.x, bottomLeftTile.y, width, height)
   g.fillRect(bottomRightTile.x, bottomRightTile.y, width, height)
  }
  
  def keyPressed(e: Int) = {
    if (e == KeyEvent.VK_RIGHT) right = true
    if (e == KeyEvent.VK_LEFT) left = true
    if (e == KeyEvent.VK_SPACE && !jumping && !falling) jumping = true
  }
  
  def keyReleased(e: Int) = {
    if (e == KeyEvent.VK_RIGHT) right = false
    if (e == KeyEvent.VK_LEFT) left = false
  }
}