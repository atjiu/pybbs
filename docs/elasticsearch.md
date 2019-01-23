The program has built-in elasticsearch, but you need to add the corresponding configuration to use

This feature is off by default. The specific configuration method is as follows:

1. Download elasticsearch, Version 6.5.3
2. Install the ik tokenizer, if you understand the command line operation, you can execute this command
    `cd elasticsearch-6.5.3/bin && ./elasticsearch-plugin https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.5.3/elasticsearch-analysis-ik-6.5.3.zip`
3. Start the program, enter the backend, open the system settings
4. The specific operation is shown in the figure below.

Turn on the search function switch

![](./assets/QQ20190103-135005.png)

Configure the connection of the ES (the connection information is modified according to your own environment, 
not necessarily the configuration shown in the figure)

![](./assets/QQ20190103-135046.png)
