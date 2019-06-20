package test0520

object MultiForTest2 {
  def main(args: Array[String]): Unit = {
    val str = "Hello"
    val ch = str.toCharArray
    var sum: Long = 1L
    //for循环遍历本身会变成char类型
    for (elem <- ch) {
      sum *= elem
  }
    println(sum)
  }
}
