import scala.language.postfixOps

name := "hands-on-akka"
version := "0.0.1-SNAPSHOT"

lazy val commonSettings = Seq(
  organization := "com.omd",

  scalaVersion := "2.13.3",

  scalacOptions ++= Seq("-Xmax-classfile-name", "128"),

 scalacOptions ++= Seq(
        "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
        "-encoding", "utf-8",                // Specify character encoding used by source files.
        "-explaintypes",                     // Explain type errors in more detail.
        "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
        "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
        //  "-language:experimental.macros",   // Allow macro definition (besides implementation and application). Disabled, as this will significantly change in Scala 3
        "-language:higherKinds",             // Allow higher-kinded types
        //  "-language:implicitConversions",   // Allow definition of implicit functions called views. Disabled, as it might be dropped in Scala 3. Instead use extension methods (implemented as implicit class Wrapper(val inner: Foo) extends AnyVal {}
        "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
        "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
        "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.

        "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
        "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
        "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
        "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
        "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
        "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
        "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
        "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
        "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
        "-Xlint:option-implicit",            // Option.apply used implicit view.
        "-Xlint:package-object-classes",     // Class or object defined in package object.
        "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
        "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
        "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
        "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
        "-Xlint:unused",                     // TODO check if we still need -Wunused below
        "-Xlint:nonlocal-return",            // A return statement used an exception for flow control.
        "-Xlint:implicit-not-found",         // Check @implicitNotFound and @implicitAmbiguous messages.
        "-Xlint:serial",                     // @SerialVersionUID on traits and non-serializable classes.
        "-Xlint:valpattern",                 // Enable pattern checks in val definitions.
        "-Xlint:eta-zero",                   // Warn on eta-expansion (rather than auto-application) of zero-ary method.
        "-Xlint:eta-sam",                    // Warn on eta-expansion to meet a Java-defined functional interface that is not explicitly annotated with @FunctionalInterface.
        "-Xlint:deprecation",                // Enable linted deprecations.

        "-Wdead-code",                       // Warn when dead code is identified.
        "-Wextra-implicit",                  // Warn when more than one implicit parameter section is defined.
        "-Wmacros:both",                     // Lints code before and after applying a macro
        "-Wnumeric-widen",                   // Warn when numerics are widened.
        "-Woctal-literal",                   // Warn on obsolete octal syntax.
        "-Wself-implicit",                   // Warn when an implicit resolves to an enclosing self-definition.
        "-Wunused:imports",                  // Warn if an import selector is not referenced.
        "-Wunused:patvars",                  // Warn if a variable bound in a pattern is unused.
        "-Wunused:privates",                 // Warn if a private member is unused.
        "-Wunused:locals",                   // Warn if a local definition is unused.
        "-Wunused:explicits",                // Warn if an explicit parameter is unused.
        "-Wunused:implicits",                // Warn if an implicit parameter is unused.
        "-Wunused:params",                   // Enable -Wunused:explicits,implicits.
        "-Wunused:linted",
        "-Wvalue-discard",                   // Warn when non-Unit expression results are unused.

        "-Ybackend-parallelism", "8",                 // Enable paralellisation — change to desired number!
        "-Ycache-plugin-class-loader:last-modified",  // Enables caching of classloaders for compiler plugins
        "-Ycache-macro-class-loader:last-modified",   // and macro definitions. This can lead to performance improvements.

    ),

  fork in Test := true,

  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.11.0"),
  addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),

  resolvers ++= Seq(
    Resolver.typesafeRepo("releases"),
    Resolver.sonatypeRepo("releases"),
    // Only necessary for SNAPSHOT release
    Resolver.sonatypeRepo("snapshots"))
)

lazy val Versions = new {
	lazy val config = "1.3.2"
	lazy val logback = "1.2.3"
	lazy val cats = "2.1.1"
	lazy val shapeless = "2.3.3"
	lazy val `cats-mtl` = "0.7.0"
	lazy val akka = "2.6.8"
	lazy val http = "10.1.12"
}

lazy val catsSettings = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % Versions.cats,
    "org.typelevel" %% "cats-effect" % Versions.cats,
    "org.typelevel" %% "cats-mtl-core" % Versions.`cats-mtl`,
    "com.chuusai" %% "shapeless" % Versions.shapeless,
   ) map {
    _ withSources() withJavadoc()
  }
)

lazy val appSettings = Seq(
  libraryDependencies ++= Seq("com.typesafe" % "config" % Versions.config)
)

lazy val coverageSettings = Seq(
  coverageEnabled in publishLocal := false,
  coverageEnabled in publish := false,
  coverageMinimum := 80
  //  coverageFailOnMinimum := true
)

lazy val loggerSettings = Seq(
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % Versions.logback ,
    "ch.qos.logback" % "logback-core" % Versions.logback)  map {
    _ withSources() withJavadoc()
  }
)

lazy val akkaSettings = Seq(
	libraryDependencies ++= Seq(
		"com.typesafe.akka" %% "akka-http"   % Versions.http,
		"com.typesafe.akka" %% "akka-stream" % Versions.akka
	)   map {
    _ withSources() withJavadoc()
  }
)

lazy val users = (project in file("modules/users"))
  .settings(appSettings ++ akkaSettings ++ catsSettings ++ loggerSettings ++ 
    coverageSettings )
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)

lazy val root = (project in file(".")).aggregate(users)