package spark.sparkday12

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, State, StateSpec, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

object MapWithStateDemo1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("sparkstreamingwc").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //每5秒产生一个批次的数据
    val ssc:StreamingContext = new StreamingContext(sc,Seconds(5))

    //设置检查点目录,维护历史批次数据
    ssc.checkpoint("F://cp20190310")
    //获取实时数据流
    val dstream: ReceiverInputDStream[String] = ssc.socketTextStream("mini1",9000)

    val mapedDStream: DStream[(String, Int)] = dstream.flatMap(_.split("")).map((_,1))

//    val specState=StateSpec.function[String,Int,Long,(String,Long)](mappingFun)
//      mapedDStream.mapWithState(specState)
//    //批次累加功能
//    val mappingFun = (key:String,values:Option[Int],state:State[Long])=>{
//      //获取key对应的历史信息
//      val historyState = state.getOption().getOrElse(0L)
//      //累加历史和当前的信息
//      val curState = values.getOrElse(0)+ historyState
//      //更新State
//      state.update(curState)
//      (key,curState)
//    }

  }
}
