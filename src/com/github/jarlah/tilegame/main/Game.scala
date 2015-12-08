package com.github.jarlah.tilegame.main

import javax.swing.JFrame
import com.github.jarlah.tilegame.Settings

object Game extends App with Settings {
  val frame = new JFrame(GAME_TITLE);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.add(new Panel())
  frame.setResizable(false)
  frame.pack()
  frame.setVisible(true);
	frame.setLocationRelativeTo(null);
}