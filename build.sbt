import org.scalajs.linker.interface.{ModuleKind, OutputPatterns, ESVersion}

val scala3Version = "3.6.3"

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "matrix-buttons",
    version := "0.0.1",
    scalaVersion := scala3Version,
    
    // Add scalajs-dom dependency
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.6.0",
    
    // Scala.js settings
    scalaJSUseMainModuleInitializer := true,
    Compile / mainClass := Some("MatrixButtons"),
    
    // Use ES modules (required for WebAssembly)
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) },
    
    // WebAssembly specific settings - use ESVersion.ES2015 instead of deprecated method
    scalaJSLinkerConfig ~= { _.withESFeatures(_.withESVersion(ESVersion.ES2015)) },
    
    // For Node.js env with WebAssembly
    jsEnv := {
      import org.scalajs.jsenv.nodejs.NodeJSEnv
      new NodeJSEnv(
        NodeJSEnv.Config()
          .withArgs(List(
            "--experimental-wasm-gc",
            "--experimental-wasm-exnref",
            "--experimental-wasm-imported-strings"
          ))
      )
    }
  )
