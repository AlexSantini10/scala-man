package it.unibo.pps.scalaman.model

import it.unibo.pps.scalaman.model.Direction.*

import scala.concurrent.duration.FiniteDuration

final case class MovingEntity(
    currentCell: Cell,
    movement: Option[Movement] = None
):

  def move(direction: Direction, timePerCell: FiniteDuration): MovingEntity =
    val to = currentCell.copy(
      x = currentCell.x + direction.dx,
      y = currentCell.y + direction.dy
    )
    copy(movement = Some(Movement(currentCell, to, timePerCell)))
