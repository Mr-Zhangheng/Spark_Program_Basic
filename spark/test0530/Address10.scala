package test0530

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Address10 {
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
    val linesUser: RDD[String] =  sc.textFile("F:/user")
    val linesBaseSite: RDD[String] =  sc.textFile("F:/baseSite")
    //获取用户手机号和对应的基站
    val users:RDD[(String,String)] = linesUser.map(x=>{
      val phone = x.split(",")(0)
      val bSite = x.split(",")(2)
      (bSite,phone)
    })
    //获取基站信息
    val baseSite:RDD[(String,(String,String))] = linesBaseSite.map(x=>{
      val bSite = x.split(",")(0)
      val jingdu = x.split(",")(1)
      val weidu = x.split(",")(2)
      (bSite,(jingdu,weidu))
    })
    val userAddrBase:RDD[(String,(String,Option[(String,String)]))] = users.leftOuterJoin(baseSite)
    val userAddr:RDD[(String,Option[(String,String)])] = userAddrBase.map(x=>{
      (x._2)
    })
    userAddr.collect().toBuffer.foreach(println)



  }
}
