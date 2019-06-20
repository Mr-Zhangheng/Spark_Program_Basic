package sparkday05
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
//使用自定义累加器实现WordCount功能
class myAccumulator extends AccumulatorV2[String,mutable.HashMap[String,Int]] {
  private  val hashmapAcc = new mutable.HashMap[String,Int]()
  //当前累加器初始值是否为0或者Nil
  //检测累加器是否为空
  override def isZero: Boolean = {
    hashmapAcc.isEmpty
  }
  //做一个累加器的拷贝,返回一个新的累加器
  override def copy(): AccumulatorV2[String, mutable.HashMap[String, Int]] = {
    val newAcc = new myAccumulator()
    hashmapAcc.synchronized {
      newAcc.hashmapAcc ++= hashmapAcc
    }
    newAcc
  }
  //重置累加器,清空数据
  //重置之后,调用isZero方法,保证返回True
  override def reset(): Unit = {
    hashmapAcc.clear()
  }
  //
  override def add(word: String): Unit = {
    hashmapAcc.get(word) match {
      case Some(v1) => hashmapAcc += ((word, v1 + 1))
      case None => hashmapAcc += ((word, 1))
    }
  }
  //
  override def merge(other: AccumulatorV2[String, mutable.HashMap[String, Int]]): Unit = {
    other match {
      case acc:AccumulatorV2[String,mutable.HashMap[String,Int]] =>{
        for ((k,v)<-acc.value){
          hashmapAcc.get(k) match {
            case Some(newv) => hashmapAcc += ((k,v+newv))
            case None => hashmapAcc += ((k,v))
          }
        }
      }
    }
  }
  //
  override def value: mutable.HashMap[String, Int] = hashmapAcc
}
object DefineAccumulatorDemo3 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("accumulate").setMaster("local")
    val sc:SparkContext = new SparkContext(conf)
    val rdd = sc.parallelize(List("scala","java","c","scala","scala"))
    //创建累加器,然后进行注册
    val acc = new myAccumulator()
    sc.register(acc,"wordCount")
    rdd.foreach(x=>acc.add(x))
    //通过value访问最后累加的结果
    println(acc.value)
  }
}
