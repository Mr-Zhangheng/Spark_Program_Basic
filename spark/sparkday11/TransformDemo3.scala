package spark.sparkday11

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object TransformDemo3 {
  def main(args: Array[String]): Unit = {
    //transform 两个用途
    //可以将实时流的处理--基于DStream的处理转变成基于RDD的操作
    //可以通过transform 使用DStream没有的API,使用RDD下的相应的API去解决,例如join
    //等同于foreach,只是类型不同

    //实现黑名单过滤功能,有一道实时数据流,(用户名,用户信息),有一个黑名单
    //根据黑名单对实时的数据流进行过滤,然后进行过滤后数据的统计分析
    val conf = new SparkConf().setAppName("sparkstreamingwc").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //每5秒产生一个批次的数据
    val ssc:StreamingContext = new StreamingContext(sc,Seconds(5))
    //黑名单数据集
    val arr =Array(("kate",true),("mary",true))
    val blackUserRDD = sc.parallelize(arr)
    //获取一道实时数据流, username userinfo
    val rDstream: ReceiverInputDStream[String] = ssc.socketTextStream("mini1",9000)
    val dStream:DStream[(String,String)] = rDstream.map(line=> {
      val arr = line.split(" ")
      val username = arr(0)
      (username,line)
    })

    //实际上使用的是匿名函数
    val result = dStream.transform(userInfoRdd=>{
      val resRDD:RDD[(String,(String,Option[Boolean]))] = userInfoRdd.leftOuterJoin(blackUserRDD)

      //将黑名单用户过滤掉
      val filterRDD = resRDD.filter(tuple2=>{
        if(tuple2._2._2.getOrElse(false))
          false
        else
          true
      })
      //过滤黑名单之后的数据,保持原来的格式返回
      val res: RDD[String] = filterRDD.map(tup=>tup._2._1)
      res
    })
    result.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
