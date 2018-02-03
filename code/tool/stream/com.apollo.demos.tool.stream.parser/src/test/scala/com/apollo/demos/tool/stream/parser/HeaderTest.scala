package com.apollo.demos.tool.stream.parser

import org.junit.Assert._
import org.junit.Test
import Helper._

class HeaderTest extends TestBase {
  @Test def testParse_0x0_BigEndian() {
    val buf = Array[Byte](0x1, 0x0, 0x0, 0x0, 0x0, 0x0, 0x5, 0x1, 0x2, 0x0, 0x3, 0x4)
    val actual: (Result_Header, Result_Body_0x0) = Header.parse(BigEndian)(buf)

    assertEquals(0x1, actual._1.version)
    assertEquals(0x0, actual._1.commandCode)
    assertEquals(0x5, actual._1.length)
    assertEquals(0x1, actual._2.resv)
    assertEquals(0x2, actual._2.slot)
    assertEquals(0x3, actual._2.boardType)
    assertEquals(0x4, actual._2.boardState)
  }

  @Test def testParse_0x0_LittleEndian() {
    val buf = Array[Byte](0x1, 0x0, 0x0, 0x5, 0x0, 0x0, 0x0, 0x1, 0x2, 0x3, 0x0, 0x4)
    val actual: (Result_Header, Result_Body_0x0) = Header.parse(LittleEndian)(buf)

    assertEquals(0x1, actual._1.version)
    assertEquals(0x0, actual._1.commandCode)
    assertEquals(0x5, actual._1.length)
    assertEquals(0x1, actual._2.resv)
    assertEquals(0x2, actual._2.slot)
    assertEquals(0x3, actual._2.boardType)
    assertEquals(0x4, actual._2.boardState)
  }

  @Test def testParse_0x1_BigEndian() {
    val buf = Array[Byte](0x1, 0x0, 0x1, 0x0, 0x0, 0x0, 0x4, 0x1, 0x2, 0x0, 0x3)
    val actual: (Result_Header, Result_Body_0x1) = Header.parse(BigEndian)(buf)

    assertEquals(0x1, actual._1.version)
    assertEquals(0x1, actual._1.commandCode)
    assertEquals(0x4, actual._1.length)
    assertEquals(0x1, actual._2.resv)
    assertEquals(0x2, actual._2.slot)
    assertEquals(0x3, actual._2.boardType)
  }

  @Test def testParse_0x1_LittleEndian() {
    val buf = Array[Byte](0x1, 0x1, 0x0, 0x4, 0x0, 0x0, 0x0, 0x1, 0x2, 0x3, 0x0)
    val actual: (Result_Header, Result_Body_0x1) = Header.parse(LittleEndian)(buf)

    assertEquals(0x1, actual._1.version)
    assertEquals(0x1, actual._1.commandCode)
    assertEquals(0x4, actual._1.length)
    assertEquals(0x1, actual._2.resv)
    assertEquals(0x2, actual._2.slot)
    assertEquals(0x3, actual._2.boardType)
  }

  @Test def testParse_0x2_BigEndian() {
    val buf = Array[Byte](0x1, 0x0, 0x2, 0x0, 0x0, 0x0, 0x2, 0x1, 0x2)
    val actual: (Result_Header, Result_Body_0x2) = Header.parse(BigEndian)(buf);

    assertEquals(0x1, actual._1.version)
    assertEquals(0x2, actual._1.commandCode)
    assertEquals(0x2, actual._1.length)
    assertEquals(0x1, actual._2.resetType)
    assertEquals(0x2, actual._2.resv)
  }

  @Test def testParse_0x2_LittleEndian() {
    val buf = Array[Byte](0x1, 0x2, 0x0, 0x2, 0x0, 0x0, 0x0, 0x1, 0x2)
    val actual: (Result_Header, Result_Body_0x2) = Header.parse(LittleEndian)(buf);

    assertEquals(0x1, actual._1.version)
    assertEquals(0x2, actual._1.commandCode)
    assertEquals(0x2, actual._1.length)
    assertEquals(0x1, actual._2.resetType)
    assertEquals(0x2, actual._2.resv)
  }
}
