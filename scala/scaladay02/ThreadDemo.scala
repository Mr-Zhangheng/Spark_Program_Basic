package scaladay02

object ThreadDemo {
  def main(args: Array[String]): Unit = {

    val arr = 1 to 10
    arr.foreach(println) //
    arr.par.foreach(println) //顺序发生变化


  }
}
