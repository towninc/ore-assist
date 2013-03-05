package com.aimluck.controller.cron

import com.google.appengine.api.datastore.DatastoreServiceFactory
import com.google.appengine.api.datastore.FetchOptions
import com.google.appengine.api.datastore.Query
import org.dotme.liquidtpl.lib.memcache.ReverseCounterLogService
import org.slim3.controller.Controller
import org.slim3.controller.Navigation
import scala.collection.JavaConversions._

class CleanupcounterController extends Controller {

  @throws(classOf[Exception])
  override def run():Navigation =  {
    val name = request.getParameter(ReverseCounterLogService.KEY_NAME)
    ReverseCounterLogService.cleanupDatastore(name)
    null
  }
}
