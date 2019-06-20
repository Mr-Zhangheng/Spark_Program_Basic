package sparkday06

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

//RDD=>dATAFRAME
//dataframe = rdd + scheme
object DFDemo3 {

  def main(args: Array[String]): Unit = {
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

    //rdd scheme进行关联生成dataframe
    val dataFrame: DataFrame = sparkSession.createDataFrame(rowRDD,scheme)
    //dataframe两种编程风格 sql 和DSL（基于API编程）
    //sql 先生成一个视图表，基于表写sql语句
    //dsl 直接使用dataframe的API
    val frame: DataFrame = dataFrame.select("name","age")
    //排序
    import sparkSession.implicits._
    val ordered: Dataset[Row] = frame.orderBy($"age" desc)
    ordered.write.json("jsonres")
    ordered.show()
    sc.stop()

  }

}
