package module1

import xitrum.Controller

class Controller1 extends Controller {
  def module1 = GET("module1") {
    val url = urlForResource("module1/module1.txt")
    respondText(url)
  }
}
