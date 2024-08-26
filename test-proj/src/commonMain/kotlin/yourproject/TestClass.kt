package yourproject

class TestClass {
    @CollectList
    class Inner1 {}

    @CollectList
    class Inner2 {}

    @CollectList
    @CollectMap("first")
    class Inner3 {}

    @CollectList
    @CollectMap("second")
    class Inner4 {}

    @CollectMap("third")
    class Inner5 {}
}