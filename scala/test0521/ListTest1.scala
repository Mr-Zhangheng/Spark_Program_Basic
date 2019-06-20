package test0521

object ListTest1 {
  def main(args: Array[String]): Unit = {
    val list1 = List(50,3,5,7,9,10,2,4,6,8,10,25,84,32,21)
    val list2 = list1.map(_*2)
    val list6 = list1.filter(_%2 == 0)
    val list4 = list1.sorted
    val list5 = list1.sorted.reverse
    list2.foreach(print)
    println()
    list6.foreach(print)
    println()
    list4.foreach(print)
    println()
    list5.foreach(print)

    val arr = Array(2,3,4)
    arr.iterator.toList
  }
}
