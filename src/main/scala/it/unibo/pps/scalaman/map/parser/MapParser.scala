package it.unibo.pps.scalaman.map.parser

import it.unibo.pps.scalaman.model.map.Cell
import it.unibo.pps.scalaman.model.map.MapParseError
import it.unibo.pps.scalaman.model.map.RawMap

object MapParser:
  def parse(text: String): Either[List[MapParseError], RawMap] =
    val rows = text.linesIterator.toVector
    if rows.isEmpty || rows.forall(_.isEmpty) then Left(List(MapParseError.EmptyMap))
    else
      val expectedWidth = rows.head.length
      val errors = collectErrors(rows, expectedWidth)

      if errors.nonEmpty then Left(errors)
      else Right(RawMap(rows.map(row => row.map(char => toCell(char).get).toVector)))

  private def collectErrors(rows: Vector[String], expectedWidth: Int): List[MapParseError] =
    rows.zipWithIndex.flatMap { case (row, rowIndex) =>
      val ragged =
        if row.length != expectedWidth then List(MapParseError.RaggedRow(rowIndex, expectedWidth, row.length))
        else Nil

      val unsupported = row.zipWithIndex.collect {
        case (char, colIndex) if toCell(char).isEmpty =>
          MapParseError.UnsupportedSymbol(char, rowIndex, colIndex)
      }

      ragged ++ unsupported
    }.toList

  private def toCell(char: Char): Option[Cell] =
    char match
      case '#' => Some(Cell.Wall)
      case '.' => Some(Cell.Floor)
      case 'S' => Some(Cell.Spawn)
      case 'C' => Some(Cell.Collectible)
      case 'H' => Some(Cell.Hunter)
      case 'A' => Some(Cell.Anticipator)
      case 'I' => Some(Cell.InvulnerabilityBonus)
      case 'R' => Some(Cell.SlowdownBonus)
      case digit if digit.isDigit => Some(Cell.Teleport(digit.asDigit))
      case _ => None
