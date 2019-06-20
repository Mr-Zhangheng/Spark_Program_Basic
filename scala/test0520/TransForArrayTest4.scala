package test0520

object TransForArrayTest4 {
  def main(args: Array[String]): Unit = {
    var arr = Array(1,2,3,4,5)
    if (arr.length < 2) {
      println(arr.toBuffer)
    }
    else {
      for (i <- 0 until (arr.length - 1,2)) {
        val tmp = arr(i)
        arr(i) = arr(i+1)
        arr(i+1) = tmp
      }
      println(arr.toBuffer)
    }
  }
}
