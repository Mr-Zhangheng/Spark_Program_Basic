package scaladay01

object demo1 {
  def main(args: Array[String]): Unit= {

    val arr =Array(10,20,30)
    var i = 0
    var sum = 0
    while (i<arr.length) {
      sum += arr(i)
      i += 1
      println(sum)
    }
  }
}
