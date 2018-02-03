package com.apollo.demos.tool.stream.parser

case class Result_Header(val version: Byte, val commandCode: Short, val length: Int)
case class Result_Body_0x0(val resv: Byte, val slot: Byte, val boardType: Short, val boardState: Byte)
case class Result_Body_0x1(val resv: Byte, val slot: Byte, val boardType: Short)
case class Result_Body_0x2(val resetType: Byte, val resv: Byte)
