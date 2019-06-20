package test0524

import java.io.File
import scala.io.Source

class myFile(val from: File) {
  def read = Source.fromFile(from).mkString
}

object RichFile1 {
  implicit class myClass(from: File) {
    def read = Source.fromFile(from).mkString
}
}
//
//object ImplicitCurrentTest6 {
//  def main(args: Array[String]): Unit = {
//    //import test0524.RichFile.myClass
//    //println(new myFile("F:/result.txt").read)
//  }
//}

