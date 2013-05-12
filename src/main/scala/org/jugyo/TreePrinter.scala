package org.jugyo

import scala.collection.mutable.HashMap
import scala.tools.nsc.{ast, Settings}
import scala.tools.nsc.interpreter._
import scala.tools.nsc.util.BatchSourceFile
import scala.util.parsing.json.{JSONArray, JSONObject}

class TreePrinter {
  val settings = new Settings()
  settings.usejavacp.value = true
  val main = new IMain(settings)

  import main.global._
  import syntaxAnalyzer._

  def parse(code: String) = {
    val parser = new SourceFileParser(new BatchSourceFile("<console>", code))
    parser.compilationUnit()
  }

  object Tree2JSONObject {
    def convert(tree: Tree): JSONObject = traverse(tree)

    def traverse(tree: Tree): JSONObject = {
      val hashMap = tree2Map(tree)
      if (tree.children.length > 0) {
        hashMap += "children" -> JSONArray(tree.children.map(traverse))
      }
      JSONObject(hashMap.toMap)
    }

    def tree2Map(tree: Tree) = {
      val hashMap = HashMap[String, Any]()
      hashMap += ("type" -> tree.getClass().getSimpleName())

      tree match {
        case ClassDef(mods, name, tparams, impl) =>
          hashMap += ("name" -> name.toString())
        case ModuleDef(mods, name, impl) =>
          hashMap += ("name" -> name.toString())
        case ValDef(mods, name, tp, rhs) =>
          hashMap += ("name" -> name.toString())
          hashMap += ("type" -> tp.toString())
        case DefDef(mods, name, tparams, vparamss, tp, rhs) =>
          hashMap += ("name" -> name.toString())
          hashMap += ("type" -> tp.toString())
        case TypeDef(mods, name, tparams, rhs) =>
          hashMap += ("name" -> name.toString())
        case LabelDef(name, params, rhs) =>
          hashMap += ("name" -> name.toString())
        case Import(expr, selectors) =>
          hashMap += ("expr" -> expr.toString())
        case Bind(name, t) =>
          hashMap += ("name" -> name.toString())
        case Ident(name) =>
          hashMap += ("name" -> name.toString())
        case Literal(x) =>
          hashMap += ("value" -> x.escapedStringValue)
        case _ =>
      }

      hashMap
    }
  }

  object Traverser {
    def traverse(trees: List[Tree], level: Int)(printTree: (Tree, Int) => Unit): Unit = {
      for (tree <- trees) {
        traverse(tree, level){printTree}
      }
    }

    def traverse(tree: Tree, level: Int)(printTree: (Tree, Int) => Unit): Unit = {
      printTree(tree, level)
      traverse(tree.children, level + 1){printTree}
    }
  }

  class TreePrinter {
    def print(trees: List[Tree]): Unit = {
      def printTree(tree: Tree, nest: Int) {
        val indent = "  " * nest
        println(indent + tree.getClass.getSimpleName())
      }
      Traverser.traverse(trees, 0){printTree}
    }

    def print(tree: Tree): Unit = print(List(tree))
  }
}
