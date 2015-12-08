package com.github.jarlah.tilegame.main

import java.awt.Canvas
import java.awt.Graphics2D
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.image.BufferedImage
import com.github.jarlah.tilegame.state.MenuState
import com.github.jarlah.tilegame.state.StateManager
import com.github.jarlah.tilegame.Settings
import java.awt.Color
import scala.util.Try

class Panel extends Canvas() with Settings with Runnable with KeyListener {
  var running = false

  val fps = 60
  val targetTime = 1000 / fps
  val gsm = new StateManager
  val image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);

  setPreferredSize(GAME_DIMENSIONS)
  setMaximumSize(GAME_DIMENSIONS)
  setMinimumSize(GAME_DIMENSIONS)
  setFocusable(true);
  requestFocus

  addKeyListener(this)

  gsm.states.push(new MenuState(gsm))
  val thread = new Thread(this)
  thread.start()

  def run = {
    running = true
    
    var frames = 0
    var lastFpsTime = 0L
    var lastLoopTime = System.nanoTime
    
    val TARGET_FPS: Int = 60
    val OPTIMAL_TIME: Long = 1000000000 / TARGET_FPS

    while (running) {
      // work out how long its been since the last update, this
      // will be used to calculate how far the entities should
      // move this loop
      var now = System.nanoTime
      var updateLength = now - lastLoopTime
      lastLoopTime = now
      var delta = updateLength / OPTIMAL_TIME.asInstanceOf[Double]

      // update the frame counter
      lastFpsTime += updateLength
      frames += 1

      // update our FPS counter if a second has passed since we last recorded
      if (lastFpsTime >= 1000000000) {
        System.out.println("(TIME: " + lastFpsTime + " / FPS: " + frames + ")")
        lastFpsTime = 0
        frames = 0
      }

      tick(delta)
      draw
      render
      
      // we want each frame to take 10 milliseconds, to do this
      // we've recorded when we started the frame. We add 10 milliseconds
      // to this and then factor in the current time to give 
      // us our final value to wait for
      // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
      val waitMs = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000
      
      Try { Thread.sleep(waitMs) }
    }
  }

  def tick(delta: Double) = gsm.tick(delta)
  def draw = gsm.draw(image.createGraphics)

  def render = {
    val bs = getBufferStrategy
    if (bs == null) {
      createBufferStrategy(3)
    } else {
      val g2d = bs.getDrawGraphics.asInstanceOf[Graphics2D]
      g2d.drawImage(image, 0, 0, null)
      Toolkit.getDefaultToolkit.sync
      g2d.dispose
      bs.show
    }
  }

  def keyPressed(e: KeyEvent) = gsm.keyPressed(e.getKeyCode)
  def keyReleased(e: KeyEvent) = gsm.keyReleased(e.getKeyCode)
  def keyTyped(e: KeyEvent) = Nil
}