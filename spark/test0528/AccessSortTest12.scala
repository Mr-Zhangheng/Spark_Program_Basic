package test0528

import java.net.URL

import org.apache.spark.{Partitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

import scala.collection.mutable

object AccessSortTest12 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf()
      .setAppName("AccessSort").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)
    val lines = sc.textFile(args(0))
    val reduced: RDD[(String, Int)] = lines.map(x => {
      (x.split("\t")(1), 1)
    }).reduceByKey(_ + _)
    //(subject,count)=>(subject,(url,count))
    val subjectRDD: RDD[(String, (String, Int))] = reduced.map(x => {
      //学科下的模块
      val url = x._1
      //学科信息
      val subject = new URL(url).getHost
      //学科下具体模块的访问次数
      val count = x._2
      (subject, (url, count))
    }).cache()
    val subjects = subjectRDD.keys.distinct().collect()
    val myPatitioner = new SubjectPartition(subjects)
    val partitionedRDD = subjectRDD.partitionBy(myPatitioner)
    val res = partitionedRDD.mapPartitions(it => {
      it.toList.sortBy(_._2._2).reverse.take(3).iterator
    })
    res.saveAsTextFile(args(1))
    sc.stop()
  }
}
class SubjectPartition(subjects:Array[String]) extends Partitioner {
  //定义一个map，存储学科和分区号
  private val subjectIndex:mutable.HashMap[String,Int] = new mutable.HashMap[String,Int]()
  //定义一个变量，用来创建分区号
  var i = 0
  for(subject <- subjects){
    subjectIndex += (subject->i)
    i += 1
  }
  //定义分区数
  override def numPartitions: Int = subjects.length
  //返回分区号
  override def getPartition(key: Any): Int = subjectIndex.getOrElse(key.toString,0)
}


