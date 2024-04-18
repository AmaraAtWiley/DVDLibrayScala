package com.dvdLibrary.dao

import com.dvdLibrary.dto.DVD

/**
 * DVDRepository is a trait that defines the contract for a DVD data access object (DAO).
 * It provides methods for adding, removing, editing, listing, displaying, and searching DVDs.
 */
trait DVDRepository {
  /**
   * Adds a new DVD to the repository.
   * @param dvd The DVD to be added.
   */
  def addDVD(dvd: DVD): Unit

  /**
   * Removes a DVD from the repository by its title.
   * @param title The title of the DVD to be removed.
   */
  def removeDVD(title: String): Unit

  /**
   * Edits the details of a DVD in the repository.
   * @param title The title of the DVD to be edited.
   * @param newDVD The new DVD details to replace the old ones.
   */
  def editDVD(title: String, newDVD: DVD): Unit

  /**
   * Lists all DVDs in the repository.
   */
  def listDVDs(): List[DVD]

  /**
   * Displays the details of a DVD by its title.
   * @param title The title of the DVD to be displayed.
   */
  def displayDVD(title: String): Option[DVD]
}