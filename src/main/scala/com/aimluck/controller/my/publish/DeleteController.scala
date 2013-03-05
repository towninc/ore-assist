package com.aimluck.controller.my.publish

import org.dotme.liquidtpl.Constants
import org.slim3.controller.Controller
import org.slim3.controller.Navigation
import com.aimluck.service.PublishService
import com.aimluck.service.UserDataService

class DeleteController extends Controller {

  @throws(classOf[Exception])
  override def run():Navigation =  {
    UserDataService.getCurrentModel match {
      case Some(userData) =>
        val id:String = request.getParameter(Constants.KEY_ID);
        PublishService.fetchOne(id, Some(userData)) match{
          case Some(publish) =>
            PublishService.delete(publish)
          case None =>
        }
      case None =>
    }
    return redirect("/my/publish/index")
  }
}
