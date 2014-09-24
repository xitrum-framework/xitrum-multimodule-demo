package module1

import xitrum.Action
import xitrum.annotation.GET

@GET("module1")
class Action1 extends Action {
  def execute() {
    val url = webJarsUrl("module1/1.0-SNAPSHOT/module1.txt")
    respondHtml(
      <body>
        <p>This is an action in module1.</p>
        <p><a href={url}>Link to a resource file in module1.</a></p>
      </body>
    )
  }
}
