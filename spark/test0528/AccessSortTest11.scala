package test0528

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object AccessSortTest11 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("AccessSort").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)

    val lines = sc.textFile(args(0))

    val reduceRes:RDD[(String,Int)] = lines.map(x=>{
      (x.split("\t")(1),1)
    }).reduceByKey(_+_)

    //cache
    val cached = reduceRes.cache()
    val subjects = Array("http://android.learn.com","http://ui.learn.com","http://java.learn.com","http://h5.learn.com","http://bigdata.learn.com")
    for(subject <- subjects) {
      val res = cached.filter(_._1.startsWith(subject)).sortBy(_._2,false).take(3)
      res.foreach(println)
    }



  }
}
