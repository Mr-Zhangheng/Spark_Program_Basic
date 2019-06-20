package test0528

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkRDDTest2 {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf()
      .setAppName("AccessSort").setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)

    val rdd = sc.textFile("f:/sparkRDD")
    //一共有多少人参加考试
    val str = rdd.map(x=>{
      val spl = x.split(" ")
      (spl(0),spl(1),spl(2),spl(3),spl(4),spl(5))
    })
    val countStu = str.map(x=>{
      (x._2)
    }).distinct().count()
    println(countStu)

    //一共有多少个小于20岁的人参加考试？
    val age0 = str.filter(_._3.toInt < 20).groupBy(_._2).count()
    println(age0)
    //一共有多少个等于20岁的人参加考试？
    val age1 = str.filter(_._3.toInt == 20).groupBy(_._2).count()
    println(age1)
    //一共有多少个大于20岁的人参加考试？
    val age2 = str.filter(_._3.toInt > 20).groupBy(_._2).count()
    println(age2)

    //一共有多个男生参加考试？
    val gender0 = str.filter(_._4 == "男").groupBy(_._2).count()
    println(gender0)
    //一共有多少个女生参加考试？
    val gender1 = str.filter(_._4.equals("女")).groupBy(_._2).count()
    println(gender1)

    //12班有多少人参加考试？
    val classes0 = str.filter(_._1.toInt == 12).groupBy(_._2).count()
    println(classes0)
    //13班有多少人参加考试？
    val classes1 = str.filter(_._1.toInt == 13).groupBy(_._2).count()
    println(classes1)

    //语文科目的平均成绩是多少？
    val cour: RDD[(String,Int)] = str.map(x=>{
      (x._5,x._6.toInt)
    })
    //println(cour.collect().toBuffer)
    val score:RDD[(String,(Int,Int))] = cour.combineByKey(
      (score:Int) => {(score,1)},
      (accres:(Int,Int),score:Int)=>{(accres._1+score,accres._2+1)},
      (accres1:(Int,Int),accres2:(Int,Int))=>{(accres1._1+accres2._1,accres1._2+accres2._2)}
    )
    val chinese :RDD[(String,Double)] = score.filter(_._1.equals("chinese")).mapValues((x:(Int,Int))=>x._1/x._2.toDouble)
    println(chinese.collect().toBuffer)
    //数学科目的平均成绩是多少？
    val math :RDD[(String,Double)] = score.filter(_._1.equals("math")).mapValues((x:(Int,Int))=>x._1/x._2.toDouble)
    println(math.collect().toBuffer)
    //英语科目的平均成绩是多少？
    val english :RDD[(String,Double)] = score.filter(_._1.equals("english")).mapValues((x:(Int,Int))=>x._1/x._2.toDouble)
    println(english.collect().toBuffer)

    //单个人平均成绩是多少？
    val stu: RDD[(String,Int)] = str.map(x=>{
      (x._2,x._6.toInt)
    })
    val scoreStu:RDD[(String,(Int,Int))] = stu.combineByKey(
      (score:Int) => {(score,1)},
      (accres:(Int,Int),score:Int)=>{(accres._1+score,accres._2+1)},
      (accres1:(Int,Int),accres2:(Int,Int))=>{(accres1._1+accres2._1,accres1._2+accres2._2)}
    )
    val avgStu :RDD[(String,Double)] = scoreStu.mapValues((x:(Int,Int))=>x._1/x._2.toDouble)
    println(avgStu.collect().toBuffer)

    //12班平均成绩是多少？
    val cla: RDD[(String,Int)] = str.map(x=>{
      (x._1,x._6.toInt)
    })
    val classAvg:RDD[(String,(Int,Int))] = cla.combineByKey(
      (score:Int) => {(score,1)},
      (accres:(Int,Int),score:Int)=>{(accres._1+score,accres._2+1)},
      (accres1:(Int,Int),accres2:(Int,Int))=>{(accres1._1+accres2._1,accres1._2+accres2._2)}
    )
    val avgClass :RDD[(String,Double)] = classAvg.filter(_._1.equals("12")).mapValues((x:(Int,Int))=>x._1/x._2.toDouble)
    println(avgClass.collect().toBuffer)
    //12班男生平均总成绩是多少？
    val avgSumClass1:RDD[(String,Double)] = str.filter(_._1.equals("12")).filter(_._4.equals("男")).map(x=>{
      (x._1,x._6.toInt)
    }).combineByKey(
      (score:Int) => {(score,1)},
      (accres:(Int,Int),score:Int)=>{(accres._1+score,accres._2+1)},
      (accres1:(Int,Int),accres2:(Int,Int))=>{(accres1._1+accres2._1,accres1._2+accres2._2)}
    ).mapValues((x:(Int,Int))=>x._1/x._2.toDouble)
    println(avgSumClass1.collect().toBuffer)
    //12班女生平均总成绩是多少？
    val avgSumClass2:RDD[(String,Double)] = str.filter(_._1.equals("12")).filter(_._4.equals("女")).map(x=>{
      (x._1,x._6.toInt)
    }).combineByKey(
      (score:Int) => {(score,1)},
      (accres:(Int,Int),score:Int)=>{(accres._1+score,accres._2+1)},
      (accres1:(Int,Int),accres2:(Int,Int))=>{(accres1._1+accres2._1,accres1._2+accres2._2)}
    ).mapValues((x:(Int,Int))=>x._1/x._2.toDouble)
    println(avgSumClass2.collect().toBuffer)

    //全校语文成绩最高分是多少？
    val chinMax = str.map(x=>{
      (x._5,x._6.toInt)
    }).filter(_._1.equals("chinese")).map(_._2).collect().max
    println(chinMax)
    //12班语文成绩最低分是多少？
    val chinMaxCla = str.map(x=>{
      (x._5,x._1,x._6.toInt)
    }).filter(_._1.equals("chinese")).filter(_._2.equals("12")).map(_._3).collect().max
    println(chinMaxCla)
    //13班数学最高成绩是多少？
    val chinMaxCla2 = str.map(x=>{
      (x._5,x._1,x._6.toInt)
    }).filter(_._1.equals("math")).filter(_._2.equals("13")).map(_._3).collect().max
    println(chinMaxCla2)

    //总成绩大于150分的12班的女生有几个？
    val scoRes1 = str.filter(_._1.equals("12")).filter(_._4.equals("女")).map(x=>{
      (x._2,x._6.toInt)
    }).reduceByKey(_+_).filter(_._2>150).count()
    println(scoRes1)

    //总成绩大于150分，且数学大于等于70，且年龄大于等于20岁的学生的平均成绩是多少？
    val scoRes2:RDD[(String,(String,Double))] = str.filter(_._3.toInt>=20).filter(_._5.equals("math")).filter(_._6.toInt<=70)
      .map(x=>{
        (x._2,(x._5,x._6.toInt))
      })
      .combineByKey(
      (score:(String,Int)) => {(score._1,score._2,1)},
      (accres:(String,Int,Int),score:(String,Int))=>{(accres._1,accres._2+score._2,accres._3+1)},
      (accres1:(String,Int,Int),accres2:(String,Int,Int))=>{(accres1._1,accres1._2+accres2._2,accres1._3+accres2._3)}
    ).mapValues((x:(String,Int,Int))=>(x._1,x._2/x._3.toDouble)).filter(x=>{
        x._2._1.equals("math")
    })
    println(scoRes2.collect().toBuffer)

  }
}
