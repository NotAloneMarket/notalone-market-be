package com.ddwu.notalonemarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:spring/dataAccessContext-mybatis.xml")
public class XmlImportConfig {
}
