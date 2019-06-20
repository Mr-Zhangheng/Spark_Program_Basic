package spark.test0531


import java.sql.{Connection, Date, DriverManager, PreparedStatement}

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object broadcastTest11 {
  def main(args: Array[String]): Unit = {
    //广播变量的使用
    //ip映射成省份信息:IP的比较:字符串的比较速度较慢--变为Long类型
    //数据是有序的:二分查找

    //创建sparkContext
    val conf = new SparkConf().setAppName("IP").setMaster("local[*]")
    val sc = new SparkContext(conf)

    //处理字典数据
    val ipDict = sc.textFile(args(0))
    val ipInfo = ipDict.map(line=>{
      val arr = line.split("\\|")
      val startIP = arr(2)
      val endIP = arr(3)
      val provice = arr(6)
      (startIP,endIP,provice)
    })

    //RDD数据广播之前收集到driver端
    val arrIP = ipInfo.collect()
    val broadcastVal: Broadcast[Array[(String, String, String)]] = sc.broadcast(arrIP)

    //读取日志数据
    val lines = sc.textFile(args(1))
    val provinceRDD = lines.map(lines=>{
      val arr = lines.split("\\|")
      val ip = arr(1)
      //ip-->省份信息
      //将IP信息转换成Long类型的数值
      val ipLongNum = ipToLong(ip)
      val ipDict = broadcastVal.value
      //在字典数据中查找
      val province = BinarySearch(ipDict,ipLongNum)
      (province,1)
    })

    val res:RDD[(String,Int)] = provinceRDD.reduceByKey(_+_)
    res.foreachPartition(it=>dataToMysql(it))
    //数据量过大,不建议collect
    //结果导入mysql
    //dataToMysql(res)
    sc.stop()
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

  //Long类型IP在字典数据里进行匹配,返回省份信息
  //使用return时指定返回类型即可
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

  def dataToMysql(iterator:Iterator[(String,Int)])={
    //只需要获取一次数据库连接
    var conn:Connection = null
    val sql = "insert into res_info(province,counts,dateInfo) values(?,?,?)"
    var ps:PreparedStatement = null
    try {
      conn = DriverManager.getConnection("jdbc:mysql://10.0.16.58:3306/mydb1?useUnicode=true&characterEncoding=utf8","root","20143650")
      iterator.foreach(item=>{
        ps = conn.prepareStatement(sql)
        ps.setString(1,item._1)
        ps.setInt(2,item._2)
        ps.setDate(3,new Date(System.currentTimeMillis()))
        ps.executeUpdate()
      })
    } catch {
      case e:Exception => println(e.printStackTrace())
    } finally {
      if (ps != null)
        ps.close()
      if (conn != null)
        conn.close()
    }

  }

}
