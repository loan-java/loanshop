/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.demo.com
 * <p>
 * 版权所有，侵权必究！
 */

package io.dkgj.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


/**
 * 生成验证码配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "6");
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.image.height", "80");
        properties.put("kaptcha.textproducer.font.names", "Arial, Courier");
        properties.put("kaptcha.obscurificator.impl", "io.dkgj.config.TxyzmGimpyEngine");
        properties.put("kaptcha.word.impl", "io.dkgj.config.TxyzmWordRenderer");
        properties.put("kaptcha.textproducer.char.string", "0123456789");
        properties.put("kaptcha.background.clear.from", "white");
        properties.put("kaptcha.background.clear.to", "white");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
