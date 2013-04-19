package module1

import xitrum.Action
import xitrum.annotation.GET

@GET("module1")
class Action1 extends Action {
  def execute() {
    val url = resourceUrl("module1/module1.txt")
    respondText(url)
  }
}
