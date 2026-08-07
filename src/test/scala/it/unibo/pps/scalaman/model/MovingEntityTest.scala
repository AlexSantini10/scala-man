package it.unibo.pps.scalaman.model

import it.unibo.pps.scalaman.model.Direction.{Down, Left, Right, Up}
import org.scalatest.funsuite.AnyFunSuite

import scala.concurrent.duration.DurationInt

class MovingEntityTest extends AnyFunSuite:

  private val startingCell = Cell(0, 0)
  test("a moving entity that is not moving stays in its starting cell") {
    assert(MovingEntity(startingCell).currentCell == startingCell)
  }

  private val millis = 100.millis
  test(
    "a moving entity given a Movement correctly moves in the 4 directions Up, Down, Left, Right"
  ) {
    val expectedTargets = Seq(
      Down -> Cell(0, +1),
      Up -> Cell(0, -1),
      Left -> Cell(-1, 0),
      Right -> Cell(1, 0)
    )
    expectedTargets
      .foreach { case (direction, target) =>
        val entity = MovingEntity(startingCell).move(direction, millis)
        assert(entity.movement.contains(Movement(startingCell, target, millis)))
      }
  }
