package scaladay04

object HighFunDemo10 {

  //定义方法,返回值是一个函数
  def printFunInfo(name:String,count:Int):(String,Int)=>Unit = {
    //定义匿名函数,仅限方法内调用
        val name1 = "hh" + name

    (name:String,count:Int) => println(s"$name1 : $count")
  }

  def main(args: Array[String]): Unit = {
    //传入参事是函数
    val list = List("a","bb","ccc")
    list.map((x:String)=>(x,x.length)) //String类型可以省略
    //list.map((_,_.lenth)) //一般超过两个参数时不用_;前后_可能会产生歧义
    list.foreach(println)
    //返回值--最后一条语句是函数
    val f1 = printFunInfo("highFun",2)
    println(f1.getClass)
    f1("highFun",2)

  }
}
