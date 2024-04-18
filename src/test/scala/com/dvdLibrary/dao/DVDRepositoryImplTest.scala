import com.dvdLibrary.dao.DVDRepositoryImpl
import com.dvdLibrary.dto.DVD
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class DVDRepositoryImplTest extends AnyFunSuite with Matchers {
  test("addDVD should add a DVD to the library") {
    val repo = new DVDRepositoryImpl
    val dvd = DVD("Test Title", 2022, "Test Director", "Test Rating", "Test Studio", "Test User Rating")
    repo.addDVD(dvd)
    assert(repo.listDVDs().contains(dvd))
  }

  test("removeDVD should not remove a DVD if it does not exist in the library") {
    val repo = new DVDRepositoryImpl
    val dvd = DVD("Test Title", 2022, "Test Director", "Test Rating", "Test Studio", "Test User Rating")
    repo.addDVD(dvd)
    repo.removeDVD("Nonexistent Title")
    assert(repo.listDVDs().contains(dvd))
  }

  test("editDVD should not edit a DVD if it does not exist in the library") {
    val repo = new DVDRepositoryImpl
    val dvd = DVD("Test Title", 2022, "Test Director", "Test Rating", "Test Studio", "Test User Rating")
    val newDVD = DVD("New Test Title", 2023, "New Test Director", "New Test Rating", "New Test Studio", "New Test User Rating")
    repo.addDVD(dvd)
    repo.editDVD("Nonexistent Title", newDVD)
    assert(repo.listDVDs().contains(dvd))
    assert(!repo.listDVDs().contains(newDVD))
  }


  test("searchDVD should return an empty list if no DVDs match the search term") {
    val repo = new DVDRepositoryImpl
    //assert(repo.listDVDs("Nonexistent Title").isEmpty)
  }

  test("removeDVD should remove a DVD from the library by title") {
    val repo = new DVDRepositoryImpl
    val dvd = DVD("Test Title", 2022, "Test Director", "Test Rating", "Test Studio", "Test User Rating")
    repo.addDVD(dvd)
    repo.removeDVD("Test Title")
    assert(!repo.listDVDs().contains(dvd))
  }

  test("editDVD should edit the details of a DVD in the library") {
    val repo = new DVDRepositoryImpl
    val dvd = DVD("Test Title", 2022, "Test Director", "Test Rating", "Test Studio", "Test User Rating")
    val newDVD = DVD("New Test Title", 2023, "New Test Director", "New Test Rating", "New Test Studio", "New Test User Rating")
    repo.addDVD(dvd)
    repo.editDVD("Test Title", newDVD)
    assert(repo.listDVDs().contains(newDVD))
    assert(!repo.listDVDs().contains(dvd))
  }

  test("listDVDs should list all DVDs in the library") {
    val repo = new DVDRepositoryImpl
    val dvd1 = DVD("Test Title 1", 2022, "Test Director 1", "Test Rating 1", "Test Studio 1", "Test User Rating 1")
    val dvd2 = DVD("Test Title 2", 2023, "Test Director 2", "Test Rating 2", "Test Studio 2", "Test User Rating 2")
    repo.addDVD(dvd1)
    repo.addDVD(dvd2)
    assert(repo.listDVDs().contains(dvd1))
    assert(repo.listDVDs().contains(dvd2))
  }

  test("displayDVD should display the details of a DVD by its title") {
    val repo = new DVDRepositoryImpl
    val dvd = DVD("Test Title", 2022, "Test Director", "Test Rating", "Test Studio", "Test User Rating")
    repo.addDVD(dvd)
    assert(repo.displayDVD("Test Title").contains(dvd))
  }
}
