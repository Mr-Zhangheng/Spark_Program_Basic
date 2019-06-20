package scaladay04

//方法嵌套:方法中定义一个方法,然后调用


object MethodInDemo11 {
  //求阶乘
  def main(args: Array[String]): Unit = {

    def factor(x:Int,res:Int):Int = {
      if(x<=1)
        res
      else factor(x-1,res*x)
    }
    //factor(x,1)

  }
 // println(fa)

}
