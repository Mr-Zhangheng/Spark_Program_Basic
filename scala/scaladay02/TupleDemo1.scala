package scaladay02

object TupleDemo1 {
  def main(args: Array[String]): Unit = {
      //构建元组
    val t1 = ("java","scala",10)
    val t2 = new Tuple4("scala",1,2,3)
    //访问,从1开始
    println(t1._3)
    println(t2._1)
    //通过下标访问,从0开始
    println(t1.productElement(0))
    //遍历 了解即可
    for (elem <- t1.productIterator) //需要拿到迭代器
      println(elem)
    t1.productIterator.foreach(println)
    //拉链,对集合进行拉链操作--所有序列都可以进行该操作;结果元素都是一个元组
    //zip
    val arr = Array("java","scala","c++")
    val arr2 = Array(1,2,3)
    val arr3 = Array(1,5,3,4)
    val res = arr.zip(arr2)
    res.foreach(println)
    val res2 = arr.zip(arr2).zip(arr3)
    res2.foreach(println)
    //对元组元素的处理
    val res3 = res.map((x:(String,Int))=>(x._1+"m",x._2))
    res3.foreach(println)

  }
}
