This app uses things provided by module1.
In build.sbt, there's this line:

::

  libraryDependencies += "tv.cntt" %% "xitrum-modularized-demo-module1" % "1.0-SNAPSHOT"

Run:

::

  ../sbt/sbt run

Then access:

::

  http://localhost:8000/module1
  http://localhost:8000/resources/public/module1/module1.txt

If you add or remove routes in module1, remember to remove routes.cache.
