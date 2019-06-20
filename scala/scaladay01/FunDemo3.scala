package scaladay01

object FunDemo3 {
  //函数定义方式
  val f1 = (x:Int,y:Int) => x*y
  val f2:(Int,Int)=>Int = (x,y)=>x*y

  val f3 = new Function2[Int,Int,Int] {
    override def apply(v1: Int, v2: Int): Int = v1*v2
  }


  def main(args: Array[String]): Unit = {
    f1(2,3)

    val arr =Array(10,20,30)
    val res1 = arr.map(_*10)
    val res2 = arr.map((x:Int)=>x*10)
    println(res1.toBuffer)
    println(res2.toBuffer)
    f3(10,15)
  }

}
