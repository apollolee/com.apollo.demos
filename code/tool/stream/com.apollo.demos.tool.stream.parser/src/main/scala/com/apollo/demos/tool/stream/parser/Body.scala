package com.apollo.demos.tool.stream.parser

import java.nio.ByteOrder

import Helper._

sealed abstract class Body[T](val commandCode: Int) {
  def decode(bo: ByteOrder)(buf: Array[Byte], length: Int): T
}

class Body_0x0 extends Body[Result_Body_0x0](0x0) {
  def decode(bo: ByteOrder)(buf: Array[Byte], length: Int) = Result_Body_0x0(getByte(bo)(buf, 7), getByte(bo)(buf, 8), getShort(bo)(buf, 9), getByte(bo)(buf, 11))
}

class Body_0x1 extends Body[Result_Body_0x1](0x1) {
  def decode(bo: ByteOrder)(buf: Array[Byte], length: Int) = Result_Body_0x1(getByte(bo)(buf, 7), getByte(bo)(buf, 8), getShort(bo)(buf, 9))
}

class Body_0x2 extends Body[Result_Body_0x2](0x2) {
  def decode(bo: ByteOrder)(buf: Array[Byte], length: Int) = Result_Body_0x2(getByte(bo)(buf, 7), getByte(bo)(buf, 8))
}

object Body {
  def apply(commandCode: Int) = commandCode match {
    case 0x0 => new Body_0x0
    case 0x1 => new Body_0x1
    case 0x2 => new Body_0x2
  }
}
