package test0601

import org.apache.spark.{SparkConf, SparkContext}

object Tweets10 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("IPCount").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)

    val rdd = sc.textFile("F:/baseSite")

    val lines = rdd.map(x=>{
      val arr = x.split(",")
      (arr(0),arr(1),arr(2),arr(3),arr(4))
    })

    val linesCount = lines.count()
    println(linesCount)

    val user1 = lines.map(x=>{
      x._3
    })
    println(user1.collect().toBuffer)
    val user2 = user1.map(x=>{
      x.split(" ")
    })
      //.filter(_.startsWith("@"))
    println(user2)


    sc.stop()
  }
}
