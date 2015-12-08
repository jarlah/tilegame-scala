package com.github.jarlah.tilegame.main

//remove if not needed
import scala.collection.JavaConversions._

trait Loop extends Runnable {
  val GAME_HERTZ = 60.0
  val TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ
  val MAX_UPDATES_BEFORE_RENDER = 5
  val TARGET_FPS = 120
  val TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS

  var paused = false
  var running = false

  def run() {
    running = true

    var lastUpdateTime = System.nanoTime()
    var lastRenderTime = System.nanoTime()
    var frameCount = 0
    var lastSecondTime = (lastUpdateTime / 1000000000).toInt
    while (running) {
      var now = System.nanoTime()
      var updateCount = 0
      if (!paused) {
        while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
          tick()
          lastUpdateTime += TIME_BETWEEN_UPDATES.toLong
          updateCount += 1
        }
        
        if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
          lastUpdateTime = now - TIME_BETWEEN_UPDATES.toLong
        }
        
        val interpolation = Math.min(1.0f, ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES).toFloat)
        draw(interpolation)
        lastRenderTime = now
        
        render()
        frameCount += 1
        
        val thisSecond = (lastUpdateTime / 1000000000).toInt
        if (thisSecond > lastSecondTime) {
          println("NEW SECOND " + thisSecond + " " + frameCount)
          frameCount = 0
          lastSecondTime = thisSecond
        }
        
        while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
          try {
            Thread.sleep(1)
          } catch {
            case e: Exception => 
          }
          now = System.nanoTime()
        }
      }
    }
  }

  protected def render()
  protected def draw(interpolation: Float)
  protected def tick()
}