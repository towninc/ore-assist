package com.aimluck.controller.my.publish

import org.dotme.liquidtpl.Constants
import org.slim3.controller.Controller
import org.slim3.controller.Navigation
import com.aimluck.service.PublishService
import com.aimluck.service.UserDataService

class ViewController extends Controller {

  @throws(classOf[Exception])
  override def run():Navigation =  {
    UserDataService.getCurrentModel match {
      case Some(userData) => {
          try {
            val id = request.getParameter(Constants.KEY_ID)
            PublishService.fetchOne(id, Some(userData)) match {
              case Some(v) => {
                  response.setCharacterEncoding(Constants.CHARSET)
                  response.setContentType(v.getContentType)
                  response.getWriter.write(v.getContent)
              }
              case None => null
            }
          }
        }
      case None =>{

        }
    }
    null
  }
}
