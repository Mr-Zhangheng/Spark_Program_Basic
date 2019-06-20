package sparkday06

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object DataFrameDemo1 {
  // RDD => DataFrame(直接指定)
  def main(args: Array[String]): Unit = {
    //创建sparksql的入口，2.0 sparksession 1.6 sqlContext hiveContext
    //创建一个sparksession1
    val sparkSession = SparkSession.builder().appName("sparksqlDemo")
      .master("local").getOrCreate()
    //创建一个sparksession2
    //    val conf = new SparkConf().setAppName("sparksqlDemo2").setMaster("local")
    //    val sparkSession1 = SparkSession.builder().config(conf).getOrCreate()
    //    //兼容hive
    //    val sparkSessionH = SparkSession.builder().appName("sparksqlDemo3")
    //      .master("local").enableHiveSupport().getOrCreate()

    //创建一个RDD
    val sc = sparkSession.sparkContext
    val peopleRDD: RDD[String] = sc.textFile("D://people.txt")
    //创建一个DataFrame
    val tupleRDD = peopleRDD.map(line=>{
      val arr = line.split(",")
      (arr(0),arr(1).trim.toInt)
    })
    import sparkSession.implicits._
    val dataFrame: DataFrame = tupleRDD.toDF("name","age")
    dataFrame.show()
    sc.stop()
    sparkSession.stop()
  }

}
