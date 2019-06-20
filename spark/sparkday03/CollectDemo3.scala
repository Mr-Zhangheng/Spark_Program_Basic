package sparkday03

import org.apache.spark.{SparkConf, SparkContext}

object CollectDemo3 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("CombineByKey").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)
    //求各科平均成绩
    val rdd = sc.parallelize(List(("english",60),("math",74),("english",90),("math",78)))
    val rdd2 = rdd.map((x:(String,Int))=> (x._1+"www",x._2+10))
    val res1 = rdd2.collect()
    val res2 = rdd2.collectAsMap()
    println(res1.getClass)
    println(res2.getClass)
    println(res1.toBuffer)
    println(res2.toList)


  }
}
