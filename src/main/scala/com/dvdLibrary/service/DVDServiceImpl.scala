package com.dvdLibrary.service

import com.dvdLibrary.dao.DVDRepositoryImpl
import com.dvdLibrary.dto.DVD

class DVDServiceImpl(dao : DVDRepositoryImpl) extends DVDService {

override def addDVD(dvd: DVD): Unit = {
  if(dao.displayDVD())
    dao.addDVD(dvd)
  }

  override def getDVDs(): List[DVD] = {
    dao.listDVDs()
  }

  override def getDVD(id: Int): Option[DVD] = {
    dao.displayDVD(id)
  }

  override def updateDVD(dvd: DVD): Unit = {
    dao.editDVD(dvd)
  }

  override def deleteDVD(id: Int): Unit = {
    dao.removeDVD(id)
  }


}
