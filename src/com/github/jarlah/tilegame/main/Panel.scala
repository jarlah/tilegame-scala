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

class Panel extends Canvas() with Loop with Settings with KeyListener {
  
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

  override def tick() = gsm.tick
  override def draw(interpolation: Float) = gsm.draw(image.createGraphics, interpolation)

  override def render = {
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