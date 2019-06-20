package spark.test0604

import org.apache.spark.sql.SparkSession

object GupiaoTest3 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("gupiao").master("local[*]").getOrCreate()
    val ds = sparkSession.read.csv("F:\\gupiao.txt")
    import sparkSession.implicits._
    val frame = ds.map(line=>{
      //注意从0开始
      val date = line.getString(0)
      val close =line.getString(6)
      (date,close)
    }).toDF("date","close")

    frame.show()

    frame.createOrReplaceTempView("t_")

    sparkSession.stop()
  }
}
