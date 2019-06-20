package scaladay04

object PatialFunDemo7 {

  val fun1:PartialFunction[String,String] ={ //只能有一个参数
    case "spark"=>"scala"
    case "hadoop"=> "java"
  }

  val fun2:PartialFunction[String,String] = {
    case "kafka"=> "scala"
    case "scala"=>"success"
  }

  def main(args: Array[String]): Unit = {
    println(fun1("spark")) //输出scala
    println(fun1.apply("hadoop")) //输出java
    println(fun1.isDefinedAt("flink")) //判断当前的对象在偏函数实现中是否有相应的处理逻辑

    val funres = (fun1.orElse(fun2))
    // 将多个偏函数串联起来,形参一个新的偏函数,偏函数的参数类型和返回类型是一致的
    println(funres("spark"))
    println(funres("kafka"))
    println(funres.getClass)

    val funres2 = fun1.andThen(fun2)
    //把多个偏函数连接起来,多级连接
    println(funres2.getClass)
    println(funres2("spark"))

  }
}
