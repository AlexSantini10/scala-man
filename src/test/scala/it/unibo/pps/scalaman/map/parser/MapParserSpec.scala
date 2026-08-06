package it.unibo.pps.scalaman.map.parser

import it.unibo.pps.scalaman.model.map.Cell
import it.unibo.pps.scalaman.model.map.MapParseError
import it.unibo.pps.scalaman.model.map.MapTestSupport
import org.scalatest.funsuite.AnyFunSuite

class MapParserSpec extends AnyFunSuite, MapTestSupport:
  private val teleportPairs = List(0 -> 5, 1 -> 6, 2 -> 7, 3 -> 8, 4 -> 9)

  private def teleportMap(start: Int, paired: Int): String =
    mapText(
      "#######",
      s"#S.${start}I.#",
      "#R....#",
      s"#C.${paired}HA#",
      "#######"
    )

  test("parses a valid rectangular map with spawn, collectible, enemies, bonuses, and a teleport pair") {
    val parsed = MapParser.parse(resourceText("valid/basic-map.txt"))

    assert(parsed.isRight)
    val map = parsed.toOption.get
    assert(map.height == 5)
    assert(map.width == 7)
    assert(map.rows.flatten.contains(Cell.Spawn))
    assert(map.rows.flatten.contains(Cell.Collectible))
    assert(map.rows.flatten.contains(Cell.Hunter))
    assert(map.rows.flatten.contains(Cell.Anticipator))
    assert(map.rows.flatten.contains(Cell.InvulnerabilityBonus))
    assert(map.rows.flatten.contains(Cell.SlowdownBonus))
    assert(map.rows.flatten.exists {
      case Cell.Teleport(0) => true
      case Cell.Teleport(5) => true
      case _ => false
    })
  }

  test("parses every documented teleport pairing") {
    teleportPairs.foreach { case (start, paired) =>
      val parsed = MapParser.parse(teleportMap(start, paired))

      assert(parsed.isRight)
      val map = parsed.toOption.get
      assert(map.rows.flatten.exists(_ == Cell.Teleport(start)))
      assert(map.rows.flatten.exists(_ == Cell.Teleport(paired)))
    }
  }

  test("rejects empty maps") {
    val parsed = MapParser.parse("")

    assert(parsed == Left(List(MapParseError.EmptyMap)))
  }

  test("rejects malformed maps with ragged rows") {
    val parsed = MapParser.parse(resourceText("invalid/ragged-map.txt"))

    assert(parsed.isLeft)
    assert(parsed.fold(_.exists {
      case MapParseError.RaggedRow(_, _, _) => true
      case _ => false
    }, _ => false))
  }

  test("rejects unsupported symbols") {
    val parsed = MapParser.parse(resourceText("invalid/unsupported-symbol.txt"))

    assert(parsed.isLeft)
    assert(parsed.fold(_.exists {
      case MapParseError.UnsupportedSymbol(_, _, _) => true
      case _ => false
    }, _ => false))
  }
end MapParserSpec
