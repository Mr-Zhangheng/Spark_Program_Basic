package test0521

object tupleTest3 {
  def main(args: Array[String]): Unit = {
    val arr1 = Array(1,2,3,4,5,6,7,8,9,10)
    val arrSum = List(arr1.sum)
    val arrSize = List(arr1.size)
    val res = arrSum.zip(arrSize)
    res.iterator.foreach(println)

    val res2 = arr1.map((_,1)).reduce((x:(Int,Int),y:(Int,Int))=>(x._1+y._1,x._2+y._2))
    println(res2)
  }
}
