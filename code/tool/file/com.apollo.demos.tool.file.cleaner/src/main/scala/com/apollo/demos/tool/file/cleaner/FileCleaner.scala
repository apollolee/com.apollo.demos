package com.apollo.demos.tool.file.cleaner

import java.io.File
import java.lang.Integer.parseInt
import java.lang.System.currentTimeMillis

import scala.Array.empty

object FileCleaner {
  private val day: Long = 1000 * 60 * 60 * 24
  private var threshold: Long = _

  def main(args: Array[String]): Unit = {
    val thresholdDays = parseInt(args(0))
    val dir = args(1)

    threshold = currentTimeMillis - day * thresholdDays
    clean(new File(dir))
  }

  def clean(dir: File) {
    val files = dir.listFiles().groupBy(_.isDirectory)
    files.getOrElse(false, empty).filter(_.lastModified < threshold).foreach(f => if (!f.delete) println("Can't delete " + f.getAbsolutePath))
    files.getOrElse(true, empty).foreach(clean)
  }
}
