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

class Panel(gsm: StateManager = new StateManager) extends Canvas() with Settings with Runnable with KeyListener {
  setPreferredSize(GAME_DIMENSIONS)
  setMaximumSize(GAME_DIMENSIONS)
  setMinimumSize(GAME_DIMENSIONS)
  setFocusable(true)
  setIgnoreRepaint(true)
  addKeyListener(this)

  val drawingBoard = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
  
  override def addNotify = {
    super.addNotify()
    createBufferStrategy(3)
    gsm.states.push(new MenuState(gsm))
    val thread = new Thread(this)
    thread.start()
    requestFocus
  }

  def run = {
    var running = true
    
    var frames = 0
    var lastFpsTime = 0L
    var lastLoopTime = System.nanoTime
    
    val TARGET_FPS: Int = 60
    val OPTIMAL_TIME: Long = 1000000000 / TARGET_FPS

    while (running) {
      var now = System.nanoTime
      var updateLength = now - lastLoopTime
      lastLoopTime = now
      var delta = updateLength / OPTIMAL_TIME.asInstanceOf[Double]
      lastFpsTime += updateLength
      frames += 1

      if (lastFpsTime >= 1000000000) {
        lastFpsTime = 0
        frames = 0
      }

      tick(delta)
      draw
      render

      val waitMs = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000
      Try { Thread.sleep(waitMs) }
    }
  }

  def tick(delta: Double) = gsm.tick(delta)
  
  def draw = {
    val g = drawingBoard.createGraphics
    g.setColor(Color.WHITE)
    g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT)
    gsm.draw(g)
  }

  def render = {
    val bs = getBufferStrategy
    val g2d = bs.getDrawGraphics.asInstanceOf[Graphics2D]
    g2d.drawImage(drawingBoard, 0, 0, null)
    Toolkit.getDefaultToolkit.sync
    g2d.dispose
    bs.show
  }

  def keyPressed(e: KeyEvent) = gsm.keyPressed(e.getKeyCode)
  
  def keyReleased(e: KeyEvent) = gsm.keyReleased(e.getKeyCode)
  
  def keyTyped(e: KeyEvent) = Nil
}