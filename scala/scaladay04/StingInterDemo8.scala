package scaladay04

object StingInterDemo8 {
  def main(args: Array[String]): Unit = {
    //s 字符串中引用变量
    val str = "scala"
    println(s"$str is easy")

    //f printf
    val d = 3.1415
    println(f"result is $d%3.2f") //两位小数,总计3位

    //raw 对字符串内容不做任何处理,原样输出
    println("scala \"spark\"")
    println(raw"scala \'spark\'")

    //三引号 --支持换行
    println(""" scala "spark" """)

  }
}
