package com.dvdLibrary.service

import com.dvdLibrary.dto.DVD

trait DVDService {
  def addDVD(dvd: DVD): Unit

  def removeDVD(title: String): Unit

  def editDVD(title: String, newDVD: DVD): Unit

  def listDVDs(): List[DVD]

  def displayDVD(title: String): Option[DVD]
}
