package com.apollo.demos.algorithm.dijkstra

import org.junit.Test

class DijkstraTest extends TestBase {
  @Test def testBase() {
    val a = Node("a")
    val b = Node("b")
    val c = Node("c")
    val d = Node("d")
    val e = Node("e")
    val f = Node("f")

    a - 7 -> b - 10 -> c - 11 -> d - 6 -> e - 9 -> f
    a - 9 -> c - 2 -> f
    a - 14 -> f
    b - 15 -> d
    val graph = a :: b :: c :: d :: e :: f :: Nil

    showDijkstra(graph, a)
  }

  private def showDijkstra(graph: List[Node], start: Node) {
    println("原始图：")
    graph.foreach(println _)

    println("\n开始Dijkstra算法：")
    val startTime = System.currentTimeMillis
    Dijkstra.dijkstra(graph, start)
    println("结束Dijkstra算法，耗时：" + (System.currentTimeMillis - startTime))

    println("\n计算后的结果：")
    graph.foreach(println _)
  }
}
