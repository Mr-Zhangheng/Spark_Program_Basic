package scaladay01

object FunDemo2 {

  //定义方法
  def add(x:Int,y:Int) ={x+y}
  //参数列表的形参(柯里化)
  def add1(x:Int)(y:Int) = {x+y}
  //带有默认参数的方法
  def printMsg(msg:String = "scala")= println(msg)

  def add2(x:Int =2,y:Int = 3,z:Int) = x+y+z
  //可变长参数应该是最后一个参数,x是个数组
  def add3(x:Int*) = {
    for (elem <- x)
      println(elem)
  }

  //无参方法
  def printMsg2() = println("scala")
  def printMsg3 = println("scala")

  def main(args:Array[String]):Unit = {
    add3(10,20,30)
    printMsg2()
    printMsg2
    printMsg3

  }

}
