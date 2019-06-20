package test0522

abstract class Shape {
  var x: Float = 0
  var y: Float = 0
  def centerPoint
}

class Rectangle extends Shape {
  def this(x:Float,y:Float){
    this()
    this.x = x
    this.y = y
  }
  override def centerPoint: Unit ={
    println(x/2,y/2)
  }
}

class Circle extends Shape {
  var r:Float = 0f
  def this(x: Float, y: Float,r:Float) {
    this()
    this.x = x
    this.y = y
    this.r = r
  }
  override def centerPoint: Unit = {
    println(x + r,y + r)
  }
}

object AbstractTest3 {
  def main(args: Array[String]): Unit = {
    val rectangle = new Rectangle(3,8)
    rectangle.centerPoint
    val circle = new Circle(0,0,5)
    circle.centerPoint
  }
}
