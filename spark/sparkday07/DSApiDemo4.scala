package spark.sparkday07

import org.apache.spark.sql.{DataFrame, RelationalGroupedDataset, Row, SparkSession}

object DSApiDemo4 {
  def main(args: Array[String]): Unit = {
    //创建一个df ds 数据集
    val sparkSession = SparkSession.builder().appName("JSon").master("local").getOrCreate()

    val frame:DataFrame = sparkSession.read.json(args(0))

    //action
    //show 默认显示20行,可指定行数 --truncate是否截取,
    frame.show()
    frame.show(10)
    //是否对显示的内容进行截取,默认取前20个字符 ...
    frame.show(true)

    //collect 返回的是一个数组类型的集合对象
    frame.collect()
    //返回list集合对象
    frame.collectAsList()
    //返回具体某一列数据的统计信息:总数,均值,方差,最大值,最小值
    frame.describe("age").show()
    //取第一行
    frame.first()
    //取前n行  如何自动补全
    frame.take(3)
    frame.head()
    frame.head(5)
    frame.takeAsList(3)
    frame.limit(3)

    //常用的与条件相关的过滤
    import sparkSession.implicits._
    frame.where($"age" > 20).show() //where是transformation类型的
    frame.filter($"age" > 20).show()
    //选某一列
    frame.select("age").show() //对列进行过滤 前两者是对行进行过滤
    frame.col("age") //选某一列
    frame.apply("name")
    //drop相关的
    //删除某些列之后的结果
    frame.drop("name")
    //按列去重
    frame.dropDuplicates("name")
    //排序分组相关
    frame.sort("age").show() //会将DF转换为DS
    frame.orderBy("age")
    //根据分区,在分区内排序
    val value = frame.sortWithinPartitions()
    val res: RelationalGroupedDataset = frame.groupBy("name")
    val res1: RelationalGroupedDataset = frame.groupBy(frame.col("age"))
    //按行去重
    frame.distinct()
    //集合操作
    frame.union(frame)
    frame.join(frame)
    frame.crossJoin(frame) //笛卡尔积
    frame.intersect(frame)
    //增加一列
    frame.withColumn("gender",frame.col("age"))
    //行转列
    //frame.explode_

    sparkSession.stop()

  }
}
