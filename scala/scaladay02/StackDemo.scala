package scaladay02

object StackDemo {

  def main(args: Array[String]): Unit = {
    var s1= scala.collection.mutable.Stack[Int]()
    s1.push(10,20,30)
    s1.pop()
    println(s1)
    s1.iterator.foreach(println)

    val res1 = s1.top //返回栈顶元素
    println(res1)


  }
}
