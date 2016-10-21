package cn.tomoya.module.topic.elastic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Document(indexName = "topic", type = "topic", shards = 1, replicas = 0, refreshInterval = "-1")
@Getter
@Setter
public class ElasticTopic {

    @Id
    private String id;

    private int topicId;

    private String title;

    private String content;
}
