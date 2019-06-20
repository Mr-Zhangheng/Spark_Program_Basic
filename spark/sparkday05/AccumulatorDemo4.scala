package sparkday05

import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

object AccumulatorDemo4 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("accumulate").setMaster("local")
    val sc:SparkContext = new SparkContext(conf)

    val rdd = sc.parallelize(1 to 100)
    val acc = new LongAccumulator
    sc.register(acc,"acc")

    val rdd1 = rdd.map(x=>{
      if (x%2 == 0) acc.add(1)
      else 0
    })
    val rdd2 =rdd1.cache()
    rdd2.count()
    println(acc.value)
    rdd2.count()
    println(acc.value)



  }
}
