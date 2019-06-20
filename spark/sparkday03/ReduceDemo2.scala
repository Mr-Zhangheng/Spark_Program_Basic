package sparkday03

import org.apache.spark.{SparkConf, SparkContext}

object ReduceDemo2 {
  //打印元素分区号
  def m1(index:Int,iterator: Iterator[Int])={
    iterator.map(x=>index+":"+x)
  }

  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("CombineByKey").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)

    //求各科平均成绩
    val rdd = sc.parallelize(List(("english",60),("math",74),("chinese",90),("math",86),("english",90),("math",78)))
    val res = rdd.reduce((x:(String,Int),y:(String,Int))=>(x._1,x._2+y._2)) //tuple类型记得加括号
    println(res)

    val rdd2 = sc.parallelize(Array(10,20,30,50,40),3)
    val list = List(10,20,30)
    list.reduceRight(_+_)
    println(rdd2.partitions.size)
    rdd2.mapPartitionsWithIndex(m1).foreach(println)
    val res1 = rdd2.fold(1000)(_+_) //初始化聚合结果的第一个值
    println(res1)
    val res2 = rdd2.aggregate(1000)(math.max(_,_),_+_)
    println(res2) //输出4000



  }
}
