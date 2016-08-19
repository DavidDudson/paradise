import main._
import org.scalatest.FunSuite

class ExpansionTests extends FunSuite {

  test("Empty Tree example") {
    var letters = ""

    @appendA
    def bar() = {}

    bar()

    assert(letters === "a")
  }
}
