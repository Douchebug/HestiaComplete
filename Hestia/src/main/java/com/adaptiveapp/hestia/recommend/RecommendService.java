package com.adaptiveapp.hestia.recommend;

import com.adaptiveapp.hestia.dal.RecommendDOMapper;
import com.adaptiveapp.hestia.model.RecommendDO;
import org.apache.spark.sql.sources.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendService implements Serializable {
    @Autowired
    private RecommendDOMapper recommendDOMapper;

    //recall data: according to userid, recall shopIdList
    public List<Integer> recall(Integer userId){
        RecommendDO recommendDO = recommendDOMapper.selectByPrimaryKey(userId);
        //default recommendation
        if(recommendDO == null){
            recommendDO = recommendDOMapper.selectByPrimaryKey(9999999);
        }
        String shopIdArray[] = recommendDO.getRecommend().split(",");
        List<Integer> shopIdList = new ArrayList<>();
        for(int i =0; i< shopIdArray.length; i++){
            shopIdList.add(Integer.valueOf(shopIdArray[i]));
        }
        return shopIdList;
    }
}
