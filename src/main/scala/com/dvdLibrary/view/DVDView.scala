package com.dvdLibrary.view

import com.dvdLibrary.view.UserIOConsoleImpl
object DVDView {
  def mainMenu(userIO: UserIOConsoleImpl): Unit = {
    userIO.print("1. Add DVD")
    userIO.print("2. Remove DVD")
    userIO.print("3. Edit DVD")
    userIO.print("4. List DVDs")
    userIO.print("5. Display DVD")
    userIO.print("6. Search DVD")
    userIO.print("7. Exit")
  }


}
