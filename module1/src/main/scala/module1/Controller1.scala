package module1

import xitrum.Controller

class Controller1 extends Controller {
  def module1 = GET("module1") {
    respondText("module1")
  }
}
