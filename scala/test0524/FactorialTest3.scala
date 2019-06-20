package test0524

import scala.collection.mutable.ArrayBuffer

object FactorialTest3 {
  def main(args: Array[String]): Unit = {
    println(factorial(2))
  }
  def factorial(x:Int):Int={
    var arr = ArrayBuffer[Int]()
    for(elem <- 0 to x){
      if(elem == 0)
        arr += 1
      else
        arr += elem
    }
    val res = arr.reduceLeft(_ * _)
    res
  }
}
