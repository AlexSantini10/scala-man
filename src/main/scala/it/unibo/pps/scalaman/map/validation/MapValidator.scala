package it.unibo.pps.scalaman.map.validation

import it.unibo.pps.scalaman.model.map.MapValidationError
import it.unibo.pps.scalaman.model.map.RawMap
import it.unibo.pps.scalaman.model.map.ValidatedMap

object MapValidator:
  def validate(map: RawMap): Either[List[MapValidationError], ValidatedMap] = ???
