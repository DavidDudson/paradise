import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

object doublerMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    val result = {
      def double[T <: Name](name: T): T = {
        val sdoubled = name.toString + name.toString
        val doubled  = if (name.isTermName) newTermName(sdoubled) else newTypeName(sdoubled)
        doubled.asInstanceOf[T]
      }
      annottees.map(_.tree).toList match {
        case ClassDef(mods, name, tparams, impl) :: rest =>
          ClassDef(mods, double(name), tparams, impl) :: rest
        case ModuleDef(mods, name, impl) :: rest => ModuleDef(mods, double(name), impl) :: rest
        case DefDef(mods, name, tparams, vparamss, tpt, rhs) :: rest =>
          DefDef(mods, double(name), tparams, vparamss, tpt, rhs) :: rest
        case TypeDef(mods, name, tparams, rhs) :: rest =>
          TypeDef(mods, double(name), tparams, rhs) :: rest
        case ValDef(mods, name, tpt, rhs) :: rest => ValDef(mods, double(name), tpt, rhs) :: rest
      }
    }
    c.Expr[Any](Block(result, Literal(Constant(()))))
  }
}

class doubler1 extends StaticAnnotation {
  def transform(annottees: Any*) = macro doublerMacro.impl
}

class doubler2 extends StaticAnnotation {
  def macroTransform(annottees: Any*) = ???
}

class doubler3 extends StaticAnnotation {
  def macroTransform(annottee: Any) = macro doublerMacro.impl
}

class doubler4 extends StaticAnnotation {
  def macroTransform(myAnnottees: Any*) = macro doublerMacro.impl
}

class doubler5 extends StaticAnnotation {
  def macroTransform[T](annottees: Any*) = macro doublerMacro.impl
}
