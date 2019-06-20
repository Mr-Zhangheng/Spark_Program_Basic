package scaladay04



object CpalesFunDemo13 {

  val f1 = (x:Int) => x+100
  //闭包
  val a =100
  val f2 =(x:Int)=>x+a

  def main(args: Array[String]): Unit = {
    println(f1(100))
    println(f2(20))


  }
}
