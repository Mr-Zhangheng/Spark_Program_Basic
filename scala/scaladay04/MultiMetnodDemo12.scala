package scaladay04

//方法参数的类型参数化

object MultiMetnodDemo12 {
  //构建一个列表,列表元素类型多样化,通过类型的参数化实现
  def listDup[A](x:A,len:Int):List[A]={
    if(len<1)
      Nil
    else
      x::listDup(x,len-1)

  }

  def main(args: Array[String]): Unit = {
    println(listDup(1,2))
    println(listDup(5,3))

  }


}
