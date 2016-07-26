package com.sangsil.sil.common.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Controller, Service, DAO가 실행될 때, 어떤 계층의 어떤 메서드가 실행되었는지를 보여주는 역할을 한다.
 * 먼저 @Around다. 
 
어드바이스는 어노테이션이 붙은 메서드를 이용해 정의한다.
 
AspectJ에서는 다섯 가지 종류의 Advice를 이용할 수 있는데 각각 @Before, @AfterReturning, @AfterThrowing, @After, @Around 어노테이션으로 표현된다.
 
@Around로 시작하기 때문에, Around Advice임을 알수 있다.
 



그 다음으로 "execution"은 포인트컷 표현식 부분이다. 
 
포인트컷 표현식은 위에서 사용된 execution()을 포함하여 여러가지 포인트컷 지시자(PointCut Designator)를 사용할 수 있다. 
 
그 종류는 다음과 같다.
■execution() : 가장 대표적이고 강력한 지시자로, 접근제어자, 리턴 타입, 타입 패턴, 메서드, 파라미터 타입, 예외 타입 등을 조합해서 메서드까지 선택가능한 가장 정교한 포인트컷을 만들수 있다.
■within() : 타입 패턴만을 이용하여 조인포인트를 정의한다.
■this : 빈 오브텍트의 타입의 조인토인트를 정의한다.
■target : 대상객체의 타입 비교를 이용한 조인포인트를 정의한다.
■args : 메서드의 파라미터 타입만을 이용하여 조인포인트를 정의한다.
■@target : 특정 어노테이션이 정의된 객체를 찾는 조인포인트를 정의한다.
■@args : 특정 어노테이션을 파라미터로 받는 오브젝트를 찾는 조인포인트를 정의한다. 
■@within : @target과 유사하게 특정 어노테이션이 정의된 객체를 찾는데, 선택될 조인포인트 메서드는 타겟 클래스에서 선언이 되어있어야 한다.
■@annotation : 조인 포인트 메서드에 특정 어노테이션을 찾는 조인포인트를 정의한다.

이렇게 종류를 써 놨지만, 필자도 다 사용을 해본것은 아니다. 
 
보통 execution 하나로 다 처리를 해버린다....ㅡ_ㅡ;;;
 



그 다음으로 * first..controller.*Controller.*(..) 부분을 살펴보자.
 
처음에 first.. 라고 되어있는것을 볼 수 있다. 이는 first 패키지 밑의 모든 서브패키지를 의미한다.
 
그 다음 controller. 는 controller 패키지 밑의 클래스와 인터페이스만을 지정한다. 
 
그 다음 *Controller.는 Controller라는 이름으로 끝나는 것을 의미한다.
 
마지막으로 *(..)은 모든 메서드를 의미한다.
 



그 다음으로 or는 포인트컷 표현식을 조합할 수 있다. 
 
포인트컷의 조합식에는 or, and, not 3가지를 사용할 수 있으며 각각 ||, &&, !으로 표현할 수도 있다. 
 
즉 @(..)) || execution 이런식으로 표현해도 무방하다는 의미이다. 

 */

@Aspect
public class LoggerAspect {
	protected Log logger = LogFactory.getLog(LoggerAspect.class);
	static String name = "";
	static String type = "";
	
//	@Around("execution(* com.lee.common..controller.*Controller.*(..)) or execution(* com.lee.common..service.*Impl.*(..)) or execution(* com.lee.common..dao.*DAO.*(..))")
	@Around("execution(* com.lee.sample..controller.*Controller.*(..)) or execution(* com.lee.sample..service.*Impl.*(..)) or execution(* com.lee.common..dao.*DAO.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		type = joinPoint.getSignature().getDeclaringTypeName();
		
		if (type.indexOf("Controller") > -1) {
			name = "Controller  \t:  ";
		}
		else if (type.indexOf("Service") > -1) {
			name = "ServiceImpl  \t:  ";
		}
		else if (type.indexOf("DAO") > -1) {
			name = "DAO  \t\t:  ";
		}
		logger.debug(name + type + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
}