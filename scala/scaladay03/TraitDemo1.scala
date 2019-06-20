package scaladay03

trait work {
  //抽象字段
  var hour: Double
  //带有初始化的字段
  val space = "out"
  //抽象方法
  def workStyle(style: String): String
  //带有实现的方法
  def workContent = {
    println("process data")
  }
}

//class Programmer extends  work{
//  override def workStyle(style: String): String = "message" //此处overwrite可省略
//  hour = 8.0
//  override def workContent: Unit = println("child implement message") //此处overwrite不可省略
//}
//
//  //特质继承一个类
//  trait work1{
//    //抽象字段
//    var hour: Double
//    //带有初始化的字段
//    val space= "out"
//    //抽象方法
//    def workStyle(style:String):String
//    //带有实现的方法
//    def workContent={
//      println("process data")
//    }
//
//    class test extends work1{
//    var hour= 10.0
//
//      override def workStyle(style: String): String = "trait exdents"
//        val obj = new test
//    }
//  }
//
//object TraitDemo1 {
//  def main(args: Array[String]): Unit = {
//    val obj = new test()
//    println("ttt")
//    println(obj.name)
//  }
//}
