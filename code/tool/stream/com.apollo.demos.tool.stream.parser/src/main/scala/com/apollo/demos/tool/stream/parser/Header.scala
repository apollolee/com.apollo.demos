package com.apollo.demos.tool.stream.parser

import java.nio.ByteOrder

import Helper._

object Header {
  def parse[T](bo: ByteOrder)(buf: Array[Byte]) = { val result = decode(bo)(buf); (result, Body(result.commandCode).decode(bo)(buf, result.length).asInstanceOf[T]) }
  def decode(bo: ByteOrder)(buf: Array[Byte]) = Result_Header(getByte(bo)(buf, 0), getShort(bo)(buf, 1), getInt(bo)(buf, 3))
}
