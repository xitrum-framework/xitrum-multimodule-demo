See `discussion <http://groups.google.com/group/xitrum-framework/browse_thread/thread/7588995934854a56>`_.

Run:

::

  sbt/sbt "project app" run

The project is devided into 2 parts:

* module1
* app, depends on module1

When you run app, routes in module1 will be collected
(see src/main/scala/module1/Controller1.scala):

::

  GET /
  GET /module1

You can also access module1.txt in module1
(see src/main/resources/public/module1/module1.txt):

::

  GET /webjars/module1/1.0-SNAPSHOT/module1.txt

If you add or remove routes in module1, remember to remove routes.cache in app.
