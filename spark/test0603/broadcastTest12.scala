package spark.test0603
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object broadcastTest12 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("sparksqlDemo")
      .master("local").getOrCreate()
    val lines:Dataset[String] = sparkSession.read.textFile(args(0))
    import sparkSession.implicits._
    val ipInfo = lines.map(line=>{
      val arr = line.split("\\|")
      val startIP = arr(2).toLong
      val endIP = arr(3).toLong
      val provice = arr(6)
      (startIP,endIP,provice)
    })
    val ipInfoDF = ipInfo.toDF("startIP","endIP","province")
    ipInfoDF.createOrReplaceTempView("t_ipInfoDF")
    ipInfoDF.show()
    val linesUser:Dataset[String] = sparkSession.read.textFile(args(1))
    import sparkSession.implicits._
    val province = linesUser.map(lines=>{
      val arr = lines.split("\\|")
      val ipTmp = arr(1)
      val ip = ipToLong(ipTmp)
      ip
    })
    val provinceDF = province.toDF("ip")
    provinceDF.createOrReplaceTempView("t_province_ip")
    province.show()

//    val sql = "select t_ipInfoDF.province,count(*) from t_ipInfoDF join t_province_ip on t_ipInfoDF.startIP <= t_province_ip.ip and t_province_ip<= t_ipInfoDF.endIP group by t_ipInfoDF.province"
//    val res:DataFrame = sparkSession.sql(sql)
//    res.show()

    //DSL 风格
    val resDF = ipInfoDF.join(provinceDF).where($"startIP"<=$"ip" and $"ip"<=$"endIP").groupBy($"province").count().sort($"count" desc)
    resDF.show()
  }
  //将IP转换成Long类型
  def ipToLong(ip:String)={
    val ipFragmengt = ip.split("[.]")
    var ipLongNum =0L
    //通过移位操作转换成Long类型的数值
    for (elem <- ipFragmengt){
      ipLongNum = elem.toLong | ipLongNum << 8L
    }
    ipLongNum
  }
}
