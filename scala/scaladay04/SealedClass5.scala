package scaladay04

sealed trait Animal
 case class  Cat(name:String,age:Int) extends Animal
 case class Dog(name:String,age:Int) extends Animal

object SealedClass5 {

//若果没有列举所有的类型,编译时会报一个pipeibuwanzhengdejinggao
  def sealedClassMaster(animal:Animal) = animal match {
    case Cat("xiaobai", 2) => println("cat")
    case Dog("xiaoran", 3) => println("dog")
    case _ => println("nothing")
  }

  def main(args: Array[String]): Unit = {
    sealedClassMaster(Cat("xiaobai",2))

  }
}
