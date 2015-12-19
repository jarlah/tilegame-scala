package com.github.jarlah.tilegame.state

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.event.KeyEvent

class MenuState(gsm: StateManager) extends State(gsm) {
  val options: Array[String] = Array("Start", "Help", "Quit")
  
  var current = 0
  
  def init = Nil
  
  def draw(g: Graphics2D): Unit = {
    g.setColor(new Color(48,146,233))
    g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT)
    for (i <- options.indices) yield {
      if (i == current) {
        g.setColor(Color.YELLOW) 
      } else {
        g.setColor(Color.DARK_GRAY)
      }
      g.setFont(new Font("Arial", Font.BOLD, 20))
      g.drawString(options(i), (GAME_WIDTH - 110) / 2, 150 + i * 30)
    }
  }
  
  def keyPressed(e: Int): Unit = {
    if (e == KeyEvent.VK_DOWN) {
      current = current + 1
      if (current >= options.length) {
       current = 0  
      }
    } 
    if (e == KeyEvent.VK_UP){
      current = current - 1
      if (current < 0) {
       current = options.length - 1  
      }
    }
    if (e == KeyEvent.VK_ENTER) {
      if (current == 0) {
        gsm.states.push(new Level1State(gsm))
      }
      if (current == 1) {
        // Help
      }
      if (current == 2) {
        System.exit(0)
      }
    }
  }
  
  def keyReleased(e: Int): Unit = Nil
  
  def tick(delta: Double): Unit = Nil
}