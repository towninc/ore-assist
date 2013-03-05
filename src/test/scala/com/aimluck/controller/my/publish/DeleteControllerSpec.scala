package com.aimluck.controller.my.publish

import org.specs.Specification
import org.specs.runner._
import org.slim3.tester.ControllerTester

object DeleteControllerSpec extends org.specs.Specification {

  val tester = new ControllerTester( classOf[DeleteController] )

  "DeleteController" should {
    doBefore{ tester.setUp;tester.start("/my/publish/delete")}
    doAfter{ tester.tearDown}

    "after tearDown" >> {
        true
    }
  }
}
class DeleteControllerSpecTest extends JUnit4( DeleteControllerSpec )
