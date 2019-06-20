package scaladay02

import scala.collection.immutable.TreeMap
import scala.collection.mutable._

object MapDemo1 {
  def main(args: Array[String]): Unit = {
    //map构建
    //默认不可变
    val map1 = Map("tttm"->20,"jey"->18)
    val map2 = Map(("tom",20),("jerry",18)) //元组形式
    //可变类型
    val map3 = Map(("tom",20),("jerry",18)) //元组形式

    //map访问
    println(map2("tom")) //若键不存在会报异常
    //先判断再打印,不会报异常
    if(map2.contains("tom")) {
      println(map2("tom"))
    }
    //getOrElse 此方法最好,实际上是通过第四种方法实现的
    println(map2.getOrElse("tom1",0))
    //若键存在返回some,不存在返回None
    println(map2.get("tom"))
    val res = map2.get("tom").get
    println(res)

    //修改:添加/删除
    //添加顺序随机,无大小顺序,无字典顺序
    map3("mary") = 22 //添加
    map3("tom") = 25 //修改,键存在时
    map3.update("kate",19) //添加/更新--看键是否存在
    map3.update("tom",10)
    map3 +=("dim"->21) //可以是元组,可以是键值对
    map3 ++= map1
    map3 -= ("dim") //只需键即可
    map3 --= Array("kate","tom")

    //遍历map
    for(elem <- map3)
      println(elem)
    for ((k,v) <- map3)
      println(k+":"+v)
    for (k <-map3.keySet)
      println(k+"="+map3(k))

    //hashmap,元素个数大于等于5时 使用的才是HashMap
    var hashMap = HashMap("tom"->20,"jry"->18)
    println(hashMap.getClass)
    println(hashMap.getOrElse("jry",0))
    hashMap.put("tom1",22)
    hashMap += (("tom2",23)) //注意是两个括号,键值对是用括号括起来的
    hashMap = hashMap.drop(1) //删除几个元素
    hashMap.foreach(println)
    hashMap.foreach((x:(String,Int))=>println(x)) //元组形式

    //treemap,有序--按键大小排序,只有不可变的集合
    val treeMap = TreeMap(2->"JAVA",5->"SCALA",1->"PYTHON")
    treeMap.foreach(println )
    //treeMap += (4 -> "C++")

  }
}
