package scaladay04

import java.io.PrintWriter

import scala.io.Source

object FileProcessDemo9 {
  def main(args: Array[String]): Unit = {
   //读文件--按行读取--Source包
    val source = Source.fromFile("F:/result.txt")
    //返回文件访问的入口,迭代器
    val it = source.getLines()
//    for (line <- it)
//      println(line)

    //读文件--按字符读取
//    for (c <- source)
//      println(c)

    //按单词读取
    val words = source.mkString.split(" ") //先转字符串再分割
    for (word <- words)
      println(word)

    //读取网络文件
    val source1 = Source.fromURL("http://www.baidu.com")
    for(line <-source1.getLines()){
      println(line)
    }

    //写文件
    val writer = new PrintWriter("res.txt")
    for(elem <- Array("scala","spark"))
      writer.println(elem)
    writer.close()


  }
}
