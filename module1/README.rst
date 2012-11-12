This module provides for "app" to use:

* GET /module1 (see src/main/scala/module1/Controller1.scala)
* GET /resources/public/module1/module1.txt (see src/main/resources/public/module1/module1.txt)

To link to the above resource, use:

::

  urlForResource("module1/module1.txt")

To publish this module (xitrum-modularized-demo-module1, see build.sbt):

::

  ../sbt/sbt clean publish-local
