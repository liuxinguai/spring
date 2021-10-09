package com.github.springframework.ioc.annotation.imp;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Import(Women.class)
@Configuration
public class ImportConfiguration {
}
