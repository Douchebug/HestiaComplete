package com.adaptiveapp.hestia.recommend;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;

import java.io.IOException;

public class LRTrain {
    public static void main(String[] args) throws IOException {
        SparkSession spark = SparkSession.builder().master("local").appName("HestiaApp").getOrCreate();

        //load feature document
        JavaRDD<String> csvFile = spark.read().textFile("file:///C:/Users/10068/Desktop/Hestia/feature.csv").toJavaRDD();
        JavaRDD<Row> rowJavaRDD = csvFile.map(new Function<String, Row>() {
            @Override
            public Row call(String v1) throws Exception {
                v1 = v1.replace("\"","");
                String[] strArr = v1.split(",");
                return RowFactory.create(new Double(strArr[11]), Vectors.dense(Double.valueOf(strArr[0]), Double.valueOf(strArr[1])
                        ,Double.valueOf(strArr[2]),Double.valueOf(strArr[3]),Double.valueOf(strArr[4])
                        ,Double.valueOf(strArr[5]),Double.valueOf(strArr[6])
                        ,Double.valueOf(strArr[7]),Double.valueOf(strArr[8])
                        ,Double.valueOf(strArr[9]),Double.valueOf(strArr[10])));
            }
        });
        StructType schema = new StructType(
                new StructField[]{
                        new StructField("label", DataTypes.DoubleType,false, Metadata.empty()),
                        new StructField("features", new VectorUDT(),false,Metadata.empty())
                }
        );

        Dataset<Row> data = spark.createDataFrame(rowJavaRDD, schema);

        //split training set and testing set
        Dataset<Row>[] dataArr = data.randomSplit(new double[]{0.8,0.2});
        Dataset<Row> trainData = dataArr[0];
        Dataset<Row> testData = dataArr[1];

        LogisticRegression lr = new LogisticRegression()
                .setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8)
                .setFamily("multinomial");
        LogisticRegressionModel lrModel = lr.fit(trainData);
        lrModel.save("file:///C:/Users/10068/Desktop/Hestia/lrmodel");

        Dataset<Row> predictions = lrModel.transform(testData);

        //evaluation
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator();
        double accuracy = evaluator.setMetricName("accuracy").evaluate(predictions);

        System.out.println("Accuracy: "+ accuracy);
    }
}
