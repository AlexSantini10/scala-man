package it.unibo.pps.scalaman

import org.scalatest.funsuite.AnyFunSuite

class MainTest extends AnyFunSuite:
  test("application name is scala-man") {
    assert(applicationName == "scala-man")
  }
