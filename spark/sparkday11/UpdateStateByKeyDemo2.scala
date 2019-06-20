package spark.sparkday11

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object UpdateStateByKeyDemo2 {

  //批次结果累加,最后输出到当前批次为止,所有单词出现次数的累加结果
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("sparkstreamingwc").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //每5秒产生一个批次的数据
    val ssc:StreamingContext = new StreamingContext(sc,Seconds(5))

    //设置检查点目录,维护历史批次数据
    ssc.checkpoint("F://cp20190310")
    //获取实时数据流
    val dstream: ReceiverInputDStream[String] = ssc.socketTextStream("mini1",9000)
    //当前批次数据(pp,1)(tt,1)(tt,1)(aa,1)
    val mapDStream: DStream[(String, Int)] = dstream.flatMap(_.split(" ")).map((_,1))
    //之前的历史数据(pp,4)(tt,9)
    //最后批次累计结果(pp,5)(tt,11)(aa,1)
    val value: DStream[(String, Int)] = mapDStream.updateStateByKey(updateFunc,new HashPartitioner(sc.defaultParallelism),true)
    value.print()

    ssc.start()
    ssc.awaitTermination()
  }

  //自定义更新函数
  //String代表当前批次的一个元素,
  // Sequence代表的是历史信息Seq当前元素的value序列,
  // Option,代表tt元素之前批次的结果
  val updateFunc = (it:Iterator[(String,Seq[Int],Option[Int])])=>{
    it.map(x=>{
      (x._1,x._2.sum + x._3.getOrElse(0))
    })
  }

}
//使用mapWithState替换updateStateByKey


