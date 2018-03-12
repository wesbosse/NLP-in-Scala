// Databricks notebook source
// set a variable and a constant
var firstName = "Wesley"
val lastName = "Bosse"

firstName.slice(0,2)

// string concatination
val fullName = firstName + " " + lastName

// array creation and member addition

myArray :+= 5

// array methods
myArray.tail
myArray.size
myArray.min
myArray.max
myArray.sum
myArray.product

// COMMAND ----------

var myTuple = (1,2,3)
myTuple._3

// COMMAND ----------

var me = Map(("firstName", firstName), ("lastName", lastName), ("age", 24))

//me("middleName") //errors
me.get("middleName") //returns None

me += ("middleName" -> "Allen")
for ((key, value) <- me) {
  print(key + ": ")
  println(value)
}

me.keys
me.values

// COMMAND ----------

// var myBoolean = false
if (myBoolean) {
  print("true")
} else if (2 == 3 || 2 == 2) {
 print("second true") 
} else {
  print("else")
}

// COMMAND ----------

def multiply(num1:Int, num2:Int): Int = {
  return num1 * num2
}

multiply(6, 100)

// COMMAND ----------

def printFunc(string:String): Unit = {
  println(string)
}

printFunc("Hey")

// COMMAND ----------

var inlineFunction = (name:String) => println(s"Hello, $name")

// COMMAND ----------

inlineFunction("hello")

// COMMAND ----------

def stripAnchor (url:String): String = {
  var urlSplit = url.split('#')
  return urlSplit(1)
}

stripAnchor("google.com#anchor")

// COMMAND ----------

def fizzBuzz(x:Int) = {
  val list = 1 to x
  for (x <- list) {
    if (x % 15 == 0)
      println("FizzBuzz")
    else if (x % 3 == 0)
      println("Fizz");
      println(x);
    else if (x % 5 == 0)
      println("Buzz")
    else
      println(x)
  }

}

// COMMAND ----------

fizzBuzz(100)

// COMMAND ----------


