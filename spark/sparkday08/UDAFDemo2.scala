package spark.sparkday08

import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

//自定义弱类型,使用DF
//多个输入对应一个输出,实现计算年龄的均值
class UDAF1 extends UserDefinedAggregateFunction{
  //定义输入参数的数据的scheme信息
  override def inputSchema: StructType = {
    StructType(List(StructField("age",IntegerType,true))) //参数要求为集合类型
  }

  //分区中聚合时产生的中间结果的scheme
  //(age+age...,1+1+...)
  override def bufferSchema: StructType = {
    StructType(StructField("sum",IntegerType)::StructField("count",IntegerType)::Nil)
    //StructType(List(StructField("")))
    //new StructType().add("sum",IntegerType).add("count",IntegerType)
  }

  //定义最后聚合返回结果的数据类型
  override def dataType: DataType = DoubleType

  //多个确定的输入输出的结果是否是确定的
  override def deterministic: Boolean = true

  //初始化中间结果对象
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    //初始化年龄聚合
    buffer(0) = 0
    //初始化人的个数
    buffer(1) = 0
  }

  //处理分区里的每一条数据,聚合到中间结果
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    if (!input.isNullAt(0)) {
      buffer(0) = buffer.getInt(0) + input.getInt(0)
      buffer(1) = buffer.getInt(1) + 1
    }
  }

  //分区之间的聚合,实现每个分区聚合之后的再汇总
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getInt(0) + buffer2.getInt(0)
    buffer1(1) = buffer1.getInt(1) + buffer2.getInt(1)
  }

  //获取返回值-聚合之后的结果
  override def evaluate(buffer: Row): Double =buffer.getInt(0)/buffer.getInt(1).toDouble

}

object UDAFDemo2 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("UDFDemo").master("local[*]").getOrCreate()
    val frame: DataFrame = sparkSession.read.json(args(0))
    //先注册
    sparkSession.udf.register("myAvg",new UDAF1)
    frame.createOrReplaceTempView("t_people")
    sparkSession.sql("select myAvg(age) from t_people").show()

    sparkSession.stop()
  }
}
