package sparkday05
import java.util.concurrent.atomic.LongAccumulator

import org.apache.spark.util.CollectionAccumulator
import org.apache.spark.{SparkConf, SparkContext}
object AccumulateDemo2 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("accumulate").setMaster("local")
    val sc:SparkContext = new SparkContext(conf)
    val rdd = sc.parallelize(List(10,20,30,40,50,60))
    //long类型的累加器
//    def longAccumulator(name:String):LongAccumulator ={
//      val acc = new Lo
//      sc.register(acc,name)
//      acc
//    }
//    val acc1 = longAccumulator("longAcc")
//    //在Executor端执行
//    rdd.foreach(x=>acc1.add(x))
//    //在driver端执行
//    println(acc1.value)
    //collect类型的累加器
    def collectAccumulator(name:String):CollectionAccumulator[Int] ={
      val acc = new CollectionAccumulator[Int]
      sc.register(acc,name)
      acc
    }
    val acc2 = collectAccumulator("collectAccumulator")
    rdd.foreach(x=>acc2.add(x))
    println(acc2.value)
  }
}
