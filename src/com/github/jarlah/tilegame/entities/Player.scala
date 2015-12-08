package com.github.jarlah.tilegame.entities

import java.awt.Rectangle
import java.awt.Graphics2D
import java.awt.Color
import com.github.jarlah.tilegame.Settings
import java.awt.event.KeyEvent

class Player(var x: Double, var y: Double, width: Int, height: Int) extends Settings {  
  // Movement
  var right = false
  var left = false
  var jumping = false
  var falling = false
  // Jumping
  var jumpSpeed = 5D
  var currentJumpSpeed = jumpSpeed
  // Falling
  var maxFallSpeed = 5D
  var currentFallSpeed = 0.2D
  // Last drawn position
  var lastDrawX = x
  var lastDrawY = y
  
  def tick = {
    if (right) x += 1
    if (left) x -= 1
    if (jumping) {
      y -= currentJumpSpeed
      currentJumpSpeed -= 0.1
      if (currentJumpSpeed <= 0) {
        currentJumpSpeed = jumpSpeed
        jumping = false
        falling = true
      }
    }
    if (falling) {
      y += currentFallSpeed
      if (currentFallSpeed < maxFallSpeed) {
        currentFallSpeed += 0.1
      }
    }
    if (!falling) {
      currentFallSpeed = 0.1
    }
  }
  
  def draw(g: Graphics2D, interpolation: Float) = {
   g.setColor(Color.WHITE)
   g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT)
   g.setColor(Color.BLACK)
   val drawX = (x - lastDrawX) * interpolation + lastDrawX;
   val drawY = (y - lastDrawY) * interpolation + lastDrawY;
   g.fillRect(drawX.asInstanceOf[Int] , drawY.asInstanceOf[Int], width, height)
   lastDrawX = drawX
   lastDrawY = drawY
  }
  
  def keyPressed(e: Int) = {
    if (e == KeyEvent.VK_RIGHT) right = true
    if (e == KeyEvent.VK_LEFT) left = true
    if (e == KeyEvent.VK_SPACE) jumping = true
  }
  
  def keyReleased(e: Int) = {
    if (e == KeyEvent.VK_RIGHT) right = false
    if (e == KeyEvent.VK_LEFT) left = false
    if (e == KeyEvent.VK_SPACE) {
      jumping = false
      falling = true
    }
  }
}