package it.unibo.pps.scalaman.model

import scala.concurrent.duration.FiniteDuration

case class Movement(from: Cell, to: Cell, remaining: FiniteDuration)
