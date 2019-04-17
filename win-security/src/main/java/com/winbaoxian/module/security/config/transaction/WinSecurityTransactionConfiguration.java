package com.winbaoxian.module.security.config.transaction;

import com.winbaoxian.module.security.config.AnnotationAttributesHolder;
import com.winbaoxian.module.security.config.EnableWinSecurityAttributeEnum;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongxuanliang252
 * @date 2018-12-14 10:29
 */
@Configuration
public class WinSecurityTransactionConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WinSecurityTransactionConfiguration.class);

    @Bean
    public Advisor wsAdvisor() {
        DefaultBeanFactoryPointcutAdvisor advisor = new DefaultBeanFactoryPointcutAdvisor();
        //切入点
        advisor.setPointcut(pointcut());
        //advice
        advisor.setAdvice(wyAdvice());
        //order
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

    private Pointcut pointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("within (com.winbaoxian.module.security.service.*Service)");
        return pointcut;
    }

    @Bean
    public Advice wyAdvice() {
        TransactionInterceptor advice = new TransactionInterceptor();
        advice.setTransactionManagerBeanName(AnnotationAttributesHolder.INSTANCE.getEnableWinSecurity().getString(EnableWinSecurityAttributeEnum.TRANSACTION_MANAGER_REF.getValue()));
        // methodName, values
        NameMatchTransactionAttributeSource attributes = new NameMatchTransactionAttributeSource();
        Map<String, TransactionAttribute> nameMap = new HashMap<>();
        // 不设置 使用默认值，PROPAGATION_REQUIRED、ISOLATION_DEFAULT
        nameMap.put("add*", new RuleBasedTransactionAttribute());
        nameMap.put("update*", new RuleBasedTransactionAttribute());
        nameMap.put("delete*", new RuleBasedTransactionAttribute());
        nameMap.put("dragAndDrop*", new RuleBasedTransactionAttribute());
        attributes.setNameMap(nameMap);
        advice.setTransactionAttributeSource(attributes);
        return advice;
    }

}
