package scaladay03

abstract class Animal1{
  val name:String
  def skill //无参返回Unit类型
}

object NonaeClassDemo1 {
  def main(args: Array[String]): Unit = {
    //定义匿名类并创建对象
    var dog = new Animal1 {
      override val name: String = "dogxb"
      override def skill: Unit = println("bit")
    }
    //使用对象去调用方法/属性
    dog.name
    dog.skill
  }
}
