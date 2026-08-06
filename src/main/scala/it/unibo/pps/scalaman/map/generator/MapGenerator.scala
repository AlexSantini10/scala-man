package it.unibo.pps.scalaman.map.generator

import it.unibo.pps.scalaman.model.map.MapGenerationError
import it.unibo.pps.scalaman.model.map.MapGenerationSpec
import it.unibo.pps.scalaman.model.map.RawMap

object MapGenerator:
  def generate(spec: MapGenerationSpec): Either[List[MapGenerationError], RawMap] = ???
