/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aimluck.controller

import com.aimluck.lib.util.AppConstants
import dispatch.classic.json.JsObject
import dispatch.classic.json.JsString
import dispatch.classic.json.JsValue
import org.dotme.liquidtpl.Constants
import org.dotme.liquidtpl.LanguageUtil
import org.dotme.liquidtpl.controller.AbstractJsonController
import scala.collection.JavaConversions._
import sjson.json.JsonSerialization._
import sjson.json.DefaultProtocol._

//FIXME Case Classで実装して一般化する
class MenuController extends AbstractJsonController {
  def getJson:JsValue = {
    val PATH:String = "path"
    val NAME:String = "name"
    val TEXT:String = "text"
    val CLASS:String = "css_class"
    val SELECT:String = "select"

    val MAIN_MENU:String = "mainMenu"
    val SUB_MENU:String = "subMenu"

    val pageBasePath = request.getParameter(Constants.KEY_BASE_PATH);
    val sitemap:List[Map[String, String]] = List(
      Map(PATH -> "/my/publish/index", TEXT -> LanguageUtil.get("publish")),
      Map(PATH -> "/logout", TEXT -> LanguageUtil.get("logout"))
    ).map { m => {
        if(m.apply(PATH).startsWith(pageBasePath)) {
          m + (SELECT -> SELECT)
        } else {
          m
        }
      }
    }

    val subMenu:List[Map[String, String]] = request.getParameter(Constants.KEY_BASE_PATH) match {
      case "/my/publish/" =>
        List(
          Map(PATH -> "/my/publish/index",
              TEXT -> LanguageUtil.get("list"),
              CLASS -> "icon_page"),
          Map(PATH -> "/my/publish/form",
              TEXT -> LanguageUtil.get("add"),
              CLASS -> "icon_add")
        )
      case _ => List()
    }

    JsObject(List(
        (tojson(MAIN_MENU).asInstanceOf[JsString], tojson(sitemap)),
        (tojson(SUB_MENU).asInstanceOf[JsString], tojson(subMenu))
      ))

  }
}
