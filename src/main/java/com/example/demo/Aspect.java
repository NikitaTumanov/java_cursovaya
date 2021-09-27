package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Класс, реализующий запись логов. Аспект (сквозная задача).
 */
@Slf4j
@Component
@org.aspectj.lang.annotation.Aspect
public class Aspect {

    /**
     * Метод начала работы логирования.
     * @param joinPoint точка в потоке выполнения, где происходит какое-то действие и появляется возможность
     *                  применить Аспект (сквозная задача).
     */
    @Before("allServiceMethods()")
    public void logParameters(JoinPoint joinPoint) {
        log.info("Parameters: {}", joinPoint.getArgs());
    }

    /**
     * Метод, подключающий к логированию все методы сервисов.
     */
    @Pointcut("within(com.example.demo.*)")
    public void allServiceMethods() {
    }

    /**
     * Метод, записывающий логи в консоль.
     * @param joinPoint точка в потоке выполнения, где происходит какое-то действие и появляется возможность
     *                      применить Аспект (сквозная задача).
     * @return Возвращение выполнения сквозной задачи.
     * @throws Throwable Базовый класс всех исключений Java.
     */
    @Around("allServiceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info("Method time: " + executionTime + "ms");
        return proceed;
    }
}