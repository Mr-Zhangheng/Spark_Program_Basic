package sparkday07

import java.util.Properties

import org.apache.spark.sql.{DataFrame, SparkSession}

object JsonFileDemo2 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("JSon").master("local").getOrCreate()

    val frame:DataFrame = sparkSession.read.json(args(0))
    import sparkSession.implicits._
    val resdf = frame.where($"age">20)
//    //write--action算子--保存文件
//    frame.write.csv("csvres")
//    val frame1:DataFrame = sparkSession.read.csv("csvres")
//    frame.write.format("parquet").save("pathInfo")
//    frame.write.parquet("parquetres")
//    sparkSession.read.parquet("parquet")
//
//    //通过jdbc读数据库某张表的数据
//    val prop = new Properties()
//    prop.put("user","root")
//    prop.put("password","123456")
//    prop.put("driver","com.mysql.jdbc.Driver")
//
//    //写数据库
//    //mode写模式 看官网 Save Mode "error"  "append" "overwrite" "ignore"
//    resdf.write.mode("append").jdbc("","t_people",prop)
//    //读数据库表中的数据
//    sparkSession.read.jdbc("url","t_people",prop)
//    sparkSession.read.format("jdbc").options(
//      Map("url"->"urlInfo"
//        ,"driver"->"com.mysql.jdbc.Driver"
//        ,"user"->"root"
//        ,"password"->"123456"
//        ,"dbtable"->"t_people")
//    ).load()


    //打印scheme信息(结构化信息--字段,类型,约束...)
    resdf.printSchema()
    //打印SQL,api=RDD 从逻辑计划到物理计划的执行流程,where调用的是filter
    resdf.explain()
    resdf.show()
    sparkSession.stop()


  }
}
