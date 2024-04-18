import com.dvdLibrary.dao.DVDRepositoryImpl
import com.dvdLibrary.view.{DVDView, UserIOConsole, UserIOConsoleImpl}

object DVDApp {
  def main(args: Array[String]): Unit = {
    val userIO: UserIOConsoleImpl = new UserIOConsoleImpl()
    val view: DVDView = new DVDView(userIO)
    val dao: DVDRepositoryImpl = new DVDRepositoryImpl();
    val controller: DVDController = new DVDController(dao,view)
    controller.run()
  }
}
