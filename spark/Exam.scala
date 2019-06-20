package spark

import org.apache.spark.sql.{DataFrame, SparkSession}

object Exam {
  def main(args: Array[String]): Unit = {
    //创建一个配置信息对象
    //设置的应用程序名称

    val sparkSession = SparkSession.builder().appName("ads")
      .master("local[*]").getOrCreate()
    val sc= sparkSession.read.parquet("C:\\Users\\acer\\Desktop\\bdp_day=20190613")
    sc.createOrReplaceTempView("t_ads")

    //查询投放请求数
    val sql = "select count(sid) from t_ads"
    val df: DataFrame = sparkSession.sql(sql)
    df.show()

    //查询投放请求数
    val scRDD = sc.rdd
    val sid = scRDD.count()
    println(sid)

    //曝光信息
    val sqlBG = "select release_status from t_ads)"
    val bgDF = sparkSession.sql(sqlBG)
    bgDF.createOrReplaceTempView("t_ads_bg")
    val sqlBD2 = "select * from t_ads_bg where status.equals(\"03\")"
    val bgDF2 = sparkSession.sql(sqlBD2)
    bgDF2.show()
  }
}
