package sparkday07

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import java.util.Properties

object DSWordCount3 {
  def main(args: Array[String]): Unit = {
    val sparkSession =SparkSession.builder().appName("").master("").getOrCreate()

    val lines:Dataset[String] = sparkSession.read.textFile("")
    import sparkSession.implicits._
    val words:Dataset[String] = lines.flatMap(_.split(" "))
    //SQL版本,t_word里面只有一列:单词
    words.createOrReplaceTempView("t_words")

    //写一个SQL,统计t_word里的各个单词数
    words.show()
    val sql = "select value,count(*) counts from t_words group by value order by counts desc"
    val frame: DataFrame = sparkSession.sql(sql)

    //DSL 版本
    //使用sparkSQL的内置函数(上百个),例如agg聚合函数
    import org.apache.spark.sql.functions._
    val res = words.groupBy($"value" as "word").count().sort($"count" desc)
    val res1 = words.groupBy($"value" as "word").agg(count("*") as "count").orderBy($"count" desc)


  }
}
