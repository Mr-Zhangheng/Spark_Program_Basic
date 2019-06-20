package test0522

class Point {

}
object Point {
  var sum:Int = 0
  def apply(args1:Int,args2:Int)={
    sum = args1 + args2;sum
  }
}
object CompanionObjectTest2 {
  def main(args: Array[String]): Unit = {
    var point = Point(3,4)
    println(Point(3,4))
  }
}
