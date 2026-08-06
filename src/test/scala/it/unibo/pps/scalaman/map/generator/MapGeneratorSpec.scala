package it.unibo.pps.scalaman.map.generator

import it.unibo.pps.scalaman.model.map.MapGenerationError
import it.unibo.pps.scalaman.model.map.MapGenerationSpec
import it.unibo.pps.scalaman.model.map.MapTestSupport
import it.unibo.pps.scalaman.map.validation.MapValidator
import org.scalatest.funsuite.AnyFunSuite

class MapGeneratorSpec extends AnyFunSuite, MapTestSupport:
  test("generates a map that passes validation") {
    val generated = MapGenerator.generate(
      MapGenerationSpec(width = 7, height = 5, collectibles = 1, teleports = 1, enemies = 1, seed = Some(42L))
    )

    assert(generated.isRight)
    val parsed = generated.toOption.get
    assert(parsed.width == 7)
    assert(parsed.height == 5)
    assert(MapValidator.validate(parsed).isRight)
  }

  test("rejects invalid generation specifications") {
    val invalidSpecs = List(
      MapGenerationSpec(width = 0, height = 5, collectibles = 1, teleports = 1, enemies = 1),
      MapGenerationSpec(width = 5, height = 0, collectibles = 1, teleports = 1, enemies = 1),
      MapGenerationSpec(width = 5, height = 5, collectibles = -1, teleports = 1, enemies = 1),
      MapGenerationSpec(width = 5, height = 5, collectibles = 1, teleports = -1, enemies = 1),
      MapGenerationSpec(width = 5, height = 5, collectibles = 1, teleports = 1, enemies = 0)
    )

    invalidSpecs.foreach { spec =>
      val generated = MapGenerator.generate(spec)

      assert(generated.isLeft)
      assert(generated.fold(_.exists {
        case MapGenerationError.InvalidSpecification(_) => true
        case _ => false
      }, _ => false))
    }
  }
end MapGeneratorSpec
