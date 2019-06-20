package spark.sparkday08

import org.apache.spark.sql.{DataFrame, Encoder, Encoders, SparkSession}
import org.apache.spark.sql.expressions.Aggregator
import sparkday06.People

//输入结果类型(表中的每条数据),中间结果类型,返回结果类型
case class Average(var sum:Int,var count:Int)
class UDAF2 extends Aggregator[People,Average,Double]{
  //初始化中间结果
  override def zero: Average = Average(0,0)

  //分区内聚合,把每一条数据聚合到中间结果对象
  override def reduce(b: Average, a: People): Average = {
    b.sum = b.sum + a.age
    b.count = b.count + 1
    b
  }

  //分区结果汇总
  override def merge(b1: Average, b2: Average): Average = {
    b1.sum = b1.sum + b2.sum
    b1.count = b1.count + b2.count
    b1
  }

  //返回聚合结果
  override def finish(reduction: Average): Double = reduction.sum/reduction.count.toDouble

  override def bufferEncoder: Encoder[Average] = Encoders.product

  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}

object UDAFDemo3 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("UDFDemo").master("local[*]").getOrCreate()
    val frame: DataFrame = sparkSession.read.json(args(0))
    frame.as("Average")
    //先注册
//    sparkSession.udf.register("Average",new UDAF2)
    frame.createOrReplaceTempView("t_people")
    sparkSession.sql("select myAvg(age) from t_people").show()

    sparkSession.stop()


  }
}
