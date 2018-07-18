下载 [elasticsearch-5.6.8](https://www.elastic.co/downloads/past-releases/elasticsearch-5-6-8)

下载后解压

安装ik分词器

```
cd elasticsearch-5.6.8/bin/
./elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v5.6.8/elasticsearch-analysis-ik-5.6.8.zip
```

启动ES

```shell
cd elasticsearch-5.6.8/bin/
./elasticsearch
```

保持后台运行

可以使用 `screen` 也可以使用 `nohup` 自行 <del>百度</del> 谷歌