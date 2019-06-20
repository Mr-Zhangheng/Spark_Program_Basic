package test0520
import scala.util.control.Breaks._
object BreakContinueTest {
  def main(args: Array[String]): Unit = {
    //输出偶数
    var flag = false
    for (i <- 1 to 10 if (i % 2 == 0 && !flag)) {
      //flag == true
      println(i)
      flag == true //位置无所谓  为什么??
    }
    breakable {
      for (i <- 1 to 10) {
        if (i == 5)
          break
        println(i)
      }
     for (i <- 1 to 10 ){
       breakable {
         if (i == 5)
           break
         println(i)
       }
     }
    }
  }
}
