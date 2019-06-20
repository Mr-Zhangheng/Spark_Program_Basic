package test0522

object MatchTest4 {
  def main(args: Array[String]): Unit = {
    var list = List(8,2,1)  //(8,2,5) //(8,3)
    list match {
      case List(1,_*) => println("List 以1开头")
      case List(a,b,c) if(b==2) => println("List含有三个元素，并且第二个元素是2")
      case List(a,b) if(b==3) => println("List含有两个元素，并且最后一个元素是3")
      case _ => println("nothing match")
    }
  }
}
