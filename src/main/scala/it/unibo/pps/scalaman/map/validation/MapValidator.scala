package it.unibo.pps.scalaman.map.validation

import scala.collection.mutable

import it.unibo.pps.scalaman.model.map.Cell
import it.unibo.pps.scalaman.model.map.Enemy
import it.unibo.pps.scalaman.model.map.EnemyKind
import it.unibo.pps.scalaman.model.map.MapValidationError
import it.unibo.pps.scalaman.model.map.Position
import it.unibo.pps.scalaman.model.map.RawMap
import it.unibo.pps.scalaman.model.map.ValidatedMap

object MapValidator:
  def validate(map: RawMap): Either[List[MapValidationError], ValidatedMap] =
    if map.height <= 0 || map.width <= 0 || map.rows.exists(_.length != map.width) then
      Left(List(MapValidationError.InvalidDimensions(map.height, map.width)))
    else
      val spawnPositions = mutable.ListBuffer.empty[Position]
      val collectibles = mutable.Set.empty[Position]
      val enemies = mutable.Set.empty[Enemy]
      val teleportPositions = mutable.Map.empty[Int, mutable.ListBuffer[Position]]
      val errors = mutable.ListBuffer.empty[MapValidationError]

      for
        (row, rowIndex) <- map.rows.zipWithIndex
        (cell, colIndex) <- row.zipWithIndex
      do
        val position = Position(rowIndex, colIndex)
        cell match
          case Cell.Wall | Cell.Floor => ()
          case Cell.Spawn =>
            spawnPositions += position
          case Cell.Collectible =>
            collectibles += position
          case Cell.Hunter =>
            enemies += Enemy(position, EnemyKind.Hunter)
          case Cell.Anticipator =>
            enemies += Enemy(position, EnemyKind.Anticipator)
          case Cell.InvulnerabilityBonus | Cell.SlowdownBonus => ()
          case Cell.Teleport(code) =>
            if code < 0 || code > 9 then errors += MapValidationError.UnsupportedTeleportCode(code)
            else
              val positions = teleportPositions.getOrElseUpdate(code, mutable.ListBuffer.empty)
              positions += position

      if errors.isEmpty then
        spawnPositions.size match
          case 0 => errors += MapValidationError.MissingSpawn
          case 1 => ()
          case count => errors += MapValidationError.InvalidSpawnCount(count)

        if collectibles.isEmpty then errors += MapValidationError.MissingCollectible
        if enemies.isEmpty then errors += MapValidationError.MissingEnemy

        val pairedTeleports = mutable.Map.empty[Int, (Position, Position)]

        for code <- 0 to 4 do
          val startPositions = teleportPositions.get(code).map(_.toVector).getOrElse(Vector.empty)
          val pairedPositions = teleportPositions.get(code + 5).map(_.toVector).getOrElse(Vector.empty)
          val occurrences = startPositions.size + pairedPositions.size

          if occurrences == 0 then ()
          else if startPositions.size == 1 && pairedPositions.size == 1 then
            pairedTeleports += code -> (startPositions.head, pairedPositions.head)
          else
            errors += MapValidationError.InvalidTeleportPair(code, occurrences)

        if errors.isEmpty then
          val reachable = reachablePositions(map, spawnPositions.head, pairedTeleports.toMap)
          val unreachableCollectibles =
            collectibles.iterator.filterNot(reachable.contains).toVector.sortBy(position => (position.row, position.col))

          if unreachableCollectibles.nonEmpty then
            Left(unreachableCollectibles.map(MapValidationError.UnreachableCollectible).toList)
          else
            Right(
              ValidatedMap(
                raw = map,
                spawn = spawnPositions.head,
                collectibles = collectibles.toSet,
                enemies = enemies.toSet,
                teleports = pairedTeleports.toMap
              )
            )
        else Left(errors.toList)
      else Left(errors.toList)

  private def reachablePositions(
      map: RawMap,
      spawn: Position,
      teleports: Map[Int, (Position, Position)]
  ): Set[Position] =
    val teleportLinks = teleports.valuesIterator.flatMap { case (start, paired) =>
      Iterator(start -> paired, paired -> start)
    }.toMap

    val visited = mutable.Set.empty[Position]
    val queue = mutable.Queue.empty[Position]
    visited += spawn
    queue.enqueue(spawn)

    while queue.nonEmpty do
      val current = queue.dequeue()

      for neighbor <- neighbors(map, current, teleportLinks) do
        if visited.add(neighbor) then queue.enqueue(neighbor)

    visited.toSet

  private def neighbors(
      map: RawMap,
      position: Position,
      teleportLinks: Map[Position, Position]
  ): Vector[Position] =
    val adjacent = Vector(
      Position(position.row - 1, position.col),
      Position(position.row + 1, position.col),
      Position(position.row, position.col - 1),
      Position(position.row, position.col + 1)
    ).filter(isWalkable(map, _))

    adjacent ++ teleportLinks.get(position)

  private def isWalkable(map: RawMap, position: Position): Boolean =
    position.row >= 0 &&
    position.row < map.height &&
    position.col >= 0 &&
    position.col < map.width &&
    map.rows(position.row)(position.col) != Cell.Wall
