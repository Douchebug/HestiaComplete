package com.adaptiveapp.hestia.recommend;

import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendSortService {

    private SparkSession spark;

    private LogisticRegressionModel lrModel;

    @PostConstruct
    public void init(){
        //load model
        SparkSession spark = SparkSession.builder().master("local").appName("HestiaApp").getOrCreate();
        lrModel = LogisticRegressionModel.load("file:///C:/Users/10068/Desktop/Hestia/lrmodel");
    }

    public List<Integer> sort(List<Integer> shopIdList, Integer userId){
        //according to the 11-dimension feature x that lrmodel needs, generate the features
        //then call the prediction function
        List<ShopSortModel> list = new ArrayList<>();
        for(Integer shopId: shopIdList){
            //fake data, we can get corresponding gender, age, score, price etc.
            //from database or cache to Do feature transformation to generate feature vector
            Vector v = Vectors.dense(1,0,0,0,0,1,0.6,0,0,1,0);
            Vector result = lrModel.predictProbability(v);
            double[] arr = result.toArray();
            double score = arr[1];
            ShopSortModel shopSortModel = new ShopSortModel();
            shopSortModel.setShopId(shopId);
            shopSortModel.setScore(score);
            list.add(shopSortModel);
        }

        list.sort(new Comparator<ShopSortModel>() {
            @Override
            public int compare(ShopSortModel o1, ShopSortModel o2) {
                if(o1.getScore() < o2.getScore())
                    return 1;
                else if (o1.getScore() > o2.getScore())
                    return -1;
                else
                    return 0;
            }
        });
        return list.stream().map(shopSortModel -> shopSortModel.getShopId()).collect(Collectors.toList());
    }

}
