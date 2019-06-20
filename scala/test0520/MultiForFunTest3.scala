package test0520

object MultiForFunTest3 {
  def main(args: Array[String]): Unit = {
    val str = "Hello"
    var sum: Long = 1L

    val product = (s:String) => {
      val ch = s.toCharArray
      for (i <- ch) {
        sum *= i
      }
      println(sum)
    }
    product(str)
  }
}
