package com.github.jarlah.tilegame.state

import java.util.Stack
import java.awt.Graphics2D

class StateManager {
  val states: Stack[State] = new Stack
  def tick = states.peek.tick
  def draw(g: Graphics2D, interpolation: Float) = states.peek.draw(g, interpolation)
  def keyPressed(e: Int) = states.peek.keyPressed(e)
  def keyReleased(e: Int) = states.peek.keyReleased(e)
}