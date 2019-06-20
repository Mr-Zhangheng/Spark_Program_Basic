package sparkday02

import org.apache.spark.{SparkConf, SparkContext}

object RepartitionDemo3 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("CombineByKey").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)
    val rdd = sc.parallelize(List(("english",60),("math",74),("chinese",90),("math",86),("english",90),("math",78)))

    println(rdd.partitions.size)

    val rdd1 = rdd.repartition(5) //分区数
    println(rdd1.partitions.size)

    val rdd2 = rdd.coalesce(6,true) //默认不发生shuffle--分区数无用,分区一定会发生shuffle
    val rdd3 = rdd.coalesce(3) //分区的合并,不涉及数据传输,不发生shuffle
    println(rdd2.partitions.size)

    //两者的区别:看源码--repartition底层调用coalesce,默认发生shuffle
    //如果rdd采用textFile方式,val rdd = sc.textFile("",10),分区数不一定是10 为什么

  }
}
