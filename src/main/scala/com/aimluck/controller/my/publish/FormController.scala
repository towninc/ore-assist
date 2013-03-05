package com.aimluck.controller.my.publish

import com.aimluck.lib.util.AppConstants
import com.aimluck.model.Publish
import com.aimluck.service.PublishService
import com.aimluck.service.UserDataService
import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.KeyFactory
import java.util.logging.Logger
import org.dotme.liquidtpl.Constants
import org.dotme.liquidtpl.LanguageUtil
import org.dotme.liquidtpl.controller.AbstractFormController
import sjson.json.JsonSerialization
import sjson.json.JsonSerialization._
import sjson.json.DefaultProtocol._
import scala.collection.JavaConversions._

class FormController extends AbstractFormController {
  var requestId:String = null.asInstanceOf[String]
  override val logger = Logger.getLogger(classOf[FormController].getName)

  override def redirectUri:String = "/my/publish/form?id=%s".format(requestId);

  override def getTemplateName:String = {
    "form"
  }

  override def validate:Boolean = {
    if(UserDataService.isUserLogin) {
      //Name
      val name = request.getParameter("name")
      if(name.size <= 0 || name.size > AppConstants.VALIDATE_STRING_LENGTH){
        addError( "name" , LanguageUtil.get("error.stringLength",Some(Array(
                LanguageUtil.get("check.name"), "1", AppConstants.VALIDATE_STRING_LENGTH.toString))));
      }

      //ContentType
      val contentType = request.getParameter("contentType")
      if(contentType.size <= 0 || contentType.size > AppConstants.VALIDATE_STRING_LENGTH){
        addError( "contentType" , LanguageUtil.get("error.stringLength",Some(Array(
                LanguageUtil.get("check.contentType"), "1", AppConstants.VALIDATE_STRING_LENGTH.toString))));
      }

      //Content
      val content = request.getParameter("content")
      if(content.size <= 0 || content.size > AppConstants.VALIDATE_LONGTEXT_LENGTH){
        addError( "content" , LanguageUtil.get("error.stringLength",Some(Array(
                LanguageUtil.get("check.content"), "1", AppConstants.VALIDATE_LONGTEXT_LENGTH.toString))));
      }

      //Published
      val isPublished = request.getParameter("isPublished")
      if((isPublished != true.toString) && (isPublished != false.toString)){
        addError( "content" , LanguageUtil.get("error.invalidValue",Some(Array(
                LanguageUtil.get("check.isPublished")))));
      }

      

    } else {
      addError(Constants.KEY_GLOBAL_ERROR ,
               LanguageUtil.get("error.sessionError"))
    }

    !existsError
  }

  override def update:Boolean = {

    UserDataService.getCurrentModel match {
      case Some(userData) => {
          try {
            val id = request.getParameter(Constants.KEY_ID)
            val check:Publish = if(id == null) {
              PublishService.createNew
            } else {
              PublishService.fetchOne(id, Some(userData)) match {
                case Some(v) => v
                case None => null
              }
            }

            if(check != null){
              //Name
              check.setName(request.getParameter("name"))
              //ContentType
              check.setContentType(request.getParameter("contentType"))
              //Content
              check.setContent(request.getParameter("content"))
              //Publised
              check.setPublished(request.getParameter("isPublished") == true.toString)

              val newKey:Key = PublishService.saveWithUserData(check, userData)
              requestId = KeyFactory.keyToString(newKey)
            }
          } catch {
            case e:Exception => addError( Constants.KEY_GLOBAL_ERROR , LanguageUtil.get("error.systemError"));
          }
        }
      case None =>
        addError(Constants.KEY_GLOBAL_ERROR ,
                 LanguageUtil.get("error.sessionError"))

    }
    !existsError
  }
}