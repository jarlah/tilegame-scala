package com.github.jarlah.tilegame.state

import java.awt.Graphics2D
import com.github.jarlah.tilegame.Settings

abstract class State(gsm: StateManager) extends Settings {
  init
  
  def init: Unit
  def tick: Unit
  def draw(g: Graphics2D, interpolation: Float): Unit
  def keyPressed(e: Int): Unit
  def keyReleased(e: Int): Unit
}