package com.dvdLibrary.view

trait UserIOConsole {
  def print(msg: String): Unit
  def readString(msgPrompt: String): String
  def readInt(msgPrompt: String): Int
  def readIntMinMax(msgPrompt: String, min: Int, max: Int): Int
  def readFloat(msgPrompt: String): Float
  def readDouble(msgPrompt: String): Double
  def readLong(msgPrompt: String): Long
}
