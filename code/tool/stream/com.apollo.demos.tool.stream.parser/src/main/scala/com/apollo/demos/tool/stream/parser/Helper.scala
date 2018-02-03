package com.apollo.demos.tool.stream.parser

import java.nio.ByteBuffer
import java.nio.ByteBuffer._
import java.nio.ByteOrder
import java.nio.ByteOrder._

object Helper {
  implicit val BigEndian = BIG_ENDIAN
  implicit val LittleEndian = LITTLE_ENDIAN

  def read[T](op: ByteBuffer => T, length: Int)(bo: ByteOrder)(buf: Array[Byte], offset: Int) = { val bb = wrap(buf, offset, length); bb.order(bo); op(bb) }

  val getByte = read[Byte](_.get, 1)_
  val getShort = read[Short](_.getShort, 2)_
  val getInt = read[Int](_.getInt, 4)_
}
