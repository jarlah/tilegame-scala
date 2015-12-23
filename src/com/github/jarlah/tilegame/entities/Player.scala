package com.github.jarlah.tilegame.entities

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.event.KeyEvent
import com.github.jarlah.tilegame.Settings
import com.github.jarlah.tilegame.map.Block
import com.github.jarlah.tilegame.state.State.xOffset
import com.github.jarlah.tilegame.state.State.yOffset
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class Player(var x: Double,
             var y: Double,
             val width: Int,
             val height: Int,
             // Directions
             var right: Boolean = false,
             var left: Boolean = false,
             var jumping: Boolean = false,
             var falling: Boolean = false,
             // Speed
             var moveSpeed: Double = 2.5,
             // Jumping
             var jumpSpeed: Double = 4.5D,
             var currentJumpSpeed: Double = 4.6D,
             // Falling
             var maxFallSpeed: Double = 6D,
             var currentFallSpeed: Double = 0.1D,
             // Collision
             var floorCollision: Boolean = false,
             var rightTopTile: Point = null,
             var rightBottomTile: Point = null,
             var leftTopTile: Point = null,
             var leftBottomTile: Point = null,
             var topLeftTile: Point = null,
             var topRightTile: Point = null,
             var bottomLeftTile: Point = null,
             var bottomRightTile: Point = null) extends Settings {  

  var walkingAnimation: Animation = new Animation()
  val walkingArr: Array[BufferedImage] = Array(
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_walk/PNG/p1_walk01.png")),
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_walk/PNG/p1_walk02.png")),
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_walk/PNG/p1_walk03.png")),
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_walk/PNG/p1_walk04.png")),
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_walk/PNG/p1_walk05.png")),
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_walk/PNG/p1_walk06.png")),
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_walk/PNG/p1_walk07.png")),
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_walk/PNG/p1_walk08.png")),
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_walk/PNG/p1_walk09.png")),
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_walk/PNG/p1_walk10.png")),
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_walk/PNG/p1_walk11.png"))
  )
  walkingAnimation.setFrames(walkingArr, Block.blockSize, Block.blockSize)
  walkingAnimation.setDelay(70)
  
  var idleAnimation: Animation = new Animation()
  val idleArr: Array[BufferedImage] = Array(
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_front.png"))
  )
  idleAnimation.setFrames(idleArr, Block.blockSize, Block.blockSize)
  idleAnimation.setDelay(70)
  
  var jumpingAnimation: Animation = new Animation()
  val jumpingArr: Array[BufferedImage] = Array(
    ImageIO.read(this.getClass.getClassLoader.getResourceAsStream("Player/p1_jump.png"))
  )
  jumpingAnimation.setFrames(jumpingArr, Block.blockSize, Block.blockSize)
  jumpingAnimation.setDelay(70)
  
  var currentAnimation: Animation = idleAnimation
  
  def tick(delta: Double, blocks: Array[Array[Block]]) = {
    currentAnimation.update
    
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
    
    if (goRight) x += moveSpeed * delta
    if (goLeft) x -= moveSpeed * delta
    
    if (jumping) {
      y -= currentJumpSpeed * delta
      currentJumpSpeed -= 0.1D
      if (currentJumpSpeed <= 0) {
        currentJumpSpeed = jumpSpeed
        falling = true
        jumping = false
      }
    }
    
    if (falling) {
      y += currentFallSpeed * delta
      if (currentFallSpeed < maxFallSpeed) {
        currentFallSpeed += 0.1
      }
    }
    
    if (!falling) currentFallSpeed = 0.1
    if (!jumping) currentJumpSpeed = jumpSpeed
    
    if (jumping || falling) currentAnimation = jumpingAnimation
    else if(left || right) currentAnimation = walkingAnimation
    else currentAnimation = idleAnimation
  }
  
  def draw(g: Graphics2D) = {
    if (left) {
      g.drawImage(currentAnimation.getImage, x.asInstanceOf[Int] + width, y.asInstanceOf[Int], -width, height, null)
    } else {
      g.drawImage(currentAnimation.getImage, x.asInstanceOf[Int], y.asInstanceOf[Int], width, height, null)
    }
  }
  
  def keyPressed(e: Int) = {
    if (e == KeyEvent.VK_RIGHT) {
      right = true
    }
    if (e == KeyEvent.VK_LEFT) {
      left = true
    }
    if (e == KeyEvent.VK_SPACE && !jumping && !falling) {
      jumping = true
    }
  }
  
  def keyReleased(e: Int) = {
    if (e == KeyEvent.VK_RIGHT) {
      right = false
    }
    if (e == KeyEvent.VK_LEFT) {
      left = false
    }
  }
}