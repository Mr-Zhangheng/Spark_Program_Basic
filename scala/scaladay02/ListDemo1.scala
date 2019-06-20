package scaladay02

object ListDemo1 {
  def main(args: Array[String]): Unit = {
    //列表的构建
    val list1 = List("a","bb","ccc")
    //空列表
    val empty = Nil
    val list2 = List()
    println(list2.getClass)
    //::右结合--从右向左计算
    val list3 = "a"::"bb"::"ccc"::Nil
    val list4 = ("a"::("bb"::("ccc"::Nil)))
    var list15 = List("a","bb","ccc","dddd")
    println(list15.getClass)

    //访问列表
    //println(list15(0))
    //遍历
    for (elem <- list15)
    //  println(elem)
    //list15.foreach(println)
    //list15.productIterator.foreach(println) //该方法不对
    list15.iterator.foreach(println)
    //元素添加
    list15 = "e1"::list15 //头部追加
    list15 = "e2" +: list15 //头部追加
    list15.foreach(println)
    list15 :+ list15 +"e3" //左结合 尾部追加
    list15.foreach(println) //尾部追加
    //删除
    list15.drop(2) //删除个数
    //基本操作
    println(list15.isEmpty)
    println(list15.head) //只返回一个元素
    list15.tail //是一个集合--除去第一个元素之外的
    list15.sorted //默认升序
    list15.sorted.reverse
    list15.size //长度

    //列表的拆分和合并
    val List(a,b,c)=List("aa","bb","cc")
    println(a)
    val rest = list15.splitAt(2) //在哪一位进行切分
    println(rest._1.foreach(println)) //会打印小括号出来:因为内部println
    rest._1.foreach(println)

    list15 take 2
    println(list15.take(2))
    list15 drop(2)
    list15 = list15.dropWhile(_.startsWith("aa"))
    println(list15)
    list15.foreach(println)

    var list6 = List(10,20,30,4,5,6,7)
    list6 = list6.map(_+10)
    list6 = list6.map((x:Int)=>x+10)
    // fold reduce foldLeft foldRight reduceLeft reduceRight
    list6.fold(0)((x:Int,y:Int)=>x+y)
    list6.fold(0)(_+_) //list6所有元素相加之和,0+和 由foldleft累加实现
    list6.reduce(_+_) //同fold

    var list8 =List("abc","def")
    //前提 集合里面的元素依然是一个集合,内层集合中的元素提取出来
    var list9 = list8.flatMap(_+"nn")
    val list10 = List(List(1,2,3),List(4,5,6))
    list9.foreach(println)
    //输出: 3 2 1 6 5 4
    // 相当于flattern+map,先map后flatten
    val res4:List[Int] = list10.flatMap(_.reverse)
    //List();
    val res6 = list10.flatten
    res6.foreach(println)
    //flatten只对集合有效,压平--压成一层--map之后生成的是集合
    val list11 = List(1,2,3,4)
    list11.flatMap((x:Int)=>List(x))

    list11.find(_%2==0)
    list11.takeWhile(_%2==0) //类似以filete
    list11.foreach(println)
    //判断集合中所有元素是否同时都满足某条件
    list11.forall(_>10)


  }
}
