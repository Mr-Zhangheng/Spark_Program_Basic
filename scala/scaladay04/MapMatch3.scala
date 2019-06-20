//package scaladay04
//
//def mapMatch(x:Any) => x match {
//  //类型擦除,Array除外,其余集合对象都可以擦除
//  case m1:Map[Int,Int] => println("match")
//  case _ => println("no match")
//}
//
//object MapMatch3 {
//  def main(args: Array[String]): Unit = {
//    val map1 = Map("scala"->1,"java"->2) //map是个string类型的
//    map1 match {
//        //类型匹配
//      case m1:Map[String,Int] => println("match")
//      case _ => println("no match")
//    }
//
//    val map2 = Map(1->1)
//    mapMatch(map2)
//    val map3 = Map("scala"=> spark)
//    mapMa
//  }
//}
