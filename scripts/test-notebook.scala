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
      println("Fizz")
    else if (x % 5 == 0)
      println("Buzz")
    else
      println(x)
  }

}

// COMMAND ----------

fizzBuzz(100)

// COMMAND ----------

var df = sqlContext
  .read
  .format("csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load("/FileStore/tables/train.csv")

// COMMAND ----------

df.show(200)

// COMMAND ----------

df.printSchema

// COMMAND ----------

df.columns

// COMMAND ----------

numericCols = Array()
for (dtype <- df.dtypes) {
  if dtype
  println(dtype)
}

// COMMAND ----------

var numericalColumns = df.dtypes.filter(colTup => colTup._2 == "IntegerType" || colTup._2 == "DoubleType").map(colTup => colTup._1)

// COMMAND ----------

df.describe().show()

// COMMAND ----------

df.filter($"Age" >= 21).show()

// COMMAND ----------

import org.apache.spark.sql.functions.{avg, corr, count}

// COMMAND ----------

df.filter("Embarked = 'S'").select(avg("Survived")).show()

// COMMAND ----------

df.select(corr("Age", "Survived")).show()

// COMMAND ----------

df = df.na.fill("N/A")
df = df.na.fill(1234567)

// COMMAND ----------

df.show(100)

// COMMAND ----------

df.groupBy("Embarked").agg(avg("Survived"), count("Survived")).show()

// COMMAND ----------

df = df.withColumnRenamed("Survived", "label")

// COMMAND ----------

df.show()

// COMMAND ----------

df = df.withColumn("FamilyCount", $"SibSp" + $"Parch")

// COMMAND ----------


