import main._
import org.scalatest.FunSuite

class ExpansionTests extends FunSuite {

  test("Nested macro should expand with identity") {
    @identity
    object Foo {
      @helloWorld def bar() = "test"
    }

    assert(Foo.bar() === "hello world")
  }

  // This also verifies expansion order
  test("Nested macro should expand with different macros") {
    var letters = 0

    @appendA
    def foo() = {
      @appendB
      def bar() = {}
    }

    assert(letters === "ab")
  }

  test("Nested macro of the same name should expand") {
    var letters = ""

    @appendA
    def foo() = {
      @appendA
      def bar() = {}
      bar()
    }

    foo()

    assert(letters === "aa")
  }

  test("Placebo after expandee should compile and work") {
    var letters = ""

    @appendA @placebo
    def bar() = {}

    bar()

    assert(letters === "a")
  }

  test("Placebo before expandee should compile and work") {
    var letters = ""

    @placebo @appendA
    def bar() = {}

    bar()

    assert(letters === "a")
  }

  test("Multiple expandees of same kinds should expand") {
    var letters = ""

    @appendA @appendA
    def bar() = {}

    bar()

    assert(letters === "aa")
  }

  test("Multiple expandees of same kinds with others in between should expand") {
    var letters = ""

    @appendA //@appendB @identity @appendA @identity @appendB
    def bar() = {}

    bar()

    assert(letters === "abab")
  }

 test("Multiple expandees of similar kinds should expand in the correct order") {
   var letters = ""


   @appendA //@appendB
    def bar() = {}

    bar()

    assert(letters === "ab")
  }

  test("Identity expandee followed by regular expandee should expand correctly") {
    var letters = ""

    @identity @appendA
    def bar() = {}

    bar()

    assert(letters === "a")
  }

  test("Regular expandee followed by Identity expandee should expand correctly") {
    var letters = ""

    @appendA //@identity
    def bar() = {}

    bar()

    assert(letters === "a")
  }
}
