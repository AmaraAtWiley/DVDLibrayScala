import com.dvdLibrary.dao.DVDRepository
import com.dvdLibrary.dto.DVD

// DVDController is a class that handles the operations related to DVDs.
class DVDController(repository: DVDRepository) {

  // addDVD method is used to add a new DVD to the repository.
  def addDVD(dvd: DVD): Unit = {
    repository.addDVD(dvd)
  }

  // removeDVD method is used to remove a DVD from the repository using its title.
  def removeDVD(title: String): Unit = {
    repository.removeDVD(title)
  }

  // editDVD method is used to edit the details of a DVD in the repository.
  def editDVD(title: String, newDVD: DVD): Unit = {
    repository.editDVD(title, newDVD)
  }

  // listDVDs method is used to list all the DVDs in the repository.
  def listDVDs(): Unit = {
    repository.listDVDs()
  }

  // displayDVD method is used to display the details of a specific DVD using its title.
  def displayDVD(title: String): Unit = {
    repository.displayDVD(title)
  }

  // searchDVD method is used to search for a DVD in the repository using its title.
  def searchDVD(title: String): Unit = {
    repository.searchDVD(title)
  }
}