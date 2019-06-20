package scaladay04

object implicitParam{
  trait Adder[T]{
    def add(x:T,y:T):T
  }
  //定义隐式变量 类型是Adder类型
  implicit val a = new Adder[Int] {
    override def add(x: Int, y: Int): Int = x+y
  }

  def add1(x:Int,y:Int)(implicit adder:Adder[Int])={
    adder.add(x,y)
  }


}

object ImplicitDemo15 {
  def main(args: Array[String]): Unit = {
    val a:Int =100
    val b:Double = a
    //强制类型转换
    val c:Int = b.toInt
    //隐式转换方法--对double类型进行增强,自动转换成Int类型
    implicit def doubleToInt(x:Double) :Int= x.toInt
    val d:Int =b
    //隐式转换方法定义: 使用implicit修饰,隐式转换方法的参数是需要增强需要扩展的类型,返回值是增强后的类型

    d.times(println("aa"))
  //  println(add1(10,20))

  }

  //隐式转换类:隐式类不能单独定义,只能有一个非隐式参数(需要增强的类)
  //在隐式类中,定义多个方法
  implicit class IntWithTimes(x:Int){
    def times[A](f: =>A)={
      def loop(x:Int): Unit = {
        if(x>0) {
          f
          loop(x-1)
        }
      }
      loop(x)
    }
  }

}
