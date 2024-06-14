package com.sparta.igeomubwotna.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ApiLogging")
@Aspect
@Component
@RequiredArgsConstructor
public class ApiLogging {

	@Pointcut("execution(* com.sparta.igeomubwotna.controller.*.*(..))")
	private void controller() {
	}

	@Around("controller()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		Class targetClass = joinPoint.getTarget().getClass();
		Object result = null;

		try{
			result = joinPoint.proceed(joinPoint.getArgs());
			return result;
		} finally {
			log.info(getRequestUrl(joinPoint, targetClass));
		}

	}

	private String getRequestUrl(ProceedingJoinPoint joinPoint, Class targetClass) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		RequestMapping requestMapping = (RequestMapping) targetClass.getAnnotation(RequestMapping.class);
		String urlBase = requestMapping.value()[0];

		String url = Stream.of( GetMapping.class, PostMapping.class, PatchMapping.class, DeleteMapping.class, RequestMapping.class)
			.filter(mappingClass -> method.isAnnotationPresent(mappingClass))
			.map(mappingClass -> getUrl(method, mappingClass, urlBase))
			.findFirst().orElse(null);
		return url;
	}

	private String getUrl(Method method, Class<? extends Annotation> annotationClass, String urlBase){
		Annotation annotation = method.getAnnotation(annotationClass);
		String[] value;
		String httpMethod = null;

		try {
			value = (String[])annotationClass.getMethod("value").invoke(annotation);
			httpMethod = (annotationClass.getSimpleName().replace("Mapping", "")).toUpperCase();
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			return null;
		} return String.format("%s http://localhost:8080%s%s", httpMethod, urlBase, value.length > 0 ? value[0] : "");
	}


}
