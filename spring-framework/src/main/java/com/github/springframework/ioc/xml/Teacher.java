package com.github.springframework.ioc.xml;

import com.github.springframework.ioc.Student;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class Teacher {

    @Autowired
    private Student student;

}
