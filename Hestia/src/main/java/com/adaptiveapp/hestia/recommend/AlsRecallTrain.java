package com.adaptiveapp.hestia.recommend;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.io.Serializable;

public class AlsRecallTrain implements Serializable {


    public static void main(String[] args) throws IOException {

        //initialize spark running environment
        SparkSession spark = SparkSession.builder().master("local").appName("HestiaApp").getOrCreate();

        JavaRDD<String> csvFile = spark.read().textFile("file:///C:/Users/10068/Desktop/Hestia/behavior.csv").toJavaRDD();//change to your own path

        JavaRDD<Rating> ratingJavaRDD = csvFile.map(new Function<String, Rating>() {
            @Override
            public Rating call(String v1) throws Exception {
                return Rating.parseRating(v1);
            }
        });

        Dataset<Row> rating = spark.createDataFrame(ratingJavaRDD, Rating.class);

        //split the data: 4:1
        Dataset<Row>[] splits = rating.randomSplit(new double[]{0.8,0.2});

        Dataset<Row> trainingData = splits[0];
        Dataset<Row> testingData = splits[1];

        //how to solve overfit: more data or smaller rank or bigger normalisation param(RegParam)
        //*************underfit: bigger rank or smaller normalisation param(RegParam)
        ALS als = new ALS().setMaxIter(10).setRank(5).setRegParam(0.01)
                .setUserCol("userId").setItemCol("shopId").setRatingCol("rating");
        //train
        ALSModel alsModel = als.fit(trainingData);

        //evaluate model
        Dataset<Row> predictions = alsModel.transform(testingData);

        //rmse evaluation: the greater rmse, the worse model
        RegressionEvaluator evaluator = new RegressionEvaluator().setMetricName("rmse")
                .setLabelCol("rating").setPredictionCol("prediction");
        double rmse = evaluator.evaluate(predictions);
        System.out.println("rmse = "+ rmse);

        //save model
        alsModel.save("file:///C:/Users/10068/Desktop/Hestia/alsmodel");
    }

    public static class Rating implements Serializable{

        //Future work: we need training data. Three columns: userId, shopId and rating(0-5)
        private int userId;
        private int shopId;
        private int rating;

        public Rating(int userId, int shopId, int rating) {
            this.userId = userId;
            this.shopId = shopId;
            this.rating = rating;
        }

        public static Rating parseRating(String str){
            str = str.replace("\"","");
            String[] strArr = str.split(",");
            int userId = Integer.parseInt(strArr[0]);
            int shopId = Integer.parseInt(strArr[1]);
            int rating = Integer.parseInt(strArr[2]);

            return new Rating(userId, shopId, rating);
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }
}
