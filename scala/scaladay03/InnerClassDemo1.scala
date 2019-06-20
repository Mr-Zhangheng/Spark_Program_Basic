package scaladay03

class outer {
  def getMsg = {
    //直接访问内部对象/属性
   // inner.printMsg
    inner.name1


  }

  class inner{
    var name = "inner"
    def printMsg = println("print")
  }
  //内部对象
  object inner {
    var name1 = "inner objoct"

  }

}

object InnerClassDemo1 {

  def main(args: Array[String]): Unit = {
    val obj =new outer
    val objinner = new obj.inner
    println(obj.inner.name1)
    // println(obj.inner.printMsg)
    println(objinner.name) //通过内部类对象访问
    println(objinner.printMsg)
  }

}
