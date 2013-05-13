ScalaAstPrinter
===============

## Making jar

```
$ sbt assembly
```

## Usage

```
$ java -jar target/scala-2.9.2/scala_ast_printer-assembly-0.1.jar ./Sample.scala.txt
```

## Example

```scala
package foo

import bar.baz._

object HelloWorld {
  def main(args: Array[String]) {
    val str:String = "Hello, world!"
    println(str)
  }
}
```

↓

```json
{
  "type":"PackageDef",
  "children":[
    {
      "name":"foo",
      "type":"Ident"
    },
    {
      "expr":"bar.baz",
      "type":"Import",
      "children":[
        {
          "type":"Select",
          "children":[
            {
              "name":"bar",
              "type":"Ident"
            }
          ]
        }
      ]
    },
    {
      "name":"HelloWorld",
      "type":"ModuleDef",
      "children":[
        {
          "type":"Template",
          "children":[
            {
              "type":"Select",
              "children":[
                {
                  "name":"scala",
                  "type":"Ident"
                }
              ]
            },
            {
              "name":"_",
              "type":"<notype>",
              "children":[
                {
                  "type":"TypeTree"
                }
              ]
            },
            {
              "name":"<init>",
              "type":"<type ?>",
              "children":[
                {
                  "type":"TypeTree"
                },
                {
                  "type":"Block",
                  "children":[
                    {
                      "type":"Apply",
                      "children":[
                        {
                          "type":"Select",
                          "children":[
                            {
                              "type":"Super",
                              "children":[
                                {
                                  "type":"This"
                                }
                              ]
                            }
                          ]
                        }
                      ]
                    },
                    {
                      "type":"Literal",
                      "value":"()"
                    }
                  ]
                }
              ]
            },
            {
              "name":"main",
              "type":"scala.Unit",
              "children":[
                {
                  "name":"args",
                  "type":"Array[String]",
                  "children":[
                    {
                      "type":"AppliedTypeTree",
                      "children":[
                        {
                          "name":"Array",
                          "type":"Ident"
                        },
                        {
                          "name":"String",
                          "type":"Ident"
                        }
                      ]
                    }
                  ]
                },
                {
                  "type":"Select",
                  "children":[
                    {
                      "name":"scala",
                      "type":"Ident"
                    }
                  ]
                },
                {
                  "type":"Block",
                  "children":[
                    {
                      "name":"str",
                      "type":"String",
                      "children":[
                        {
                          "name":"String",
                          "type":"Ident"
                        },
                        {
                          "type":"Literal",
                          "value":""Hello, world!""
                        }
                      ]
                    },
                    {
                      "type":"Apply",
                      "children":[
                        {
                          "name":"println",
                          "type":"Ident"
                        },
                        {
                          "name":"str",
                          "type":"Ident"
                        }
                      ]
                    }
                  ]
                }
              ]
            }
          ]
        }
      ]
    }
  ]
}
```