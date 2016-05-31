package com.edsoft;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
       /* ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"application-context.xml"});
        BeanFactory factory = appContext;*/

       // System.out.println("");

        Program program = new Program();
        program.startDemos();
    }
}
