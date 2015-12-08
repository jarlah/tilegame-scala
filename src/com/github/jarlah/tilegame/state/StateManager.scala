package com.github.jarlah.tilegame.state

import java.util.Stack
import java.awt.Graphics2D

class StateManager {
  val states: Stack[State] = new Stack
  def tick(delta: Double) = states.peek.tick(delta)
  def draw(g: Graphics2D) = states.peek.draw(g)
  def keyPressed(e: Int) = states.peek.keyPressed(e)
  def keyReleased(e: Int) = states.peek.keyReleased(e)
}