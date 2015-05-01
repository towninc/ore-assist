/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aimluck.service

import com.aimluck.meta.PublishMeta
import com.aimluck.model.Publish
import com.aimluck.lib.util.AppConstants
import com.aimluck.model.UserData
import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.KeyFactory
import java.util.Date
import java.util.logging.Logger
import org.dotme.liquidtpl.Constants
import org.dotme.liquidtpl.LanguageUtil
import org.dotme.liquidtpl.helper.BasicHelper
import org.dotme.liquidtpl.lib.memcache.ReverseCounterLogService
import org.slim3.datastore.Datastore
import scala.collection.JavaConversions._
import sjson.json.DefaultProtocol
import sjson.json.Format
import sjson.json.JsonSerialization

object PublishService {
  val logger = Logger.getLogger(PublishService.getClass.getName)

  object PublishProtocol extends DefaultProtocol {
    import dispatch.classic.json._
    import JsonSerialization._

    implicit object PublishFormat extends Format[Publish] {
      override def reads(json: JsValue): Publish = json match {
        case _ => throw new IllegalArgumentException
      }

      def writes(publish: Publish): JsValue = {
        JsObject(List(
            (JsString(Constants.KEY_ID), tojson(if(publish.getKey != null) KeyFactory.keyToString(publish.getKey) else null)),
            (JsString("name"),  tojson(publish.getName)),
            (JsString("contentType"),  tojson(publish.getContentType)),
            (JsString("content"),  tojson(publish.getContent)),
            (JsString("isPublished"),  tojson(publish.isPublished)),
            (JsString("createdAt"), if(publish.getCreatedAt != null) tojson(AppConstants.dateTimeFormat.format(publish.getCreatedAt)) else tojson("")),
            (tojson("isPublishedMapAll").asInstanceOf[JsString], BasicHelper.jsonFromStringPairs(PublishService.isPublishedMapAll)),
            (tojson("contentTypeMapAll").asInstanceOf[JsString], BasicHelper.jsonFromStringPairs(PublishService.contentTypeMapAll)),
            (JsString("extension"),  tojson(PublishService.getExtension(publish))),
            (JsString(Constants.KEY_DELETE_CONFORM), tojson(LanguageUtil.get("deleteOneConform", Some(Array(LanguageUtil.get("publish"), publish.getName)))))
          ))
      }
    }
  }

  object PublishListProtocol extends DefaultProtocol {
    import dispatch.classic.json._
    import JsonSerialization._

    implicit object PublishFormat extends Format[Publish] {
      override def reads(json: JsValue): Publish = json match {
        case _ => throw new IllegalArgumentException
      }

      def writes(publish: Publish): JsValue =
        JsObject(List(
            (JsString(Constants.KEY_ID), tojson(if(publish.getKey != null) KeyFactory.keyToString(publish.getKey) else null)),
            (JsString("name"),  tojson(publish.getName)),
            (JsString("contentType"),  tojson(publish.getContentType)),
            (JsString("isPublished"),  tojson(publish.isPublished)),
            (JsString("createdAt"), if(publish.getCreatedAt != null) tojson(AppConstants.dateTimeFormat.format(publish.getCreatedAt)) else tojson("")),
            (JsString("extension"),  tojson(PublishService.getExtension(publish))),
            (JsString(Constants.KEY_DELETE_CONFORM), tojson(LanguageUtil.get("deleteOneConform", Some(Array(LanguageUtil.get("contact"), publish.getName)))))
          ))
    }
  }

  def fetchOne( id:String, _userData:Option[UserData] ):Option[Publish] = {
    val m:PublishMeta = PublishMeta.get
    try {
      val key = KeyFactory.stringToKey(id)
      _userData match {
        case Some(userData) =>{
            Datastore.query(m).filter(m.key.equal(key))
            .filter(m.userDataRef.equal(userData.getKey)).asSingle match {
              case v:Publish => Some(v)
              case null => None
            }
          }
        case None => {
            Datastore.query(m).filter(m.key.equal(key)).asSingle match {
              case v:Publish => Some(v)
              case null => None
            }
          }
      }
      
    } catch {
      case e:Exception => {
          logger.severe(e.getMessage)
          logger.severe(e.getStackTraceString)
          None
        }
    }
  }

  def fetchAll(_userData:Option[UserData]):List[Publish] = {
    val m:PublishMeta = PublishMeta.get
    _userData match {
      case Some(userData) => Datastore.query(m).filter(m.userDataRef.equal(userData.getKey)).asList.toList
      case None => Datastore.query(m).asList.toList
    }
  }

  def createNew():Publish = {
    val result:Publish = new Publish
    result.setName("")
    result.setContentType("")
    result.setContent("")
    result
  }

  def saveWithUserData(model:Publish, userData:UserData):Key = {
    val key:Key = model.getKey
    if(key == null){
      model.setKey(Datastore.createKey(classOf[Publish], ReverseCounterLogService.increment("p")))
    }
    
    val now:Date = new Date
    if(model.getCreatedAt == null){
      model.setCreatedAt(now)
    }
    model.setUpdatedAt(now)
    model.getUserDataRef.setModel(userData)
    Datastore.put(userData, model).apply(1)
  }

  def delete(publish:Publish){
    Datastore.delete(publish.getKey)
  }

  val isPublishedMapAll:List[(String, String)] = List[(String, String)](
    true.toString -> LanguageUtil.get("publish.isPublished.true"),
    false.toString -> LanguageUtil.get("publish.isPublished.false")
  );

  val contentTypeMapAll:List[(String, String)] = List[(String, String)](
    "text/xml" -> "XML (text/xml)",
    "text/html" -> "HTML (text/html)",
    "text/javascript" -> "Javascript (text/javascript)",
    "text/plain" -> "Text (text/plain)"
  );

  def getExtension(publish:Publish):String = {
    publish.getContentType match {
      case "text/xml" => "xml"
      case "text/html" => "html"
      case "text/javascript" => "js"
      case _ => "txt"
    }
  }
}
