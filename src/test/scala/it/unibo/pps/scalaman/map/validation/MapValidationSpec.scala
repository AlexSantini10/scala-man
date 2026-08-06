package it.unibo.pps.scalaman.map.validation

import it.unibo.pps.scalaman.model.map.EnemyKind
import it.unibo.pps.scalaman.model.map.MapValidationError
import it.unibo.pps.scalaman.model.map.MapTestSupport
import it.unibo.pps.scalaman.map.parser.MapParser
import org.scalatest.funsuite.AnyFunSuite

class MapValidationSpec extends AnyFunSuite, MapTestSupport:
  private val teleportPairs = List(0 -> 5, 1 -> 6, 2 -> 7, 3 -> 8, 4 -> 9)

  private def teleportMap(start: Int, paired: Int): String =
    mapText(
      "#######",
      s"#S.${start}I.#",
      "#R....#",
      s"#C.${paired}HA#",
      "#######"
    )

  test("accepts a valid playable map") {
    val parsed = MapParser.parse(resourceText("valid/basic-map.txt")).toOption.get
    val validated = MapValidator.validate(parsed)

    assert(validated.isRight)
    val map = validated.toOption.get
    assert(map.spawn.row == 1 && map.spawn.col == 1)
    assert(map.collectibles.nonEmpty)
    assert(map.enemies.exists(_.kind == EnemyKind.Hunter))
    assert(map.enemies.exists(_.kind == EnemyKind.Anticipator))
    assert(map.teleports.contains(0))
  }

  test("accepts every documented teleport pairing") {
    teleportPairs.foreach { case (start, paired) =>
      val parsed = MapParser.parse(teleportMap(start, paired)).toOption.get
      val validated = MapValidator.validate(parsed)

      assert(validated.isRight)
      assert(validated.toOption.get.teleports.contains(start))
    }
  }

  test("rejects maps without a spawn") {
    val parsed = MapParser.parse(resourceText("invalid/missing-spawn.txt")).toOption.get
    val validated = MapValidator.validate(parsed)

    assert(validated.isLeft)
    assert(validated.fold(_.contains(MapValidationError.MissingSpawn), _ => false))
  }

  test("rejects maps with more than one spawn") {
    val parsed = MapParser.parse(
      mapText(
        "#######",
        "#SS0I.#",
        "#R....#",
        "#C.5HA#",
        "#######"
      )
    ).toOption.get
    val validated = MapValidator.validate(parsed)

    assert(validated.isLeft)
    assert(validated.fold(_.contains(MapValidationError.InvalidSpawnCount(2)), _ => false))
  }

  test("rejects maps without collectibles") {
    val parsed = MapParser.parse(resourceText("invalid/missing-collectible.txt")).toOption.get
    val validated = MapValidator.validate(parsed)

    assert(validated.isLeft)
    assert(validated.fold(_.contains(MapValidationError.MissingCollectible), _ => false))
  }

  test("rejects maps without enemies") {
    val parsed = MapParser.parse(resourceText("invalid/missing-enemy.txt")).toOption.get
    val validated = MapValidator.validate(parsed)

    assert(validated.isLeft)
    assert(validated.fold(_.contains(MapValidationError.MissingEnemy), _ => false))
  }

  test("rejects unreachable collectibles") {
    val parsed = MapParser.parse(resourceText("invalid/unreachable-collectible.txt")).toOption.get
    val validated = MapValidator.validate(parsed)

    assert(validated.isLeft)
    assert(validated.fold(_.exists {
      case MapValidationError.UnreachableCollectible(_) => true
      case _ => false
    }, _ => false))
  }

  test("rejects incomplete teleport pairs") {
    val parsed = MapParser.parse(resourceText("invalid/incomplete-teleport.txt")).toOption.get
    val validated = MapValidator.validate(parsed)

    assert(validated.isLeft)
    assert(validated.fold(_.exists {
      case MapValidationError.InvalidTeleportPair(0, 1) => true
      case _ => false
    }, _ => false))
  }

  test("rejects duplicated teleport cells") {
    val parsed = MapParser.parse(
      mapText(
        "#######",
        "#S.0I.#",
        "#R....#",
        "#C.0HA#",
        "#######"
      )
    ).toOption.get
    val validated = MapValidator.validate(parsed)

    assert(validated.isLeft)
    assert(validated.fold(_.exists {
      case MapValidationError.InvalidTeleportPair(0, 2) => true
      case _ => false
    }, _ => false))
  }
end MapValidationSpec
