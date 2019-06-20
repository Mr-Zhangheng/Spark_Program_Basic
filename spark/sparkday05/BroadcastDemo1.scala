package sparkday05
import org.apache.spark.{SparkConf, SparkContext}
object BroadcastDemo1 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("broadcast").setMaster("local")
    val sc:SparkContext = new SparkContext(conf)
    val rdd = sc.parallelize(List(10,20,30,40,50,60))
    val arr = Array(1,2,3,4,5,6)
    //定义一个变量,广播变量,将数据集进行广播
    //如果要广播的是RDD数据集,则需要先收集到Driver端,然后再广播
    //广播变量在Executor端只能读取
    val broadcast = sc.broadcast(arr)

    val res = rdd.map(x=>{
      val sum = broadcast.value.sum
      x + sum
    })
    res.foreach(println)
    sc.stop()
  }
}
