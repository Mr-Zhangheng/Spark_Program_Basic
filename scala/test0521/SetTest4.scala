package test0521

object SetTest4 {
  def main(args: Array[String]): Unit = {
    val set1 = Set(1,2,3,4,5,6,7,8,9)
    val set2 = Set(5,6,7,8,9,10,11,12)

    val res2 = set1.intersect(set2)
    val res3 = set1.diff(set2)
    val res1 = set1.union(set2)
    println(res1)
    println(res2)
    println(res3)
  }
}
