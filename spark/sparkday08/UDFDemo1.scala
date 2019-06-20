package spark.sparkday08

import org.apache.spark.sql.{DataFrame, SparkSession}


//使用场景:一对一,一个输入,传入自定义函数,得到一个计算函数
object UDFDemo1 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("UDFDemo").master("local[*]").getOrCreate()
    val frame: DataFrame = sparkSession.read.json(args(0))
    //自定义函数,处理姓名,注册自定义函数
    sparkSession.udf.register("nameProcess",(x:String)=>x.charAt(0).toUpper+x.substring(1,x.length))
    frame.createOrReplaceTempView("t_people")
    sparkSession.sql("select nameProcess(name),age from t_people ").show()

    sparkSession.stop()
  }
}
