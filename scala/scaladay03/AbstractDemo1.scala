package scaladay03

abstract class Animal{
  var name:String
  val age:Int = 1
  def walk:String
}

class Cat extends Animal{
  var name = "cat"
  override def walk: String = "use leg"
  //重写的属性应该用val修饰
  override val age = 2
}


object AbstractDemo1 {
  def main(args: Array[String]): Unit = {
    val obj = new Cat
    println(obj.age)

  }
}
