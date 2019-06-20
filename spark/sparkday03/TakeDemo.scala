package sparkday03
import org.apache.spark.{SparkConf, SparkContext}
object TakeDemo {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("CombineByKey").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)
    //求各科平均成绩
    val rdd = sc.parallelize(List(10,20,60,50,40))
    val res1:Array[Int] = rdd.take(3)
    //数据量小时使用,数据会传输到driver端
    val res2:Array[Int] = rdd.takeOrdered(3)
    //排序后取topN
    val res3 = rdd.top(3)
    //等同于take(1)
    val res4 = rdd.first()
    val res5 = rdd.takeSample(false,3,100L)
    println(res1.toBuffer+":"+res2.toBuffer+":"+res3.toBuffer +":"+res4+":"+res5.toBuffer)
//    println(rdd.count()) //元素个数
//    //foreach与map的对比
//    rdd.foreach(println)
//    rdd.foreachPartition(_.foreach(println))
//    rdd.saveAsTextFile("")
    //rdd.iterator().toList
  }
}
