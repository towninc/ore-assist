/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aimluck.controller
import org.slim3.controller.router.RouterImpl;

class AppRouter() extends RouterImpl {
  addRouting("/_ah/{id}/view.xml", "/view?id={id}");
  addRouting("/_ah/{id}/view.html", "/view?id={id}");
  addRouting("/_ah/{id}/view.js", "/view?id={id}");
  addRouting("/_ah/{id}/view.txt", "/view?id={id}");
}
