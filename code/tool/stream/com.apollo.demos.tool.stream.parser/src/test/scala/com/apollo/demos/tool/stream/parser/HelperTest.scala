package com.apollo.demos.tool.stream.parser

import org.junit.Assert._
import org.junit.Test
import Helper._

class HelperTest extends TestBase {
  @Test def testGetByte_BigEndian() {
    val buf = Array[Byte](0x1, 0x2, 0x3)
    assertEquals(0x1, getByte(BigEndian)(buf, 0))
    assertEquals(0x2, getByte(BigEndian)(buf, 1))
    assertEquals(0x3, getByte(BigEndian)(buf, 2))
  }

  @Test def testGetByte_LittleEndian() {
    val buf = Array[Byte](0x1, 0x2, 0x3)
    assertEquals(0x1, getByte(LittleEndian)(buf, 0))
    assertEquals(0x2, getByte(LittleEndian)(buf, 1))
    assertEquals(0x3, getByte(LittleEndian)(buf, 2))
  }

  @Test def testGetShort_BigEndian() {
    val buf = Array[Byte](0x0, 0x1, 0x0, 0x2, 0x0, 0x3)
    assertEquals(0x1, getShort(BigEndian)(buf, 0))
    assertEquals(0x2, getShort(BigEndian)(buf, 2))
    assertEquals(0x3, getShort(BigEndian)(buf, 4))
  }

  @Test def testGetShort_LittleEndian() {
    val buf = Array[Byte](0x1, 0x0, 0x2, 0x0, 0x3, 0x0)
    assertEquals(0x1, getShort(LittleEndian)(buf, 0))
    assertEquals(0x2, getShort(LittleEndian)(buf, 2))
    assertEquals(0x3, getShort(LittleEndian)(buf, 4))
  }

  @Test def testGetInt_BigEndian() {
    val buf = Array[Byte](0x0, 0x0, 0x0, 0x1, 0x0, 0x0, 0x0, 0x2, 0x0, 0x0, 0x0, 0x3)
    assertEquals(0x1, getInt(BigEndian)(buf, 0))
    assertEquals(0x2, getInt(BigEndian)(buf, 4))
    assertEquals(0x3, getInt(BigEndian)(buf, 8))
  }

  @Test def testGetInt_LittleEndian() {
    val buf = Array[Byte](0x1, 0x0, 0x0, 0x0, 0x2, 0x0, 0x0, 0x0, 0x3, 0x0, 0x0, 0x0)
    assertEquals(0x1, getInt(LittleEndian)(buf, 0))
    assertEquals(0x2, getInt(LittleEndian)(buf, 4))
    assertEquals(0x3, getInt(LittleEndian)(buf, 8))
  }
}
