package scaladay03

import scala.util.Random

object MatchDemo {
  def main(args: Array[String]): Unit = {
    //常量模式匹配
    val x = "scala"
    x match {
      case "spark" => println("spark")
      case "scala" => println("scala")
      case "10" => println("number")
      case _ => println("Match Nothing")
    }

    //匹配不同类型
    val list1 = List("scala",'e',100,true)
    var res = list1(Random.nextInt(4)) //
    println(res)
    res match {
      case "scala" => println("scala")
      case 100 => println("number")
      case true => println("boolean")
      case _ => println("nothing")
    }

    //变量匹配
    //若case 后是小写则被认为是变量--可以匹配任何类型的值;
    // 大写则被视为常量
    val a = 10
    val A =20
    res match {
      case "ch" => println("string")
      case A => println(A)
      case a => println(a)
      case s => println(s)
    }

    //元组的匹配
    var tuple = (10,"ss",true)
    tuple match  {
      //匹配含有三个元素的
      case (a,b,c)=> println("three elem")
      //匹配第一个元素是10的元组
      case (10,a,b) =>println("head 10")
      case (10,_,_) => println("head 10")
      //case )(10,) //tuple元素个数确定,不可以用 _*
      case _ => println("match nothing")
    }

    var list = List(10,20,30)
    list match {
      case 10::Nil => List(10)
      case x::y::z::Nil => println("three elem list")
      //含有至少一个元素的列表
      case x :: tail => println("at less one elem")
    }

    val arr = Array("aa","bb")
    arr match {
        //含有一个元素的数组
      case Array(x) => println("one")
        //含有一个为aa的元素
      case Array("aa") => println("aa")
        //含有至少一个aa的数组
      case Array("aa",_*) => println("multi arr")
    }

    //类型匹配,源代码中较为常见
    val list5 = List("aa",100,true,'f')
    var obj = list5(Random.nextInt(4))
    obj match  {
      case x:Int => println("Int")
      case y:String => println("String")
      case z:Boolean => println("boolean")
      case _ => println("match nothing")
    }




  }
}
