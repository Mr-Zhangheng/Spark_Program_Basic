package test0522

class Person(val name:String){
  val firstName:String = name.split(" ")(0)
  val lastName:String = name.split(" ")(1)
}

object PersonTest1 {
  def main(args: Array[String]): Unit = {
    val person = new Person("Fred Smith")
    println(person.firstName)
    println(person.lastName)
  }
}

