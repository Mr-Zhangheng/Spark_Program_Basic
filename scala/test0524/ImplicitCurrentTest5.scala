package test0524
import java.io.File
import scala.io.Source

//隐式的增强File类的方法
class RichFile(val from: File) {
  def read = Source.fromFile(from.getPath).mkString
}

object RichFile {  //隐式转换方法
  implicit def file2RichFile(from: File) = new RichFile(from)
}

object ImplicitCurrentTest6 {
  def main(args: Array[String]): Unit = {
    import test0524.RichFile._
    println(new File("F:/result.txt").read)
  }
}
