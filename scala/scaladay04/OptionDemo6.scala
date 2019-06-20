package scaladay04

object OptionDemo6 {
  def main(args: Array[String]): Unit = {
    val map1 = Map("scala"->"spark","java"->"hadoop")
    map1.get("scala") match {
      case Some(v) =>println(v)
      case None => println("no result")
    }

  }
}
