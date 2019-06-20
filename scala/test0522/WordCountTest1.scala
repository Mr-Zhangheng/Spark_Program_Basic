package test0522

object WordCountTest1 {
  def main(args: Array[String]): Unit = {
    val lines = List("hello dog","hello xiaobai","hello xiaoran")
    val res = lines.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).mapValues(_.size).toList.sortWith(_._2 > _._2)
    res.foreach(println)
  }

}
