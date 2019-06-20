package sparkday07

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object TransformDemo1 {
  def main(args: Array[String]): Unit = {

    //RDD DF DS 互相转换
    //RDD -->DF(自己指定/反射方式/通过编码定义scheme信息+RDD)

    //DF -->RDD
    //创建一个sparksession1
    val sparkSession = SparkSession.builder().appName("sparksqlDemo")
      .master("local").getOrCreate()

    val sc = sparkSession.sparkContext
    val rdd = sc.textFile("D://people.txt")
    //由字符串类型的数据集，变成Row类型的数据集
    val rowRDD:RDD[Row]=rdd.map(line=>{
      val arr = line.split(",")
      val name = arr(0)
      val age = arr(1).toInt
      Row(name,age)
    })
    //创建给RDD对应的scheme信息
    val scheme:StructType = StructType(List(
      StructField("name",StringType,true),
      StructField("age",IntegerType,true)
    ))

    import sparkSession.implicits._
    //rdd scheme进行关联生成dataframe
    val dataFrame: DataFrame = sparkSession.createDataFrame(rowRDD,scheme)
    //DF -->RDD ,使用RDD算子对当前数据集进行计算
    val dfToRDD = dataFrame.rdd
    //DF -->DS
   // val unit :Dataset[People]= dataFrame.as[Pepple]
    //DS-->DF
//    val dsToDF:DataFrame = dfToDs.toDF
//    //DS --> RDD
//    val dsToRDD = dfToDs.rdd

    sc.stop()
    sparkSession.stop()

  }
}
