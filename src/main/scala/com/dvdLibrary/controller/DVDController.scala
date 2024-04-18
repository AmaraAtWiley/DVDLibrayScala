//import com.dvdLibrary.dao.DVDRepository
//import com.dvdLibrary.dto.DVD
import com.dvdLibrary.dao.DVDRepository
import com.dvdLibrary.dto.DVD
import com.dvdLibrary.view.DVDView
// DVDController is a class that handles the operations related to DVDs.
class DVDController(dao: DVDRepository, view: DVDView) {

  def run(): Unit ={
    var keepGoing: Boolean = true
    var menuSelection: Int = 0;
    while (keepGoing)
      menuSelection = getMenuSelection;
      menuSelection match {
        case 1 =>
          addDVD()

        case 2 =>
         removeDVD()

        case 3 =>
          editDVD()

        case 4 => listDVDs()
        case 5 =>
          displayDVD()
        case 6 =>
          exit()
          keepGoing = false
        case _ => println("Invalid choice")
      }
  }

  // addDVD method is used to add a new DVD to the repository.
  def addDVD(): Unit = {
      val newDvD: DVD = view.getDvdInfo()
      dao.addDVD(newDvD)
  }
  def exit(): Unit={
    view.exitMessage()
  }
  // removeDVD method is used to remove a DVD from the repository using its title.
  def removeDVD(): Unit = {
      val title: String = view.getDvdTitle()
      dao.removeDVD(title)
      view.displaySuccessRemove()
  }

  // editDVD method is used to edit the details of a DVD in the repository.
  def editDVD(): Unit = {
    val title: String = view.getDvdTitle()
    val newDvD: DVD = view.getDvdInfo()
    dao.editDVD(title, newDvD)
    view.displaySuccessEdit()
  }
//
// listDVDs method is used to list all the DVDs in the repository.
  def listDVDs(): Unit = {
    val dvds = dao.listDVDs()
    view.displayDVDs(dvds)
  }

  
  // displayDVD method is used to display the details of a specific DVD using its title.
  def displayDVD(): Unit = {
    val title: String = view.getDvdTitle()
    val dvdOption: Option[DVD] = dao.displayDVD(title)
    dvdOption match {
      case Some(dvd) => view.displayDVD(dvd)
      case None => view.displayDVDNotFound()
    }
  }

  private def getMenuSelection: Int = {
    view.mainMenu();
  }
}