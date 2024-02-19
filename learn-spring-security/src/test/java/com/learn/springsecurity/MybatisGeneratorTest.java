package com.learn.springsecurity;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.learn.springsecurity.entity.BaseEntity;

import java.sql.Types;
import java.util.Collections;

public class MybatisGeneratorTest {

    private static final  DataSourceConfig.Builder schema = new DataSourceConfig
                .Builder("jdbc:mysql://localhost:3306/auth?serverTimezone=Asia/Shanghai", "root", "root")
                .schema("baomidou");

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/auth?serverTimezone=Asia/Shanghai", "root", "root")
                .globalConfig(builder -> {
                    builder.author("SHX") // 设置作者
                            .outputDir("/Users/shx/code/java/learn-spring-security/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.learn.springsecurity.mybatis") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/shx/code/java/learn-spring-security/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_user_info") // 设置需要生成的表名
                            .addTablePrefix("t_") // 设置过滤表前缀
                            .entityBuilder().superClass(BaseEntity.class).build()
                    ;
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
