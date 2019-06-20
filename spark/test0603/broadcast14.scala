package spark.test0603

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.{Dataset, SparkSession}
import spark.test0603.broadcastTest12.ipToLong

object broadcast14 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("sparksqlDemo")
      .master("local").getOrCreate()

    //解析字典数据
    val lines:Dataset[String] = sparkSession.read.textFile(args(0))
    import sparkSession.implicits._
    val dictDS = lines.map(line=>{
      val arr = line.split("[|]")
      val startIP = arr(2)
      val endIP = arr(3)
      val provice = arr(6)
      (startIP,endIP,provice)
    })

    //先把要广播的数据收集到driver
    val array: Array[(String, String, String)] = dictDS.collect()

    val broadcast: Broadcast[Array[(String, String, String)]] = sparkSession.sparkContext.broadcast(array)

    //处理日志数据
    val linesUser:Dataset[String] = sparkSession.read.textFile(args(1))
    import sparkSession.implicits._
    val logFrame = linesUser.map(lines=>{
      val arr = lines.split("[|]")
      val ipTmp = arr(1)
      val ip = ipToLong(ipTmp)
      ip
    }).toDF("ip")

    //注册表
    logFrame.createOrReplaceTempView("t_log")
    //ip -->province

    val func = (ip:Long)=>{
      val dict= broadcast.value
      val province = BinarySearch(dict,ip)
      province
    }
    //注册一个自定义函数,ip-->province
    val ipToProvince = sparkSession.udf.register("ipToProvince",func)

    sparkSession.sql("select ipToProvince(ip) province,count(*) counts from t_log group by province order by counts desc").show()
  }

  def BinarySearch(arr:Array[(String,String,String)],ip:Long):String={
    var low = 0
    var high = arr.length
    while (low <= high){
      val mid = (low + high) / 2
      if((ip >= arr(mid)._1.toLong) && (ip <= arr(mid)._2.toLong))
        return arr(mid)._3
      else if (ip < arr(mid)._1.toLong)
        high = mid-1
      else
        low = mid + 1
    }
    "undifined"
  }
}
