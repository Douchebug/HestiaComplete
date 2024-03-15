ind this in pom.xml and change it into your version

        <groupId>mysql</groupId>

        <artifactId>mysql-connector-java</artifactId>

        
        <version>5.1.41</version>
remember to reload your pom.xml

find this in application.properties file

        spring.datasource.driver-class-name=com.mysql.jdbc.Driver
change it into

        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#To design your compatible database

#compulsory

find ddl.sql file, change all "0000-00-00 00:00:00" into CURRENT_TIMESTAMP. e.g.:

created_at datetime(0) NOT NULL DEFAULT "0000-00-00 00:00:00" -> created_at datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP

make sure you created a database called hestiadb and then

execute the sql instruction in ddl.sql

(btw, you can ignore the dml.sql file, it's for my own test)

#if needed

change applicationproperties.xml:

        spring.datasource.username=root #change to your mysql user name
#compulsory

change applicationproperties.xml:

        spring.datasource.password=2000925xun. #change to your mysql password
#For operation platform, administrator account and password are as follows:

admin.email=hestia@tcd.ie

admin.encryptPassword=123456

#The home page of our app

http://localhost:8010/static/index.html

#Operation platform

http://localhost:8010/admin/admin/loginpage

Currently I've connected our project to googlemap api so it may ask your location permission, pls allow it.




#NEW THINGS:

Be sure to download elasticsearch.zip.

Change \logstash-7.3.1\bin\mysql\last_value_meta content to:
--- !ruby/object:DateTime '2000-03-15 14:20:00.169000000 Z'

Related commands: 

    PS C:\Users\10068\Downloads\Compressed\kibana-7.3.1-windows-x86_64\bin> .\kibana
    PS C:\Users\10068\Downloads\Compressed> .\elasticsearch-node1\bin\elasticsearch
    PS C:\Users\10068\Downloads\Compressed> .\elasticsearch-node2\bin\elasticsearch
    PS C:\Users\10068\Downloads\Compressed> .\elasticsearch-node3\bin\elasticsearch
    PS C:\Users\10068\Downloads\Compressed\logstash-7.3.1\bin> ./logstash -f mysql/jdbc.conf


## Kibana dev tools:

        delete /test 
    
    PUT /test 
    {
      "settings": {
        "number_of_shards": 1,
        "number_of_replicas": 1
      }
    }
    
    DELETE /employee
    
    //非结构化方式新建索引
    put /employee
    {
      "settings": {
        "number_of_shards": 1,
        "number_of_replicas": 1
      }
    }
    
    PUT /employee/_doc/1
    {
      "name":"Qiao2",
      "age":24
    }
    
    PUT /employee/_doc/1
    {
      "name":"Qiao3"
    }
    
    //获取索引记录
    get /employee/_doc/1
    
    //指定字段修改
    POST /employee/_update/1
    {
      "doc": {
        "name":"Qiao4"
      }
    }
    
    
    //强制指定创建，若已存在则失败
    
    POST /employee/_create/4
    {
      "name":"123 33 33333",
      "age":3
    }
    
    
    //删除某个文档
    DELETE /employee/_doc/3
    
    //查询全部文档
    
    GET /employee/_search
    
    
    //使用结构化的方式创建索引
    
    put /employee
    {
      "settings": {
        "number_of_shards": 1,
        "number_of_replicas": 1
      },
      "mappings":{
        "properties":{
          "name":{"type":"text"},
          "age":{"type":"integer"}
        }
      }
    }
    
    PUT /employee/_doc/1
    {
      "name":"Qiao Yongxun",
      "age":24
    }
    
    POST /employee/_create/2
    {
      "name":"123",
      "age":30
    }
    
    //不带条件查询所有记录
    get /employee/_search
    {
      "query":{
        "match_all": {}
      }
    }
    
    //分页查询
    get /employee/_search
    {
      "query":{
        "match_all": {}
      },
      "from":1,
      "size":10
    }
    
    //带关键字条件的查询
    get /employee/_search
    {
      "query":{
        "match":{"name":"123"}
      }
    }
    
    //带排序的查询
    get /employee/_search
    {
      "query":{
        "match":{"name":"123"}
      },
      "sort":[
        {"age":{"order":"desc"}
        }
      ]
    }
    
    //带filter的查询
    //match与term的区别，match会对内容进行分词匹配，而term不会
    //filter相当于mysql中的where
    get /employee/_search
    {
      "query":{
        "bool":{
          "filter":[
            {"term":{"age":30}}
            ]
        }
      }
    }
    
    //带聚合的查询
    get /employee/_search
    {
      "query":{
        "match":{"name":"123"}
      },
      "sort":[
        {"age":{"order":"desc"}
        }
      ],
      "aggs":{
        "group_by_age":{
          "terms":{
            "field":"age"
          }
        }
      }
    }
    
    //新建一个索引
    put /movie/_doc/1
    {
      "name":"Eating an apple a day & keeps the doctor away"
    }
    
    Get /movie/_search
    {
      "query":{
        "match": {
          "name": "an"
        }
      }
    }
    //使用analyze api查看分词状态
    get /movie/_analyze
    {
      "field":"name",
      "text":"Eating an apple a day & keeps the doctor away"
    }
    
    delete /movie
    
    //使用结构化方式重新建索引
    put /movie
    {
      "settings":{
        "number_of_shards":1,
        "number_of_replicas":1
      },
      "mappings":{
        "properties":{
          "name":{"type":"text", "analyzer":"english"}
        }
      }
    }
    
    get /movie/_search
    
    //tmdb part
    //使用结构化方式重新建索引
    put /movie
    {
      "settings":{
        "number_of_shards":1,
        "number_of_replicas":1
      },
      
      
      
      "mappings":{
        "properties":{
          "name":{"type":"text", "analyzer":"english"}
        }
      }
    }
    
    
    delete /shop
    //define index structure for shop
    //fielddata: for aggregation
    //difference between text type and keyword type: text can be analyzed, whiile keyword is a string
    put /shop
    {
      "settings":{
        "number_of_shards":1,
        "number_of_replicas":1
      },
      "mappings":{
        "properties":{
          "id":{"type":"integer"},
          "name":{"type":"text","analyzer":"english","search_analyzer":"english"},
          "tags":{"type":"text","analyzer":"whitespace","fielddata":true},
          "location":{"type":"geo_point"},
          "remark_score":{"type":"double"},
          "price_per_man":{"type":"integer"},
          "category_id":{"type":"integer"},
          "category_name":{"type":"keyword"},
          "seller_id":{"type":"integer"},
          "seller_remark_score":{"type":"double"},
          "seller_disabled_flag":{"type":"integer"}
        }
      }
    }
    //script: caculate the distance between your place and the restaurant
    get /shop/_search
    {
      "query":{
        "match": {
          "name": "和"
        }
      },
      "_source":"*",
      "script_fields":{
          "distance":{
            "script":{
              "source":"haversin(lat,lon,doc['location'].lat,doc['location'].lon)",
              "lang":"expression",
              "params":{"lat":31.37,"lon":127.12}
            }
          }
        }
    }
    
    // sort by distance
    
    get /shop/_search
    {
      "query":{
        "match": {
          "name": "和"
        }
      },
      "_source":"*",
      "script_fields":{
          "distance":{
            "script":{
              "source":"haversin(lat,lon,doc['location'].lat,doc['location'].lon)",
              "lang":"expression",
              "params":{"lat":31.37,"lon":127.12}
            }
          }
        },
        "sort":[
        {
          "_geo_distance":{
            "location":{
              "lat":31.37,
              "lon":127.12
            },
            "order":"asc",
            "unit":"km",
            "distance_type":"arc"
          }
        }
      ]
    }
    
    //sort using function_score
    get /shop/_search
    {
      "_source": "*",
      "script_fields":{
          "distance":{
            "script":{
              "source":"haversin(lat,lon,doc['location'].lat,doc['location'].lon)",
              "lang":"expression",
              "params":{"lat":31.23916171,"lon":121.48789949}
            }
          }
        },
        "query":{
          "function_score": {
            "query": {
               "bool":{
                  "must":[
                    {"match":{"name":{"query":"凯悦","boost":0.1}}},
                    {"term":{"seller_disabled_flag": 0}},
                    {"term":{"tags": "落地大窗"}}
                  ]
                }
              },
            "functions": [
              {
                "gauss":{
                  "location":{
                    "origin":"31.23916171,121.48789949",
                    "scale":"100km",
                    "offset":"0km",
                    "decay": 0.5
                  }
                },
                "weight": 9
              },
              {
                "field_value_factor": {
                  "field": "remark_score"
                },
                "weight": 0.2
              },
              {
                "field_value_factor": {
                  "field": "seller_remark_score"
                },
                "weight": 0.1
              }
            ],
            "score_mode": "sum",
            "boost_mode": "sum"
          }
        },
        "sort":[
            {
              "_score":{
                "order":"desc"
              }
            }
          ],
        "aggs":{
          "group_by_tags":{
            "terms": {
              "field":"tags"
            }
          }
        }
        
    }
    
    post /shop/_update_by_query
    {
      "query":{
        "bool":{
          "must":[
            {"term":{"name":"凯"}},
            {"term":{"name":"悦"}}
          ]
        }
      }
    }
    
    
    
    delete /shop
    //define index structure for shop with synonyms
    //fielddata: for aggregation
    //difference between text type and keyword type: text can be analyzed, whiile keyword is a string
    put /shop
    {
      "settings":{
        "number_of_shards":1,
        "number_of_replicas":1,
        "analysis":{
          "filter":{
            "my_synonym_filter":{
              "type":"synonym",
              "synonyms_path":"English/synonyms.txt"
            }
          },
          "analyzer":{
            "English_syno":{
              "type":"custom",
              "tokenizer":"standard",
              "filter":["my_synonym_filter"]
            },
            "English_syno_whitespace":{
              "type":"custom",
              "tokenizer":"whitespace",
              "filter":["my_synonym_filter"]
            }
          }
        }
      },
      "mappings":{
        "properties":{
          "id":{"type":"integer"},
          "name":{"type":"text","analyzer":"English_syno","search_analyzer":"English_syno"},
          "tags":{"type":"text","analyzer":"whitespace","fielddata":true},
          "location":{"type":"geo_point"},
          "remark_score":{"type":"double"},
          "price_per_man":{"type":"integer"},
          "category_id":{"type":"integer"},
          "category_name":{"type":"keyword"},
          "seller_id":{"type":"integer"},
          "seller_remark_score":{"type":"double"},
          "seller_disabled_flag":{"type":"integer"}
        }
      }
    }
    
    get /shop/_analyze
    {
      "field":"name",
      "text":"iphone"
    }
    //Adopting a lexical influence recall strategy model
    get /shop/_search
    {
      "_source": "*",
      "script_fields":{
          "distance":{
            "script":{
              "source":"haversin(lat,lon,doc['location'].lat,doc['location'].lon)",
              "lang":"expression",
              "params":{"lat":31.23916171,"lon":121.48789949}
            }
          }
        },
        "query":{
          "function_score": {
            "query": {
               "bool":{
                  "must":[
                    {
                      "bool":{
                        "should":[
                          {"match":{"name":{"query":"住宿","boost":0.1}}},
                          {"term":{"category_id": {"value": 2,"boost":0}}}
                        ]
                      }
                    },
                    {"term":{"seller_disabled_flag": 0}}
                  ]
                }
              },
            "functions": [
              {
                "gauss":{
                  "location":{
                    "origin":"31.23916171,121.48789949",
                    "scale":"100km",
                    "offset":"0km",
                    "decay": 0.5
                  }
                },
                "weight": 9
              },
              {
                "field_value_factor": {
                  "field": "remark_score"
                },
                "weight": 0.2
              },
              {
                "field_value_factor": {
                  "field": "seller_remark_score"
                },
                "weight": 0.1
              },
              {
                "filter":{"term":{"category_id": 2}},
                "weight": 0.2
              }
            ],
            "score_mode": "sum",
            "boost_mode": "sum"
          }
        },
        "sort":[
            {
              "_score":{
                "order":"desc"
              }
            }
          ],
        "aggs":{
          "group_by_tags":{
            "terms": {
              "field":"tags"
            }
          }
        }
        
    }

#Remember to change configuration/path in Hestia project to make it compatible with your pc!!!

#New table in mysql

    CREATE TABLE `hestiadb`.`Untitled`  (
      `id` int(11) NOT NULL,
      `recommend` varchar(1000) NOT NULL,
      PRIMARY KEY (`id`)
    ); 


##Future work:
Find synonyms, fill them in the synonyms.txt
Generate training data.
Come up with the category name(better be 8 categories)
Find synonyms for categories, fill them in the program.
Generate training data.
