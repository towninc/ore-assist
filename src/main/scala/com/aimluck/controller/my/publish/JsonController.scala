package com.aimluck.controller.my.publish

import dispatch.classic.json.JsValue
import java.util.Date
import java.util.logging.Logger
import scala.collection.JavaConversions._
import org.dotme.liquidtpl.Constants
import org.dotme.liquidtpl.LanguageUtil
import org.dotme.liquidtpl.controller.AbstractJsonDataController
import com.aimluck.service.UserDataService
import com.aimluck.service.PublishService
import sjson.json.JsonSerialization._

class JsonController extends AbstractJsonDataController {

  Logger.getLogger(classOf[JsonController].getName)

  override def getList:JsValue = {
    import com.aimluck.service.PublishService.PublishListProtocol._
    val startDate:Date =  new Date
    UserDataService.getCurrentModel match {
      case Some(userData) =>
        tojson(PublishService.fetchAll(Some(userData)))
      case None =>
        addError(Constants.KEY_GLOBAL_ERROR, LanguageUtil.get("error.sessionError"))
        null
    }
  }

  override def getDetail(id:String):JsValue = {
    import com.aimluck.service.PublishService.PublishProtocol._
    val startDate:Date =  new Date

    UserDataService.getCurrentModel match {
      case Some(userData) =>
        PublishService.fetchOne(id, Some(userData)) match {
          case Some(v) => {
              tojson(v)
            }
          case None => {
              addError(Constants.KEY_GLOBAL_ERROR,
                       LanguageUtil.get("error.dataNotFound"))
              null
            }
        }
      case None =>
        addError(Constants.KEY_GLOBAL_ERROR,
                 LanguageUtil.get("error.sessionError"))
        null
    }
  }

  override def getForm(id:String):JsValue = {
    import com.aimluck.service.PublishService.PublishProtocol._
    val startDate:Date =  new Date
    UserDataService.getCurrentModel match {
      case Some(userData) =>
        if((id != null) && (id.size > 0)){
          PublishService.fetchOne(id, Some(userData)) match {
            case Some(v) => {
                tojson(v)
              }
            case None => {
                addError(Constants.KEY_GLOBAL_ERROR,
                         LanguageUtil.get("error.dataNotFound"))
                null
              }
          }
        } else {
          tojson(PublishService.createNew)
        }
      case None =>
        addError(Constants.KEY_GLOBAL_ERROR,
                 LanguageUtil.get("error.sessionError"))
        null
    }
  }
}
