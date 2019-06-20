package spark.sparkday12

import java.util
import org.apache.kafka.common.TopicPartition

object JedisOffset {

  def apply(groupId: String) = {
    //创建Map形式的Topic,Partition,Offset
    var formdbOffset = Map[TopicPartition,Long]()
    //获取Jedis连接
    val jedis1 = JedisConnectionPool.getConnection()
    val topicPartitionOffset: util.Map[String, String] = jedis1.hgetAll(groupId)
    // 导入隐式转换
    import scala.collection.JavaConversions._
    // 将Redis中的Topic下的partition中的offset转换成List
    //list中元素（String,String）,第一个存放的topic-0,第一个存放topic-0下的偏移量，
    val topicPartitionOffsetlist: List[(String, String)] =
    topicPartitionOffset.toList
    // 循环处理所有的数据
    for (topicPL <- topicPartitionOffsetlist) {
      val split: Array[String] = topicPL._1.split("[-]")
      formdbOffset += (
        new TopicPartition(split(0), split(1).toInt) -> topicPL._2.toLong)
    }
    formdbOffset
  }
}
