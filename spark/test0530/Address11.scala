package test0530

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Address11 {
  def main(args: Array[String]): Unit = {
    //统计每个用户在每个基站停留时间
    //根据停留时间进行排序
    //取出排在前两位的基站信息
    //基站信息转换成经纬度--join操作

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

    val rdd = linesUser.map(x=>{
      val arr = x.split(",")
      val phoneNum = arr(0)
      val time = arr(1).toLong
      val loc = arr(2)
      val event = arr(3)
      val time_length = if(event == 1) -time else time
      ((phoneNum,loc),time_length)

    })
    //计算每个用户在每个基站停留时间
    val rdd1 = rdd.reduceByKey(_+_)

    //对于rdd1数据做数据转换--->(loc,(phoneNum,time_length))
    val rdd2 = rdd1.map(x=>{
      (x._1._2,(x._1._1,x._2))
    })

    val rdd3 = linesBaseSite.map(x=>{
      val arr = x.split(",")
      val loc = arr(0)
      val jingdu = arr(1)
      val weidu = arr(2)
      (loc,(jingdu,weidu))
    })
    //数据拼接
    val wholeInfo = rdd2.join(rdd3)

    //数据类型转换
    val resData = wholeInfo.map(x=>{
      val phoneNum = x._2._1._1
      val time_length = x._2._1._2
      val xy = x._2._2
      (phoneNum,time_length,xy)
    })

    //排序,按在每个基站的停留时长
    val res = resData.groupBy(_._1).mapValues(_.toList.sortBy(_._2))
    res.collect().toBuffer.foreach(println)


  }
}
