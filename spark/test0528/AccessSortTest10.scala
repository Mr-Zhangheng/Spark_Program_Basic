package test0528

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object AccessSortTest10 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("AccessSort").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)

    val rdd = sc.textFile(args(0))
//    val mapRes:RDD[(String,Int)] = rdd.repartition(1).flatMap(_.split("\t"))
//      .filter(_.startsWith("http:")).map((_,1))
//      .reduceByKey(_+_).sortBy(_._1,false)
//      .sortBy(_._2,false)

    val mapRes :RDD[(String,Int)] = rdd.map(x=>{
      val course = x.split("\t")
      (course(1),1)
    }).reduceByKey(_+_)
    val courseRes:RDD[(String,String,Int)] = mapRes.map(x=>{
      val cs = new URL(x._1).getHost
      (x._1,cs,x._2)
    })
    //groupby之后的结果:(cs,compactbuffer(3个元素))-->对value进行处理-->先将三元组变成集合
    val res = courseRes.groupBy(_._2).mapValues(_.toList.sortBy(_._3).reverse.toBuffer).take(3)
    res.foreach(println)
    sc.stop()
  }
}
