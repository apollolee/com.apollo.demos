package com.apollo.demos.algorithm.dijkstra

class Link(val a: Node, val z: Node, var weight: Int) {
  a.links.add(this)
  z.links.add(this)

  override def toString = a.id + "-" + weight + "->" + z.id

  def otherNode(current: Node) = if (a eq current) z else a

  def updateOther(current: Node) {
    val other = otherNode(current)
    val newCost = current.cost + weight
    if (newCost < other.cost) {
      other.cost = newCost
      other.path = other :: current.path
    }
  }
}

object Link {
  def apply(a: Node, z: Node, weight: Int): Link = new Link(a, z, weight: Int)
}
