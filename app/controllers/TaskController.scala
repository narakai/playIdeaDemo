package controllers

import models.Task
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

}
