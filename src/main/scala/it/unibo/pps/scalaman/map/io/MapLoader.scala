package it.unibo.pps.scalaman.map.io

import java.nio.file.Path

import it.unibo.pps.scalaman.model.map.MapLoadError

object MapLoader:
  def load(path: Path): Either[MapLoadError, String] = ???
