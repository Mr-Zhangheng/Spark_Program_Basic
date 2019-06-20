package sparkday06

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

//通过反射方式RDD=>dataFrame
object DFDemo2 {

  def main(args: Array[String]): Unit = {
    //创建一个sparksession1
    val sparkSession = SparkSession.builder().appName("sparksqlDemo")
      .master("local").getOrCreate()

    val sc = sparkSession.sparkContext
    val rdd = sc.textFile("D://people.txt")
    //由字符串类型的数据集，变成对象的数据集
    val peopleRDD:RDD[People]=rdd.map(line=>{
      val arr = line.split(",")
      val name = arr(0)
      val age = arr(1).toInt
      People(name,age)
    })

    //rdd=>dataframe
    import sparkSession.implicits._
    val dF: DataFrame = peopleRDD.toDF
    //注册一张表,基于表操作对应dataframe中的数据
    dF.createOrReplaceTempView("t_people")

    //对df数据进行排序
    val sql ="select * from t_people order by age desc"
    val dataFrame: DataFrame = sparkSession.sql(sql)
    dataFrame.show()
    sc.stop()

  }

}

case class People(name:String,age:Int)

