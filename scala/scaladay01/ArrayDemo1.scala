package scaladay01

import scala.collection.mutable.ArrayBuffer

object ArrayDemo1 {
  def main(args:Array[String]): Unit= {
    //定长数组
    val arr = Array(10, 50, 20, 30)
    val arr2 = new Array[Int](10) // 自动用0初始化

    //访问数组
    println(arr(1))
    arr(1) = 8
    println(arr(1))

    //数组遍历
    for (elem <- arr2)
      println(elem)
    for (i <- 0 until arr.length)
      println(arr(i))

    //数组基本方法
    println(arr.sum)
    println(arr.max)
    println(arr.min)
    println(arr.sorted.toBuffer) //升序排序
    println(arr.sorted.reverse.toBuffer) //降序
    println(arr.sortWith(_ > _).toBuffer)
    println(arr.sortWith((x: Int, y: Int) => x > y))


    //变长数组,不用给出长度
    val arr3 = ArrayBuffer[String]()
    val arr4 = new ArrayBuffer[Double]()
    println(arr4)
    println(arr4.length)
    //追加元素
    arr3 += "java"
    arr3 += ("scala", "python")
    println(arr3)
    //追加集合中的元素
    arr3 ++= Array("c", "C++")
    println(arr3)
    //append
    arr3.append("PHP")
    println(arr3)
    //insert,第几个元素之前
    arr3.insert(2, "R")
    println(arr3)
    //删除操作
    //从尾部删除几个元素
    arr3.trimEnd(1)
    arr3.trimStart(1)
    println(arr3)
    //从第几个开始删除,删除几个
    arr3.remove(2, 1)
    println(arr3)
    //定长数组与变长数组的转换
    val arr5 = Array(30, 50)
    arr5.toBuffer
    arr3.toArray

    //数组的构建
    //for推导
    //map--映射
    val arr6 = Array(1, 2, 3, 5, 8, 7)
    for (elem <- arr6) yield elem + 10
    val res = arr6.map((x:Int)=>x+10)
    println(arr6.toBuffer)
    println(res.toBuffer)
    val res1 = arr6.filter((x:Int)=>x%4==0)
    println(res1.toBuffer)

    //高阶数组--矩阵--多维数组
    //2*2数组
    val matrix = Array.ofDim[Int](2,2)
    for (i <- 0 to 1;j <-0 to 1)
      matrix(i)(j)=i*j

    println(matrix(0).toBuffer)

    //定义不规则数组
    val multiArr = new Array[Array[Int]](3)
    multiArr(0) = new Array[Int](2)
    multiArr(1) = new Array[Int](5)
    multiArr(2) = new Array[Int](1)

  }

}
