package com.dvdLibrary.view

import scala.io.StdIn

class UserIOConsoleImpl extends UserIOConsole {
   override def print(msg: String): Unit = {
    println(msg)
  }

   override def readString(msgPrompt: String): String = {
    println(msgPrompt)
    StdIn.readLine()
  }

   override def readInt(msgPrompt: String): Int = {
    var num: Int = 0
    var invalidInput = true

    while (invalidInput) {
      try {
        val stringValue = this.readString(msgPrompt)
        num = stringValue.toInt;
        invalidInput = false;
      } catch {
        case _: NumberFormatException =>
          this.print("Input error. Please enter a valid integer.")
      }
    }
    num
   }

   override def readIntMinMax(msgPrompt: String, min: Int, max: Int): Int = {
    val input = readInt(msgPrompt)
    if (input < min || input > max) {
      print(s"Input must be between $min and $max. Please try again.")
      readIntMinMax(msgPrompt, min, max)
    } else {
      input
    }
   }

   override def readFloat(msgPrompt: String): Float = {
    var num: Float = 0.0f
    var invalidInput = true
    while (invalidInput) {
      try {
        val stringValue = this.readString(msgPrompt)
        num = stringValue.toFloat
        invalidInput = false
      } catch {
        case _: NumberFormatException =>
          this.print("Input error. Please enter a valid float.")
      }
    }
    num
  }

   override def readDouble(msgPrompt: String): Double = {
    var num: Double = 0.0
    var invalidInput = true
    while (invalidInput) {
      try {
        val stringValue = this.readString(msgPrompt)
        num = stringValue.toDouble
        invalidInput = false
      } catch {
        case _: NumberFormatException =>
          this.print("Input error. Please enter a valid Double.")
      }
    }
    num
   }

  override def readLong(msgPrompt: String): Long = {
    var num: Long = 0L
    var invalidInput = true
    while (invalidInput) {
      try {
        val stringValue = this.readString(msgPrompt)
        num = stringValue.toLong
        invalidInput = false
      } catch {
        case _: NumberFormatException =>
          this.print("Input error. Please enter a valid Double.")
      }
    }
    num
  }




}
