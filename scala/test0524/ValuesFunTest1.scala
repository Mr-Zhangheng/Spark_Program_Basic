package test0524

object ValuesFunTest1 {
  def value(fun: (Int)=> Int,low:Int,high:Int):Unit ={
    for (elem <- low to high){
      val map = Map(elem->fun(elem))
      map.foreach(println)
    }
}
  def fun(x:Int):Int={
   var  y = x * x
    y
  }

  def main(args: Array[String]): Unit = {
   value(x=>x*x,-5,5)
  }
}
