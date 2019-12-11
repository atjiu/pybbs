package co.yiiu.pybbs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class ServerRunner implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(ServerRunner.class);

    @Override
    public void run(String... args) {
    }
}
