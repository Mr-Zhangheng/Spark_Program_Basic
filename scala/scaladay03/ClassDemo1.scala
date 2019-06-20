package scaladay03

class Person{
  //定义属性,一定要有初始化值
  //var 修饰的属性是私有的,但提供了public的get/set方法
  var name = "xiaobai"
  var name1:String = _ //val 不可以这样写; _ 相当于占位符,可以用来初始化,但需要知道类型
  //val 修饰的属性是私有的,但提供了public的get方法
  val age = 20
  //private 修饰属性,是形式私有的,get/set方法也是私有的
  private var height = 180
  private val weight = 70

  //对象私有属性,属性对象私有
  private  [this] var hobby = "travelling"
  private  [this] val hobby1 = "travelling"

  def compare(p:Person) ={
    this.age > p.age
    this.height > p.height
    //this.hobby1 > p.hobby1  //对象之间属性私有,不可相互访问
  }

  private var _age1 = 20
  //get
  def age1 = {
    _age1
  }
  //set
  def age1_(age:Int) = {
    if(age>0 && age<120) {
      _age1 = age
    }
  }

}

object ClassDemo1 {
  def main(args: Array[String]): Unit = {
    val p1 = new Person
    //调用的全是方法
    println(p1.name)
    println(p1.name="jerry")
    println(p1.name)

  }
}
