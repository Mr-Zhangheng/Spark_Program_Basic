package test0521

object MultiListTest2 {
  def main(args: Array[String]): Unit = {
    val list1 = List("aa","bb")
    val list2 = List(List(1,3,5),List(2,4,6))
    val list3 = list1.flatMap(_+"")
    val list4:List[Int] = list2.flatMap(_.reverse.reverse)
    val list7 = list1.flatten
    list3.foreach(println)
    list4.foreach(println)

    val list5 = List("hello dog","hello xiaobai","hello xiaoran")
    val list6 = list5.flatMap(x => x.split(" "))
    list6.foreach(println)

    val arr1 = Array(1,3,4,5,7,9,8)
    val arrSum1 = arr1.sum
    val arrSum2 = arr1.par.sum
    println(arrSum1)
    println(arrSum2)
  }
}
