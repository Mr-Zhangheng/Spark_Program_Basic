package scaladay03

//类似Java中的单例对象:不可创建单例对象的对象
//单例对象中的方法/属性类似于Java中的静态方法/属性
object Utils{
  val count = 10
  def utilMethod()={
    println("object method")
  }
}

object ObjectDemo {
  def main(args: Array[String]): Unit = {
   println(Utils.count)
    Utils.utilMethod()


  }
}
