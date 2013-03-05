package com.aimluck.controller.my.publish

import org.specs.Specification
import org.specs.runner._
import org.slim3.tester.ControllerTester

object ViewControllerSpec extends org.specs.Specification {

  val tester = new ControllerTester( classOf[ViewController] )

  "ViewController" should {
    doBefore{ tester.setUp;tester.start("/my/publish/view")}

    "not null" >> {
      val controller = tester.getController[ViewController]
      controller mustNotBe null
    }
    "not redirect" >> {
      tester.isRedirect mustBe false
    }
    "get destination path is null" >> {
      tester.getDestinationPath mustBe null
    }

    doAfter{ tester.tearDown}

    "after tearDown" >> {
        true
    }
  }
}
class ViewControllerSpecTest extends JUnit4( ViewControllerSpec )
