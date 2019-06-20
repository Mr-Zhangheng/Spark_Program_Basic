package sparkday02

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CombineByKeyDemo1 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("CombineByKey").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)
    //求各科平均成绩
    val rdd = sc.parallelize(List(("english",60),("math",74),("chinese",90),("math",86),("english",90),("math",78)))
    //使用combineByKey
    val reducedres:RDD[(String,(Int,Int))] = rdd.combineByKey(
      (score:Int) => {(score,1)},
      (accres:(Int,Int),score:Int)=>{(accres._1+score,accres._2+1)},
      (accres1:(Int,Int),accres2:(Int,Int))=>{(accres1._1+accres2._1,accres1._2+accres2._2)}
    )
    //计算平均成绩
    val res:RDD[(String,Double)] = reducedres.mapValues((x:(Int,Int))=>x._1/x._2.toDouble)
    println(res.collect().toBuffer)
  }
}
