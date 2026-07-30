package it.unibo.pps.scalaman

import org.junit.Assert.*
import org.junit.Test

class MainTest:

  @Test
  def testApplicationName =
    assertEquals("scala-man", applicationName)
