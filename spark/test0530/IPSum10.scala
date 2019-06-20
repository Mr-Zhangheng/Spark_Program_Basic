package test0530

import org.apache.avro.SchemaBuilder.ArrayBuilder
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

import scala.collection.mutable.ArrayBuffer

object IPSum10 {
  def main(args: Array[String]): Unit = {
    //创建一个配置信息对象
    //设置的应用程序名称
    val conf:SparkConf = new SparkConf()
      .setAppName("IPCount")
      .setMaster("local[*]")
    //local 使用本地一个线程模拟机群执行任务
    //local[num]:使用num个线程去模拟集群执行任务
    //local[*] 使用所有空闲的线程去模拟集群执行任务

    //spark上下文对象,执行spark程序的入口
    val sc:SparkContext = new SparkContext(conf)

    //读取数据源
    //通过参数设置源文件路径,HDFS文件/本地文件
    //RDD集合里的每一个元素即为文件里的一行
    //textFile对于一个或者多个文件
    val lines: RDD[String] =  sc.textFile("F:/cdn")
    //IP的rdd集合
    val IPs:RDD[String] = lines.map(x=>{
      (x.split(" ")(0))
    })
    //独立IP
    val IP = IPs.distinct()
    //独立IP个数
    val IPCount = IP.count()
    println(IPCount)

    //统计每个视频独立IP数
    //获取IP和相应的访问URL
    val videoIPURL:RDD[(String,String)] = lines.map(x=>{
      //获取IP
      val vdeIP = x.split("\"")(0).split(" ")(0)
      //获取URL和访问的独立IP
      val vdeURL = x.split("\"")(1).split(" ")(1)
      (vdeURL,vdeIP)
    }).distinct()
    //每个视频独立IP
    val videoIPCount:RDD[(String,Int)] = videoIPURL.map(x=>{
      (x._1,1)
    }).reduceByKey(_+_)
    videoIPCount.collect().toBuffer.foreach(println)
    println(videoIPCount.count())

    //统计一天中每个小时的流量
    //获取访问时间中的小时
    val hours:RDD[(Int,String)] = lines.map(x=>{
      val hour = x.split("\"")(0).split(" ")(3).split("/")(2).split(":")(1).toInt
      val ipHour = x.split(" ")(0)
      (hour,ipHour)
    })
    //每个小时的流量(pv)
    val hourCount:RDD[(Int,Int)] = hours.map(x=>{
      (x._1,1)
    }).reduceByKey(_+_)
    println(hourCount.collect().toBuffer)

    sc.stop()

  }

}
