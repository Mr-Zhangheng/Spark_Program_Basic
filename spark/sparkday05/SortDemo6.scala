package sparkday05
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
object SortDemo6 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("accumulate").setMaster("local")
    val sc:SparkContext = new SparkContext(conf)
    //解决自定义类型的数据对象的排序
    //Java方法中会重写方法或定义对象
    val arr = Array(("kate",20,98),("mary",18,95),("rose",23,65),("candy",20,99))
    val rdd = sc.parallelize(arr)
    //对RDD数据进行排序,先按年龄,再按颜值排序
    val userRDD:RDD[User] = rdd.map(x=>{
      new User(x._1,x._2,x._3)
    })
    val sortedRDD = userRDD.sortBy(u=>u)
    println(sortedRDD.collect().toBuffer)
    val sortedRDD1 = rdd.sortBy(tuple=>new User(tuple._1,tuple._2,tuple._3))
    println(sortedRDD1.collect().toBuffer)
    sc.stop()
  }
}
//通过定义类,在类中实现对象的比较逻辑--模仿Java
//要加val
class User(val name:String,val age:Int,val faceValue:Int) extends Ordered[User] with Serializable {
  override def compare(that: User): Int = {
    if (this.age == that.age)
      //颜值高的在前面
      -(this.faceValue - that.faceValue)
    else
    //年龄小的在前面
      this.age - that.age
  }
  override def toString: String = s"name:$name,age:$age,faceValue:$faceValue"
}

