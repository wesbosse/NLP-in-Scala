// Databricks notebook source
// MAGIC %md
// MAGIC Test Message Spam Classification with NLP in Spark

// COMMAND ----------

import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler, StopWordsRemover, CountVectorizer, Tokenizer, IDF}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder}

// COMMAND ----------

var df = sqlContext
  .read
  .format("csv")
  .option("inferSchema", "true")
  .option("delimiter", "\t")
  .load("/FileStore/tables/SMSSpamCollection")

// COMMAND ----------

df.show()

// COMMAND ----------

var indexer = new StringIndexer()
  .setInputCol("_c0")
  .setOutputCol("label")

// COMMAND ----------

var tokenizer = new Tokenizer()
  .setInputCol("_c1")
  .setOutputCol("tokenized")

var stopWordsRemover = new StopWordsRemover()
  .setInputCol("tokenized")
  .setOutputCol("tokenizedNSW")

var countVectorizer = new CountVectorizer()
  .setInputCol("tokenized")
  .setOutputCol("countVectorized")

var vectorAssembler = new VectorAssembler()
  .setInputCols(Array("idfOutput"))
  .setOutputCol("features")

var idf = new IDF()
  .setInputCol("countVectorized")
  .setOutputCol("idfOutput")

// COMMAND ----------

var lr = new LogisticRegression()

// COMMAND ----------

var pipe = new Pipeline()
  .setStages(Array(indexer, tokenizer, countVectorizer, idf, vectorAssembler, lr))

// COMMAND ----------

var evaluator = new MulticlassClassificationEvaluator()
  .setMetricName("accuracy")

// COMMAND ----------

var params = new ParamGridBuilder()
  .addGrid(lr.elasticNetParam,Array(0,.1))
  .addGrid(countVectorizer.vocabSize,Array(1500, 2000, 3000))
  .build()

// COMMAND ----------

var cv = new CrossValidator()
  .setEstimator(pipe)
  .setEstimatorParamMaps(params)
  .setEvaluator(evaluator)

// COMMAND ----------

var Array(train, test) = df.randomSplit(Array(.8, .2), 42)
var cvModel = cv.fit(train)

// COMMAND ----------

var predictions = cvModel.transform(test)
evaluator.evaluate(predictions)
var maxScore = cvModel.avgMetrics.max
var maxIndex = cvModel.avgMetrics.indexOf(maxScore)

// COMMAND ----------

cvModel.getEstimatorParamMaps(maxIndex)

// COMMAND ----------


