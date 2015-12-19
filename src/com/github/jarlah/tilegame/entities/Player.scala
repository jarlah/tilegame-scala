package com.github.jarlah.tilegame.entities

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.event.KeyEvent

import com.github.jarlah.tilegame.Settings
import com.github.jarlah.tilegame.objects.Block
import com.github.jarlah.tilegame.state.State
import com.github.jarlah.tilegame.state.State.xOffset
import com.github.jarlah.tilegame.state.State.yOffset

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
    
    // We want to keep going left or right for ex when we jump 
    var goLeft = left
    var goRight = right
    
    blocks.map { block =>
      block.map { b =>
        if (b.blocking) {
          if (b.contains(rightTopTile) || b.contains(rightBottomTile)) {
            goRight = false;
          }
          if (b.contains(leftTopTile) || b.contains(leftBottomTile)) {
            goLeft = false;
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
    
    if (goRight) State.xOffset += moveSpeed * delta

    if (goLeft) State.xOffset -= moveSpeed * delta
    
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
   g.setColor(Color.BLUE)
   g.fillRect(rightTopTile.x - State.xOffset.toInt, rightTopTile.y - State.yOffset.toInt, 3, 3)
   g.setColor(Color.BLUE)
   g.fillRect(rightBottomTile.x - State.xOffset.toInt, rightBottomTile.y - State.yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(leftTopTile.x - State.xOffset.toInt, leftTopTile.y - State.yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(leftBottomTile.x - State.xOffset.toInt, leftBottomTile.y - State.yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(topLeftTile.x - State.xOffset.toInt, topLeftTile.y - State.yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(topRightTile.x - State.xOffset.toInt, topRightTile.y - State.yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(bottomLeftTile.x - State.xOffset.toInt, bottomLeftTile.y - State.yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(bottomRightTile.x - State.xOffset.toInt, bottomRightTile.y - State.yOffset.toInt, 3, 3)
   g.setColor(Color.BLACK)
   g.fillRect(x.asInstanceOf[Int], y.asInstanceOf[Int], width, height)
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