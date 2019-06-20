package scaladay04

//样例类匹配
case class Person(name:String,age:Int)

object CaseClassMatch {

  def  matchDemo(person:Person)={
    person match {
      case Person("tom",20) => println("tom") //apply 构建一个样例对象
      case Person("Kate",19) => println("Kate")
      case Person(name, age) => println(name+":"+"age") //unapply方法,对象属性的提取
      case _ => println("match nothing")
    }
  }

  def main(args: Array[String]): Unit = {
    val p1=Person("tom",20)
    matchDemo(p1)
    val p2 = Person("jerry",10)
    matchDemo(p2)
  }

}
