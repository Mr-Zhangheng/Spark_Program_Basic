package spark.sparkday07

import org.apache.spark.sql.SparkSession

case class Student(subject:String,name:String)

object WindowDemo5 {
  //分组topN
  //row_number--不并列,顺次排序  rank--有并列,有占位   dense_rank--有并列,不占位
  //avg sum ...
  //over  partition by
  //学科 姓名 rank
  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder().appName("win").master("local[*]").getOrCreate()
    //
    val lines = sparkSession.sparkContext.textFile("C:\\Users\\acer\\Desktop\\win.txt")
    val studentRDD = lines.map(line=> {
      val arr = line.split(" ")
      //返回对象,需要先定义样例类
      Student(arr(0),arr(1))
    })
    import sparkSession.implicits._
    val dataFram = studentRDD.toDF()

    //注册一个临时表
    dataFram.createOrReplaceTempView("t_student")
    //根据科目分组,统计每个人在每个科目的票数
    val dataFrame1 = sparkSession.sql("select subject,name,count(*) counts from t_student group by subject,name")
    //根据票数排序,显示每个人的排名结果,最后取每组的top1
    dataFrame1.createOrReplaceTempView("t_student_count")
    sparkSession.sql("select * from (select *,row_number() over(partition by subject order by counts desc) rank from t_student_count) t_temp where rank < 2").show()

    sparkSession.stop()
  }
}
