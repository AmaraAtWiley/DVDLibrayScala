package com.dvdLibrary.dao

import com.dvdLibrary.dto.DVD

/**
 * DVDRepositoryImpl is a class that implements the DVDRepository trait.
 * It provides the actual implementation of the methods for adding, removing, editing, listing, displaying, and searching DVDs.
 * It uses a List of DVDs as the data structure to store the DVDs.
 */
class DVDRepositoryImpl extends DVDRepository {
  /**
   * A private variable that holds the list of DVDs.
   */
  private var dvdLibrary: List[DVD] = List.empty

  /**
   * Adds a new DVD to the library.
   * @param dvd The DVD to be added.
   */
  override def addDVD(dvd: DVD): Unit = {
    dvdLibrary = dvd :: dvdLibrary
  }

  /**
   * Removes a DVD from the library by its title.
   * @param title The title of the DVD to be removed.
   */
  override def removeDVD(title: String): Unit = {
    dvdLibrary = dvdLibrary.filterNot(_.title == title)
  }

  /**
   * Edits the details of a DVD in the library.
   * @param title The title of the DVD to be edited.
   * @param newDVD The new DVD details to replace the old ones.
   */
  override def editDVD(title: String, newDVD: DVD): Unit = {
    dvdLibrary.find(_.title == title).foreach { oldDVD =>
      dvdLibrary = newDVD :: dvdLibrary.filterNot(_ == oldDVD)
    }
  }

  /**
   * Lists all DVDs in the library.
   */
  override def listDVDs(): List[DVD] = {
    dvdLibrary
  }

  /**
   * Displays the details of a DVD by its title.
   * @param title The title of the DVD to be displayed.
   */
  override def displayDVD(title: String): Option[DVD] = {
    dvdLibrary.find(_.title == title)
  }


}
