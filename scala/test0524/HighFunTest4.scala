package test0524
import scala.collection.mutable.ArrayBuffer
object HighFunTest4 {
  def main(args: Array[String]): Unit = {
    val res = largest(x=>10*x-x*x,1 to 10)
    println(res)
  }
  def largest(fun:(Int)=>Int,inputs:Seq[Int]):Int={
    var arr = ArrayBuffer[Int]()
    for(elem <- inputs)
      arr += fun(elem)
    val maxX = arr.max
    maxX
  }
  def fun(x:Int): Int ={
    val y= 10*x-x*x
    y
  }
}
