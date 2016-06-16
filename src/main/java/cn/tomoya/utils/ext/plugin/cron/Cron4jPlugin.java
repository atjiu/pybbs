/**
 * Copyright (c) 2011-2013, kidzhou 周磊 (zhouleib1412@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.tomoya.utils.ext.plugin.cron;

import cn.tomoya.utils.ext.kit.Reflect;
import cn.tomoya.utils.ext.kit.ResourceKit;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import it.sauronsoftware.cron4j.Scheduler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cron4jPlugin implements IPlugin {

    private static final String JOB = "job";

    private Map<Runnable, String> jobs = Maps.newLinkedHashMap();

    private String config;

    private Scheduler scheduler;

    private Map<String, String> jobProp;


    public Cron4jPlugin add(String jobCronExp, Runnable job) {
        jobs.put(job, jobCronExp);
        return this;
    }

    public Cron4jPlugin config(String config) {
        this.config = config;
        return this;
    }

    @Override
    public boolean start() {
        loadJobsFromProperties();
        startJobs();
        return true;
    }

    private void startJobs() {
        scheduler = new Scheduler();
        Set<Entry<Runnable, String>> set = jobs.entrySet();
        for (Entry<Runnable, String> entry : set) {
            scheduler.schedule(entry.getValue(), entry.getKey());
            LogKit.debug(entry.getValue() + " has been scheduled to run and repeat based on expression: " + entry.getKey());
        }
        scheduler.start();
    }

    private void loadJobsFromProperties() {
        if (StrKit.isBlank(config)) {
            return;
        }
        jobProp = ResourceKit.readProperties(config);
        Set<Map.Entry<String, String>> entries = jobProp.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            if (!key.endsWith(JOB) || !isEnableJob(enable(key))) {
                continue;
            }
            String jobClassName = jobProp.get(key) + "";
            String jobCronExp = jobProp.get(cronKey(key)) + "";
            Class<Runnable> clazz = Reflect.on(jobClassName).get();
            try {
                jobs.put(clazz.newInstance(), jobCronExp);
            } catch (Exception e) {
                Throwables.propagate(e);
            }
        }
    }

    private String enable(String key) {
        return key.substring(0, key.lastIndexOf(JOB)) + "enable";
    }

    private String cronKey(String key) {
        return key.substring(0, key.lastIndexOf(JOB)) + "cron";
    }

    private boolean isEnableJob(String enableKey) {
        Object enable = jobProp.get(enableKey);
        if (enable != null && "false".equalsIgnoreCase((enable + "").trim())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean stop() {
        scheduler.stop();
        return true;
    }

}