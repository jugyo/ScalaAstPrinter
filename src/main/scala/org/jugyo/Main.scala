package org.jugyo

import scala.io.Source
import scala.util.parsing.json._

object Main {
  def main(args: Array[String]) {
    if (args.length != 1) {
      println("Usage: command <file>")
      System.exit(1)
    }

    val file = Source.fromFile(args(0))
    val code = file.mkString
    file.close()

    val parser = new TreePrinter()
    val tree = parser.parse(code)

    val result = parser.Tree2JSONObject.convert(tree)
    println(pprint(Some(result)))
  }

  // copy from https://gist.github.com/umitanuki/944839
  def pprint(j: Option[Any], l:Int = 0):String = {
    val indent = (for(i <- List.range(0, l)) yield "  ").mkString
    j match{
      case Some(o:JSONObject) => {
        val keys = o.obj.keys.toList.sortWith((a, b) => a != "children")
        List("{",
          keys.map(key => indent + "  " + "\"" + key + "\":" + pprint(o.obj.get(key), l + 1)).mkString(",\n"),
          indent + "}").mkString("\n")
      }
      case Some(a:JSONArray) => {
        List("[",
          a.list.map(v => indent + "  " + pprint(Some(v), l + 1)).mkString(",\n"),
          indent + "]").mkString("\n")
      }
      case Some(s: String) => "\"" + s + "\""
      case Some(n: Number) => n.toString
      case None => "null"
      case _ => "undefined"
    }
  }
}
