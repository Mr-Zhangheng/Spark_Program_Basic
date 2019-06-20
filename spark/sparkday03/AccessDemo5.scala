package sparkday03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object AccessDemo5 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("AccessSort").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)

    val rdd = sc.textFile(args(0))
    //url获取
    val urlStr:RDD[String] = rdd.map(_.split("\t")(1))




  }
}
