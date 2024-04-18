package com.dvdLibrary.view

import com.dvdLibrary.dto.DVD
import com.dvdLibrary.view.UserIOConsoleImpl
class DVDView (userIO: UserIOConsoleImpl) {
  def mainMenu(): Int = {
    userIO.print("1. Add DVD")
    userIO.print("2. Remove DVD")
    userIO.print("3. Edit DVD")
    userIO.print("4. List DVDs")
    userIO.print("5. Display DVD")
    userIO.print("6. Exit")
    userIO.readIntMinMax("Please select from the above choices", 1, 6)
  }
  def exitMessage(): Unit = {
    userIO.print("Thank you & Goodbye")
  }
  def getDvdInfo(): DVD = {
    val title = userIO.readString("Please enter Title")
    val releaseYear = userIO.readInt("Please enter releaseYear")
    val director = userIO.readString("Please enter Director Name")
    val mpaaRating = userIO.readString("Please enter mpaaRating")
    val studio = userIO.readString("Please enter studio")
    val notes = userIO.readString("Please enter num of notes")
    val newDVD = new DVD(title, releaseYear, director, mpaaRating, studio, notes)
    newDVD
  }

  def getDvdTitle(): String = {
    val title = userIO.readString("Please enter Title")
    title
  }

  def displayDVD(dvd: DVD): Unit = {
    userIO.print(s"Title: ${dvd.title}, " +
      s"Director: ${dvd.director}, " +
      s"Release Year: ${dvd.releaseYear}, " +
      s"MPAA Rating: ${dvd.mpaaRating}, " +
      s"Studio: ${dvd.studio}, " +
      s"Notes: ${dvd.userRating}")
  }
  def displayDVDNotFound(): Unit ={
    userIO.print("*** DVD NOT FOUND ***")
  }

  def displaySuccessRemove(): Unit = {
    userIO.print("*** DVD Removed ***")
  }

  def displaySuccessEdit(): Unit = {
    userIO.print("*** DVD Edited ***")
  }

  def displayDVDs(dvdDetails: List[DVD]): Unit = {
    var index = 1
    dvdDetails.foreach { currentItem =>
      userIO.print(s"[$index] Title: ${currentItem.title}, " +
        s"Director: ${currentItem.director}, " +
        s"Release Year: ${currentItem.releaseYear}, "   +
        s"MPAA Rating: ${currentItem.mpaaRating}, "   +
        s"Director: ${currentItem.director}, "   +
        s"Studio: ${currentItem.studio}, " +
        s"Notes: ${currentItem.userRating}, ")
      index += 1
    }
  }

}
