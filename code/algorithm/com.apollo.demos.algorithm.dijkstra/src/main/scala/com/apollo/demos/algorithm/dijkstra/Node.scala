package com.apollo.demos.algorithm.dijkstra

import scala.collection.mutable.HashSet

class Node(val id: String) {
  var cost = Int.MaxValue
  var path: List[Node] = Nil

  val links = new HashSet[Link]

  class Mid(val a: Node, val weight: Int) { def ->(z: Node): Node = { Link(a, z, weight); z } }
  def -(weight: Int): Mid = new Mid(this, weight)

  override def toString = "[" + id + "][" + links.size + ":" + links.mkString("[", "][", "]") + "][" + cost + "][" + path.map(_.id).mkString("->") + "]"
}

object Node {
  def apply(id: String) = new Node(id)
}
