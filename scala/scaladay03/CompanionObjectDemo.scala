package scaladay03
//student对象的伴生类
class student{
  var name = "zhangsan"
  private val age =10
  private [this] val gender = "female"
  def method()={
    student.objectName
    student.objectage
    //对象私有属性不可访问,伴生类可以访问伴生对象的公共及私有属性/方法
    //student.gender
    student.getMsg()
  }
}
//类student的伴生对象
//定义在同一个源文件中
//单例对象的名字与类名一致,单例对象变为相应类的伴生对象
object student{
  var objectName = "obj"
  private  val objectage = 20
  private [this] val gender ="male"
  def getMsg()={

  }

  def apply: student = new student()

  def main(args: Array[String]): Unit = {
    val obj =new student
    obj.name
    obj.age
    //伴生对象可以访问伴生类的私有属性,不可以访问伴生类的对象私有属性
    //obj.gender
  }
}

object CompanionObjectDemo {
  def main(args: Array[String]): Unit = {
    val obj = new student
    val obj1 = student //自动调用apply方法
    obj.name

    student.getMsg()
  }
}
