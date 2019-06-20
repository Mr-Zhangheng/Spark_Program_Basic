package spark.test0604

import org.apache.spark.sql.{RelationalGroupedDataset, SparkSession}

object ScoreTest2 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("score").master("local[*]").getOrCreate()
    val ds = sparkSession.read.json("F:\\score.txt")
    //添加列名
    val frame = ds.toDF("course","name","score")
    //注册表
    frame.createOrReplaceTempView("t_score")
    sparkSession.sql("select sc.* from (select *,row_number() over(partition by course order by score desc) rn from t_score) sc where sc.rn < 4").show()

    sparkSession.stop()
  }
}
