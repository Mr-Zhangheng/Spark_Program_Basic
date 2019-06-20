package sparkday02

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SortByKeyDemo2 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("CombineByKey").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)

    //根据key排序
    val rdd = sc.parallelize(List(("english",60),("math",74),("chinese",90),("math",86),("english",90),("math",78)))
    val sorted:RDD[(String,Int)] = rdd sortByKey()
    println(sorted.collect().toBuffer)
    //自定义排序规则
    val sorted1 = rdd.sortBy((x:(String,Int))=>x._1) //各类型都可以该算子

    val rdd2 = sc.parallelize(Array(10,3,55,22))
    val sorted2 = rdd2.sortBy((x:Int)=>x) //rdd2.sorted((x:Int)=>x+"")  --sorted2与rdd2一样,不改变内容
    println(sorted2.collect().toBuffer)

  }


}
