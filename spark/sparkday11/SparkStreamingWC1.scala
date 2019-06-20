package spark.sparkday11

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingWC1 {
  def main(args: Array[String]): Unit = {
   //上下文环境
    //StreamingContext

    //保证线程个数大于等于2
    val conf = new SparkConf().setAppName("sparkstreamingwc").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //每5秒产生一个批次的数据
    val ssc:StreamingContext = new StreamingContext(sc,Seconds(5))
    //获取一道实时的socket数据流
    //使用netcat模拟实时socket流 nc -lk 9000
    val dstream: ReceiverInputDStream[String] = ssc.socketTextStream("mini1",9000)
    //dstream底层是一系列的RDD,每个RDD代表收到的一个元素,就是一行单词流
    //仅处理当前批次操作
    val res: DStream[(String, Int)] = dstream.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)

    res.print()

    //提交任务到集群执行
    ssc.start()
    //等待当前任务处理完,再终止程序的运行--优雅的停止
    ssc.awaitTermination()
  }
}


