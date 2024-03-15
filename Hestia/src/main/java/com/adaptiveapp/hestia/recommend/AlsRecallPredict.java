package com.adaptiveapp.hestia.recommend;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.ForeachPartitionFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class AlsRecallPredict {

    public static void main(String[] args){


        SparkSession spark = SparkSession.builder().master("local").appName("HestiaApp").getOrCreate();

        //load model
        //ALSModel alsModel = ALSModel.read().load("file:///C:/Users/10068/Desktop/Hestia/alsmodel");
        ALSModel alsModel = ALSModel.load("file:///C:/Users/10068/Desktop/Hestia/alsmodel");

        JavaRDD<String> csvFile = spark.read().textFile("file:///C:/Users/10068/Desktop/Hestia/behavior.csv").toJavaRDD();//change to your own path

        JavaRDD<AlsRecallTrain.Rating> ratingJavaRDD = csvFile.map(new Function<String, AlsRecallTrain.Rating>() {
            @Override
            public AlsRecallTrain.Rating call(String v1) throws Exception {
                return AlsRecallTrain.Rating.parseRating(v1);
            }
        });

        Dataset<Row> rating = spark.createDataFrame(ratingJavaRDD, AlsRecallTrain.Rating.class);

        //Predicting Recall Outcomes Offline for Five Users
        Dataset<Row> users = rating.select(alsModel.getUserCol()).distinct().limit(5);

        Dataset<Row> userRecs = alsModel.recommendForUserSubset(users, 20);

        userRecs.foreachPartition(new ForeachPartitionFunction<Row>() {



            @Override
            public void call(Iterator<Row> t) throws Exception {
                //link a new database
                Connection  connection = DriverManager
                        .getConnection("jdbc:mysql://127.0.0.1:3306/hestiadb?"+
                                "user=root&password=2000925xun.&useUnicode=true&characterEncoding=UTF-8");
                PreparedStatement preparedStatement = connection.prepareStatement("insert into recommend(id,recommend)values(?,?)");

                List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();

                t.forEachRemaining(action->{
                    int userId = action.getInt(0);
                    List<GenericRowWithSchema> recommendationList = action.getList(1);
                    List<Integer> shopIdList = new ArrayList<Integer>();
                    recommendationList.forEach(row->{
                        Integer shopId = row.getInt(0);
                        shopIdList.add(shopId);
                    });
                    String recommendData = StringUtils.join(shopIdList,",");
                    Map<String,Object>map = new HashMap<String,Object>();
                    map.put("userId",userId);
                    map.put("recommend", recommendData);
                    data.add(map);
                });

                data.forEach(stringObjectMap -> {
                    try {
                        preparedStatement.setInt(1, (Integer) stringObjectMap.get("userId"));
                        preparedStatement.setString(2,(String) stringObjectMap.get("recommend"));

                        preparedStatement.addBatch();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                });
                preparedStatement.executeBatch();
                connection.close();
            }
        });

    }

    public static class Rating implements Serializable {

        //Future work: we need training data. Three columns: userId, shopId and rating(0-5)
        private int userId;
        private int shopId;
        private int rating;

        public Rating(int userId, int shopId, int rating) {
            this.userId = userId;
            this.shopId = shopId;
            this.rating = rating;
        }

        public static AlsRecallTrain.Rating parseRating(String str) {
            str = str.replace("\"", "");
            String[] strArr = str.split(",");
            int userId = Integer.parseInt(strArr[0]);
            int shopId = Integer.parseInt(strArr[1]);
            int rating = Integer.parseInt(strArr[2]);

            return new AlsRecallTrain.Rating(userId, shopId, rating);
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
