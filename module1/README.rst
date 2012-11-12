This module provides for "app" to use:

* GET /module1 (see main/scala/module1/Controller1.scala)
* GET /resources/public/module1/module1.txt (see main/resources/public/module1/module1.txt)

To publish this module (xitrum-modularized-demo-module1):

::

  ../sbt/sbt clean publish-local
