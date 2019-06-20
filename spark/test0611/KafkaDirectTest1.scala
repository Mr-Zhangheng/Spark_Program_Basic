package spark.test0611

import java.lang

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.codehaus.jackson.map.deser.std.StringDeserializer
import redis.clients.jedis.Jedis
import spark.sparkday12.{JedisConnectionPool, JedisOffset}

object KafkaDirectTest1 {
  //过滤日志
  Logger.getLogger("org").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("kafka-mysql")
    //每秒每个分区kafka拉取信息的速率
      .set("spark.streaming.kafka.maxRatePerPartition","100")
    //序列化
      .set("spark.serilizer","org.apache.spark.serializer.KryoSerializer")
    val ssc = new StreamingContext(conf,Seconds(3))
    //启动一参数设置
    val groupId = "test001"
    //kafka配置参数
    val kafkaParams = Map[String,Object](
      //kafka的key和value的解码方式
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> groupId,
      //从头开始消费
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: lang.Boolean)
    )
    val topics = Array("test")
    //启动二参数设置(获取Redis中的kafka偏移量)
    var formdbOffset: Map[TopicPartition,Long] = JedisOffset(groupId)
    //拉取kafka数据
    val stream: InputDStream[ConsumerRecord[String,String]] =
      //首先判断一下,要消费的kafka数据是否是第一次消费,之前有没有消费过
      if (formdbOffset.size == 0) {
        KafkaUtils.createDirectStream[String,String](
          ssc,
          LocationStrategies.PreferConsistent,
          ConsumerStrategies.Subscribe[String,String](topics,kafkaParams)

        )
      } else {
        //第一次消费数据,没有任何的消费者信息数据
        KafkaUtils.createDirectStream(
          ssc,
          LocationStrategies.PreferConsistent,
          ConsumerStrategies.Assign[String,String](
            formdbOffset.keys,kafkaParams,formdbOffset
          )
        )
      }
    //数据偏移量处理
    stream.foreachRDD({
      rdd =>
        //获取偏移量对象数组
        val offsetRange: Array[OffsetRange] =
          rdd.asInstanceOf[HasOffsetRanges].offsetRanges
        //逻辑处理
        rdd.map(_.value())
          .map((_,1))
          .reduceByKey(_+_)
          .foreach(println)
        //偏移量存入Redis
        val jedis: Jedis = JedisConnectionPool.getConnection()
        for(or <- offsetRange) {
          jedis.hset(groupId,or.topic + "-" + or.partition,or.untilOffset.toString)
        }
        jedis.close()
    })
    //启动Streaming程序
    ssc.start()
    ssc.awaitTermination()
  }
}
