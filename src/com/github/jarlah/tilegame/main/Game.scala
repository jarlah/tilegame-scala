package com.github.jarlah.tilegame.main

import javax.swing.JFrame
import com.github.jarlah.tilegame.Settings

object Game extends App with Settings {
  val frame = new JFrame(GAME_TITLE);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.setResizable(false)
  frame.setVisible(true) 
  val panel = new Panel
  frame.add(panel)
  panel.createBufferStrategy(3)
  frame.pack
  frame.setLocationRelativeTo(null)
  panel.requestFocus
}