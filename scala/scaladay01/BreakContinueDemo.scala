package scaladay01
import scala.util.control.Breaks._

object BreakContinueDemo {

  def main(args: Array[String]): Unit = {

    //条件语句和布尔语句实现continue,break功能

    var flag = false
    for (i <- 1 to 10 if (i % 2 == 0 && !flag)) {
      println(i)
      if (i == 5)
        flag = true
    }

    //实现break功能
    breakable {
      for (i <- 1 to 10) {
        println(i)
        if (i == 5)
          break
      }
    }

    //实现continue功能
    for (i <- 1 to 10)
      breakable {
        if (i == 8)
          break
        println(i)
      }


  }
}
