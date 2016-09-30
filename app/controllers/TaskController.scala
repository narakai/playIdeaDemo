package controllers

import models.{Artist, Task}
import play.api.mvc.{Action, Controller}

/**
  * Created by leon on 2016/9/27.
  */
class TaskController extends Controller{
  def index = Action {
    Redirect(routes.TaskController.tasks())
  }

  def tasks = Action {
    //list传给view
    Ok(views.html.index(Task.all))
  }

  def newTask = Action(parse.urlFormEncoded) {
    implicit request =>
      Task.add(request.body.get("taskName").get.head)
      Redirect(routes.TaskController.index())
  }

  def deleteTask(id: Int) = Action {
    Task.delete(id)
    Ok
  }

  def listArtist = Action {
    Ok(views.html.home(Artist.fetch))
  }

  def fetchArtistByName(name: String) = Action {
    Ok(views.html.home(Artist.fetchByName(name)))
  }

  def search(name: String, country:String) = Action {
    val result = Artist.fetchByNameOrCountry(name, country)
    if (result.isEmpty) {
      NoContent
    } else {
      Ok(views.html.home(result))
    }
  }

  def search2(name: Option[String], country: String) = Action {
    val result = name match {
      case Some(n) => Artist.fetchByNameOrCountry(n, country)
      case None => Artist.fetchByCountry(country)
    }
    if (result.isEmpty) {
      NoContent
    } else {
      Ok(views.html.home(result))
    }
  }
}
