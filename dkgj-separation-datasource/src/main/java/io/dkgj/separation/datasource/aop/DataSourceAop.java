package io.dkgj.separation.datasource.aop;

import io.dkgj.separation.datasource.bean.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/*
 * 在service层决定数据源
 *
 * 必须在事务AOP之前执行，所以实现Ordered,order的值越小，越先执行
 * 如果一旦开始切换到写库，则之后的读都会走写库
 */

@Aspect
@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)
@Component
public class DataSourceAop implements PriorityOrdered {
    private final static Logger logger = LoggerFactory.getLogger(DataSourceAop.class);

    @Pointcut("@annotation(io.dkgj.separation.datasource.annotation.WriteDataSource)")
    public void writeMethod() {
    }

    @Pointcut("@annotation(io.dkgj.separation.datasource.annotation.ReadDataSource)")
    public void readMethod() {
    }

    @Before("writeMethod() and execution(* io.dkgj.service.impl..*.*(..)) ")
    public void beforeWrite(JoinPoint point) {
        //设置数据库为写数据
        DataSourceContextHolder.write();
        //调试代码，可注释
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        logger.info("dataSource切换到：Write 开始执行:" + className + "." + methodName + "()方法...");
    }

    @Before("readMethod() and execution(* io.dkgj.service.impl..*.*(..)) ")
    public void beforeRead(JoinPoint point) throws ClassNotFoundException {
        //设置数据库为读数据
        DataSourceContextHolder.read();
        //调试代码，可注释
        String className = point.getTarget().getClass().getName();//根据切点获取当前调用的类名
        String methodName = point.getSignature().getName();//根据切点获取当前调用的类方法
        logger.info("dataSource切换到：Read 开始执行:" + className + "." + methodName + "()方法...");
//        Object[] args = point.getArgs();//根据切点获取当前类方法的参数
//        Class reflexClassName = Class.forName(className);//根据反射获取当前调用类的实例
//        Method[] methods = reflexClassName.getMethods();//获取该实例的所有方法
//        for (Method method : methods) {
//            if (method.getName().equals(methodName)) {
//                String desrciption = method.getAnnotation(ReadDataSource.class).description();//获取该实例方法上注解里面的描述信息
//                System.out.println("desrciption:" + desrciption);
//            }
//        }
    }

    @After("readMethod() || writeMethod()")
    public void after() {
        DataSourceContextHolder.clear();
    }

    @Override
    public int getOrder() {
        /**
         * 值越小，越优先执行
         * 要优于事务的执行
         * 在启动类中加上了@EnableTransactionManagement(order = 10)
         */
        return 1;
    }

}