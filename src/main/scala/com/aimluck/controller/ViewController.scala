package com.aimluck.controller

import org.dotme.liquidtpl.Constants
import org.slim3.controller.Controller
import org.slim3.controller.Navigation
import com.aimluck.service.PublishService

class ViewController extends Controller {
  @throws(classOf[Exception])
  override def run():Navigation =  {
    try {
      val id = request.getParameter(Constants.KEY_ID)
      PublishService.fetchOne(id, None) match {
        case Some(v) => {
            if(v.isPublished) {
              response.setCharacterEncoding(Constants.CHARSET)
              response.setContentType(v.getContentType)
              response.getWriter.write(v.getContent)
            }
          }
        case None => null
      }
    }
    null
  }
}
