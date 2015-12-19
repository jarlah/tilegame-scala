package com.github.jarlah.tilegame.entities

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.event.KeyEvent

import com.github.jarlah.tilegame.Settings
import com.github.jarlah.tilegame.objects.Block
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
  // Collision
  var floorCollision = false
  var rightTopTile: Point =    null
  var rightBottomTile: Point = null
  var leftTopTile: Point =     null
  var leftBottomTile: Point =  null
  var topLeftTile: Point = 	  null
  var topRightTile: Point = 	  null
  var bottomLeftTile: Point =  null
  var bottomRightTile: Point = null
  
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
    // Therefore we manipulate these local variables as copies of the original
    var goLeft = left
    var goRight = right
    
    blocks.map { block =>
      block.map { b =>
        if (b.blocking) {
          if (b.contains(rightTopTile) || b.contains(rightBottomTile)) {
            println("Right collision")
            goRight = false;
          }
          if (b.contains(leftTopTile) || b.contains(leftBottomTile)) {
            println("Left collision")
            goLeft = false;
          }
          if (b.contains(topLeftTile) || b.contains(topRightTile)) {
            println("Top collision")
            jumping = false;
            falling = true;
          }
          if (b.contains(bottomLeftTile) || b.contains(bottomRightTile)) {
            println("Floor collision")
        	  y = b.getY - height - yOffset
        	  falling = false
            floorCollision = true
          }
        } else {
          if (!floorCollision && !jumping) {
            falling = true;
            jumping = false
          }
        }
      }
    }
    
    floorCollision = false
    
    if (goRight) xOffset += moveSpeed * delta
    if (goLeft) xOffset -= moveSpeed * delta
    
    if (jumping) {
      yOffset -= currentJumpSpeed * delta
      currentJumpSpeed -= 0.1D
      if (currentJumpSpeed <= 0) {
        currentJumpSpeed = jumpSpeed
        falling = true
        jumping = false
      }
    }
    
    if (falling) {
      yOffset += currentFallSpeed * delta
      if (currentFallSpeed < maxFallSpeed) {
        currentFallSpeed += 0.1
      }
    }
    
    if (!falling) currentFallSpeed = 0.1
    if (!jumping) currentJumpSpeed = jumpSpeed
  }
  
  def draw(g: Graphics2D) = {
   g.setColor(Color.BLUE)
   g.fillRect(rightTopTile.x - xOffset.toInt, rightTopTile.y - yOffset.toInt, 3, 3)
   g.setColor(Color.BLUE)
   g.fillRect(rightBottomTile.x - xOffset.toInt, rightBottomTile.y - yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(leftTopTile.x - xOffset.toInt, leftTopTile.y - yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(leftBottomTile.x - xOffset.toInt, leftBottomTile.y - yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(topLeftTile.x - xOffset.toInt, topLeftTile.y - yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(topRightTile.x - xOffset.toInt, topRightTile.y - yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(bottomLeftTile.x - xOffset.toInt, bottomLeftTile.y - yOffset.toInt, 3, 3)
   g.setColor(Color.RED)
   g.fillRect(bottomRightTile.x - xOffset.toInt, bottomRightTile.y - yOffset.toInt, 3, 3)
   g.setColor(Color.CYAN)
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