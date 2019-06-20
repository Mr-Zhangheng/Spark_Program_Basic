package scaladay02

object QueueDemo {

  def main(args: Array[String]): Unit = {
    val q1 = scala.collection.mutable.Queue[String]()
    q1 += "aa"
    q1 ++= List("dd","ff")
    q1 +: "gg"

    q1.take(1) //取第一个元素
    q1.size //元素个数

    q1.iterator.foreach(println) //遍历
    q1.front //对头
    q1.last //队尾
    q1.tail.front //第二个元素
    //尾部追加
    q1.enqueue("hh")






  }
}
