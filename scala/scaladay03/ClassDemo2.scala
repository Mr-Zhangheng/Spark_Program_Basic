package scaladay03

//有一个默认的无参构造方法
//自定义构造方法,主构造方法和辅助构造方法
//类名之后定义的构造方法是主构造方法,只能有一个,没有方法体,
// 主构造方法执行的时候,类中的语句也会被全部执行
//在类中定义的构造方法是辅助构造方法
//主构造方法中的定义参数会变成类中的属性,辅助构造方法不会
//主构造方法可以加private去修饰,变成私有方法
//辅助构造方法,方法名:this
//辅助构造方法第一条语句一定要引用主构造方法/其他辅助构造方法


class Person1 private (var name:String,val age:Int){
  var sex = 0
  var height = 0
  //var name = "kkk" //和主构造方法重复
  println("person1")

  //定义辅助构造方法
  def this(sex:Int){
    this("tom",20)
    this.sex = sex
  }
  def this(name:String,age:Int,height:Int){
    this(1)
    //this(name,age)
    //this.height = 1


  }
}

object ClassDemo2 {
  def main(args: Array[String]): Unit = {
//    val person = new Person1("kk",20)
//    println(person.age) //访问的是get/set方法
    val obj = new Person1("11",22,177)


  }
}
