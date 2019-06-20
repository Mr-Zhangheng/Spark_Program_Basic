package spark.test0603

import org.apache.spark.sql.{Dataset, SparkSession}
import spark.test0603.broadcastTest12.ipToLong

object broadcast13 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("sparksqlDemo")
      .master("local").getOrCreate()

    //解析字典数据,DS本身没有列名,可以转换为DF以便加上列名
    val lines:Dataset[String] = sparkSession.read.textFile(args(0))
    import sparkSession.implicits._
    val dictFrame = lines.map(line=>{
      val arr = line.split("[|]")
      val startIP = arr(2).toLong
      val endIP = arr(3).toLong
      val provice = arr(6)
      (startIP,endIP,provice)
    }).toDF("startIP","endIP","province")

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
    dictFrame.createOrReplaceTempView("t_dict")
    logFrame.createOrReplaceTempView("t_log")

    //根据IP信息不等值连接--join--会发生shuffle--效率低--变为广播变量
    sparkSession.sql("select province,count(*) counts from t_dict join t_log on (startIP <= ip and ip <= endIP) group by province order by counts desc").show()

    sparkSession.stop()
  }
}
