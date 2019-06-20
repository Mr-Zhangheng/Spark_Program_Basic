package sparkday05
import java.sql.DriverManager
import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}
object JDBCRDD5 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("accumulate").setMaster("local[4]")
    val sc:SparkContext = new SparkContext(conf)
    val jdbcurl = "jdbc:mysql://10.0.16.58:3306/mydb1?useUnicode=true&characterEncoding=utf8"
    val user ="root"
    val password = "20143650"
    //sql语句,要有一个范围
    val sql = "select * from  careers where cid >? and cid <? "
    //创建数据库连接
    val conn = ()=>{
      Class.forName("com.mysql.jdbc.Driver").newInstance()
      DriverManager.getConnection(jdbcurl,user,password)
    }
    val JRDD:JdbcRDD[(Int,String)] = new JdbcRDD(sc,conn,sql,1L,6L,1,resultSet=>{
      val cid = resultSet.getInt("cid")
      val cname = resultSet.getString("cname")
      (cid,cname)
    })
    JRDD.foreach(println)
    sc.stop()
  }
}
