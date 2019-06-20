package sparkday03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object JoinDemo1 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("CombineByKey").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)

    //求各科平均成绩
    val rdd = sc.parallelize(List(("english",60),("math",74),("chinese",90),("math",86),("english",90),("math",78)))
    val rdd2 = sc.parallelize(List(("english",60),("math",86),("english",90),("math",78),("art",88)))

    val rdd3 = rdd.join(rdd2)
    //匹配不上用None,匹配的上用Some(Int)
    val rdd4:RDD[(String,(Int,Option[Int]))] =rdd2.leftOuterJoin(rdd)

    //cogroup
    val rdd6 = rdd.cogroup(rdd2) //tuple元素参数为2个
    val rdd7 = rdd.cogroup(rdd2,rdd)  //三个连接 结果tuple元素为三个

   // rdd7.sortBy()

    println(rdd3.collect().toBuffer)//第二个参数Value是tuple
    println(rdd4.collect().toBuffer)
    println(rdd6.collect().toBuffer)
  }

}
