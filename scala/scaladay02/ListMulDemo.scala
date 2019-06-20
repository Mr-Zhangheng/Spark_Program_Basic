package scaladay02

import scala.collection.mutable.ListBuffer

object ListMulDemo {
  def main(args: Array[String]): Unit = {
    //可变列表
    val list1 = ListBuffer[String]()
    list1 += "scala"
    list1.append("java","speak")
    list1 ++= List("flink","kafka")
    println(list1(0))



  }
}
