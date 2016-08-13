package main

import scala.annotation.compileTimeOnly
import scala.meta._

@compileTimeOnly("@printDef not expanded")
class printDef extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Defn.Def) = meta {
    q"println(${defn.toString})"
  }
}

@compileTimeOnly("@printVal not expanded")
class printVal extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Defn.Val) = meta {
    q"println(${defn.toString})"
  }
}

@compileTimeOnly("@printVar not expanded")
class printVar extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Defn.Var) = meta {
    q"println(${defn.toString})"
  }
}

@compileTimeOnly("@printClass not expanded")
class printClass extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Defn.Class) = meta {
    q"println(${defn.toString})"
  }
}

@compileTimeOnly("@printTrait not expanded")
class printTrait extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Defn.Trait) = meta {
    q"println(${defn.toString})"
  }
}

@compileTimeOnly("@printObject not expanded")
class printObject extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Defn.Object) = meta {
    q"println(${defn.toString})"
  }
}


@compileTimeOnly("@identity not expanded")
class identity extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Defn) = meta {
    defn
  }
}

@compileTimeOnly("@populateDef not expanded")
class helloWorld extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Defn.Def) = meta {
    val q"..$mods def $name[..$tparams](...$paramss): $tpeopt = $expr" = defn
    q"""..$mods def $name[..$tparams](...$paramss): $tpeopt = "hello world""""
  }
}

@compileTimeOnly("@appendA not expanded")
class appendA extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Defn.Def) = meta {
    val q"..$mods def $name[..$tparams](...$paramss): $tpeopt = { ..$stats }" = defn
    val newStats =  stats :+ q"letters += 'a'"
    q"..$mods def $name[..$tparams](...$paramss): $tpeopt = { ..$newStats }"
  }
}
@compileTimeOnly("@appendB not expanded")
class appendB extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Defn.Def) = meta {
    val q"..$mods def $name[..$tparams](...$paramss): $tpeopt = { ..$stats }" = defn
    val newStats = stats :+ q"letters += 'b'"
    q"..$mods def $name[..$tparams](...$paramss): $tpeopt = { ..$newStats }"
  }
}

