## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

Compile js with `sbt fastOptJS`

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).


Compile javascript: `sbt fastOptJS`

Run server: `sbt moviesJVM/run`

TODO:
 * fix build so that the js file is available upon build
 * serve js file and index.html in httpserver
 * ui stuff
