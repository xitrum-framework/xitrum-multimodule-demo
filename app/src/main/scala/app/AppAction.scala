package app

import xitrum.Action
import xitrum.annotation.GET

@GET("")
class AppAction extends Action {
  def execute() {
    respondViewNoLayout()
  }
}
