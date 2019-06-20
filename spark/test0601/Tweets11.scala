package test0601

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object Tweets11 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("TweetsDemo")
      .master("local").getOrCreate()
    val sc = sparkSession.sparkContext
    val jsons: DataFrame = sparkSession.read.json("G:\\千峰\\第四阶段Spark\\spark\\spark0601\\spark06\\习题\\reduced-tweets.json")

    //统计Tweets总条数
    val linesCount = jsons.count()
    println(linesCount)

    jsons.take(5)
    //统计每个用户的tweets条数
//    val userLinesCount = jsons.groupBy("user").count()
//    userLinesCount.show()
    jsons.createOrReplaceTempView("tweets")
    val sql = "select count(*) from tweets group by user"
    val dataFrame: DataFrame = sparkSession.sql(sql)
    dataFrame.show()

    sc.stop()
  }
}
