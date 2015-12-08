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

class Player(var x: Double, var y: Double, width: Int, height: Int) extends Settings {  
  // Movement
  var right = false
  var left = false
  var jumping = false
  var falling = false
  // Jumping
  var jumpSpeed = 4D
  var currentJumpSpeed = jumpSpeed
  // Falling
  var maxFallSpeed = 5D
  var currentFallSpeed = 0.1D
  
  var topCollision = false
  
  def tick(delta: Double, blocks: Array[Array[Block]]) = {
    blocks.map{ block => 
      block.map { b =>
        if (Collision.playerBlock(new Point((x + width + State.xOffset).toInt, (y + State.yOffset + 2).toInt), b) 
         || Collision.playerBlock(new Point((x + width + State.xOffset).toInt, (y + height + State.yOffset - 1).toInt), b)) {
          right = false;
        }
        
        if (Collision.playerBlock(new Point((x + State.xOffset - 1).toInt, (y + State.yOffset + 2).toInt), b) 
         || Collision.playerBlock(new Point((x + State.xOffset - 1).toInt, (y + height + State.yOffset - 1).toInt), b)) {
          left = false;
        }
        
        if (Collision.playerBlock(new Point((x + State.xOffset + 1).toInt, (y + State.yOffset * delta).toInt), b) 
         || Collision.playerBlock(new Point((x + width + State.xOffset - 1).toInt, (y + State.yOffset).toInt), b)) {
          jumping = false;
          falling = true;
        }
        
        if (Collision.playerBlock(new Point((x + State.xOffset + 2).toInt, (y + height + State.yOffset + 1).toInt), b) 
         || Collision.playerBlock(new Point((x + width + State.xOffset - 1).toInt, (y + height + State.yOffset + 1).toInt), b)) {
          y = b.getY - height - State.yOffset
          falling = false
          topCollision = true
        } else {
          if (!topCollision && !jumping) {
            falling = true;
          }
        }
      }
    }
    
    topCollision = false
    
    if (right) State.xOffset += 1 * delta
    
    if (left) State.xOffset -= 1 * delta
    
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
  }
  
  def draw(g: Graphics2D) = {
   g.setColor(Color.WHITE)
   g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT)
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