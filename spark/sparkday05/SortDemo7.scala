package sparkday05
import org.apache.spark.{SparkConf, SparkContext}
object SortDemo7 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("accumulate").setMaster("local")
    val sc:SparkContext = new SparkContext(conf)
    //解决自定义类型的数据对象的排序
    //隐式转换
    val arr = Array(("kate",20,98),("mary",18,95),("rose",23,65),("candy",20,99))
    val rdd = sc.parallelize(arr)
    import SortRules.OrderUser
//    val sorted = rdd.sortBy(tuple=>User1(tuple._2,tuple._3))
//    println(sorted.collect().toBuffer)
  }
}
case class User1(age:Int,faceValue:Int)
object SortRules {
  implicit object OrderUser extends Ordering[User1] {
    override def compare(x: User1, y: User1): Int = {
      if (x.age == y.age)
        -(x.faceValue - y.faceValue)
      else
        x.age - y.age
    }
  }
}

