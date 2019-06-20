package scaladay02
//
//import scala.collection.immutable.HashSet.HashSet
//import scala.collection.mutable.HashSet.HashSet1

object SetDemo1 {

  def main(args: Array[String]): Unit = {
//    var set1 = new HashSet[String]()
//    set1 = set1 + "aa"
//    set1 = set1 ++ Set("aa","bb","cc")

    //构建可变的set
    val set2 =scala.collection.mutable.HashSet[Int]()
    set2 += 10
    set2.add(20)
    set2 -= 10
    set2.remove(20)

    //并交差
    val set3 =scala.collection.mutable.HashSet[Int]()
    set3.add(20)
    set3 add 30
    val res1 = set2 union(set3)
    println(res1)
    val res2 = set2 ++ set3 //同union
    //交集
    val res3 = set2.intersect(set3)
    val res4 = set2 & set3
    println(res3)
    //差集
    val res5 = set2.diff(set3)
    val res6 = set2 &~ set3
    println(res5)

    //遍历
    for (elem <- set3)
      println(elem)
    val it = set2.iterator
    it.foreach(println)



  }
}
