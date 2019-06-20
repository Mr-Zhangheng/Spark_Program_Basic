package spark.test0604

import org.apache.spark.sql.{Dataset, SparkSession}

object HeightTest1 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("height").master("local[*]").getOrCreate()
    //整理height文件数据
    val ds: Dataset[String] = sparkSession.read.textFile("F:\\height.txt")
    import sparkSession.implicits._
    val frame = ds.map(line=>{
      val arr = line.split(" ")
      val id = arr(0)
      val gender = arr(1)
      val height = arr(2)
      (id,gender,height)
    }).toDF("id","gender","height")

    //注册表
    frame.createOrReplaceTempView("t_height")

   //统计男性中身高超过 180cm 的人数
    sparkSession.sql("select count(*) counts from t_height where gender==\"M\" and height > 180").show()

    //统计女性中身高超过 170cm 的人数
    sparkSession.sql("select count(*) counts from t_height where gender==\"F\" and height > 170").show()

    //对人群按照性别分组并统计男女人数
    sparkSession.sql("select gender,count(*) counts from t_height group by gender").show()

    //用类 RDD 转换的方式对 DataFrame 操作来统计并打印身高大于 210cm 的前 50 名男性
    classRDD(sparkSession)

    //对所有人按身高进行排序并打印前 50 名的信息
    sparkSession.sql("select * from t_height order by height desc limit 50").show()

    //统计男性,女性的平均身高
    sparkSession.sql("select gender,avg(height) from t_height group by gender").show()

    //统计男性，女性身高的最大值
    sparkSession.sql("select gender,max(height) from t_height group by gender").show()

    sparkSession.stop()
  }

  def classRDD(sparkSession: SparkSession)={
    val sc = sparkSession.sparkContext
    val linesRDD = sc.textFile("F:\\height.txt")
    import sparkSession.implicits._
    val frame = linesRDD.map(line=>{
     val arr = line.split(" ")
      (arr(0),arr(1).trim.toInt,arr(2).trim.toInt)
    }).toDF("id","gender","height")
    frame.createOrReplaceTempView("t_height_RDD")
    //sparkSession.sql("select * from t_height_RDD")
    sparkSession.sql("select gender,height,count(*) counts from t_height_RDD having gender==\"M\" and height > 210 order by height desc limit 50").show()
    sc.stop()
  }

}
