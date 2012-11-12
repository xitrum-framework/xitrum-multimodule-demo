This module provides for "app" to use:

* GET /module1 (see src/main/scala/module1/Controller1.scala)
* GET /resources/public/module1/module1.txt (see src/main/resources/public/module1/module1.txt)

To link to the above resource, of course you can use the URL
/resources/public/module1/module1.txt, but in your Scala or Scalate code,
it's better to use:

::

  urlForResource("module1/module1.txt")

Something like /resources/public/module1/module1.txt?ec7FMnm7Vdcb8mmtfdbqPA
will be generated. ec7FMnm7Vdcb8mmtfdbqPA is generated based on module1.txt
content.

To publish this module (xitrum-modularized-demo-module1, see build.sbt):

::

  ../sbt/sbt clean publish-local
