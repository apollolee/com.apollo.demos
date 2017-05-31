package com.apollo.demos.algorithm.dijkstra

import scala.collection.mutable.HashSet

object Dijkstra {
  def dijkstra(graph: List[Node], start: Node) {
    val remained = HashSet(graph: _*)

    start.cost = 0
    start.path = start :: Nil

    while (!remained.isEmpty) {
      val current = remained.minBy(_.cost)
      current.links.foreach(_.updateOther(current))
      remained.remove(current)
    }
  }
}
