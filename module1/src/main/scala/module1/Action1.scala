package module1

import xitrum.Action
import xitrum.annotation.GET

@GET("module1")
class Action1 extends Action {
  def execute() {
    val url = webJarsUrl("module1/1.0-SNAPSHOT/module1.txt")
    respondText(url)
  }
}
