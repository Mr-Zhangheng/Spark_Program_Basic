package test0524

object ReduceLeftTest2 {
  def main(args: Array[String]): Unit = {
    val arr = Array(8,9,5,2,10,3)
    val maxRes = arr.reduceLeft(_ max _)
    println(maxRes)

    val arr1 = List("abc","def","eff")
    val arr2 = for (elem <- arr1) yield elem + ','
    val str = arr2.reduce(_+_)
    println(arr2)
//    val str = arr2.reduce(_ + _).dropRight(1)
//    println(str)
    println(str.substring(0,str.length-1))

  }
}
