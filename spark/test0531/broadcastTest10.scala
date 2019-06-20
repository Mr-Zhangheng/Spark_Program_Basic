package test0531

import org.apache.spark.{SparkConf, SparkContext}

object broadcastTest10 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("IPCount").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)

    val linesIP = sc.textFile(args(0))
    val linesLog = sc.textFile(args(1))

    val rdd1 = linesIP.map(x=>{
      val arr = x.split("\\|")
     val ipS = ip2Long(arr(0))
      //val ipS = arr(0)
      val ipE = ip2Long(arr(1))
      val prov = arr(6)
      (prov,ipS,ipE)
    })
    val ip = rdd1.collect()
    val broadcastIP = sc.broadcast(ip)

    val rdd2 =linesLog.map(x=>{
      val IP = x.split("\\|")(1)
      //val arrIP = IP.split(".")
      //val IPs = arrIP(0).toLong*255*255*255 + arrIP(1).toLong*255*255 + arrIP(2).toLong*255 + arrIP(3).toInt
      val IPs = ip2Long(IP)
      (IPs)
    })


    //val index = binarySerch()

//    IPCount.foreach(println)



    }

  def ip2Long(str: String) = {
    val arr = str.split(".")
    val ip = (arr(0).toInt * 256 * 256 * 256 + arr(1).toInt * 256 * 256 + arr(2).toInt * 256 + arr(3)).toLong
    ip
  }

  def binarySerch(x:Long,y:Long,ip:Long):Long = {
    var low = x
    var high = y
    while (low <= high){
      val middle = (low+high)/2
      if((ip >= middle) && (ip <= middle))
        middle
      if (ip < middle)
        high = middle - 1
      if(ip > middle)
        low = middle + 1
    }
    -1
  }

}
