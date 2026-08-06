package it.unibo.pps.scalaman.map.parser

import it.unibo.pps.scalaman.model.map.MapParseError
import it.unibo.pps.scalaman.model.map.RawMap

object MapParser:
  def parse(text: String): Either[List[MapParseError], RawMap] = ???
