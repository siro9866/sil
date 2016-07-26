// ================================================================
// S:전자정부 프레임워크 사용 페이징 적용

※ 간단하게 메이븐으로 추가해서 사용 할 수 있으나 -mvc 같은 것 때문에 log4j 와 충돌을 일으켜 쿼리 로그 등이 정상적으로 나타나지 않아서 
실제 파일을 다운로드 받아 삽입해서 구현했다. 

＠ 메이븐으로 추가방법
pom.xml
	<repositories>
		<repository>
			<id>egovframe</id>
			<url>http://www.egovframe.go.kr/maven/</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>
	
	<!-- 전자정부 페이징 -->
	<dependency>
		<groupId>egovframework.rte</groupId>
		<artifactId>egovframework.rte.ptl.mvc</artifactId>
		<version>3.5.0</version>
	</dependency> 
	
context-common.xml
	<!-- 전자정부 페이징 설정  -->
	<bean id="textRenderer" class="egovframework.rte.ptl.mvc.tags.ui.pagination.DefaultPaginationRenderer" />
	<bean id="paginationManager" class="egovframework.rte.ptl.mvc.tags.ui.pagination.DefaultPaginationManager">
		<property name="rendererType">
			<map>
				<entry key="text" value-ref="textRenderer" />
			</map>
		</property>
	</bean>	

include-header.jspf
<%@ taglib prefix="ui" uri="/WEB-INF/config/pagination.tld" %>	



＠ 라이브러리 추가 말고 실제 파일 추가방법
전자정부프레임워크의 jar 파일을 다운로드 받아 새로운 패키지에 넣는다.
http://cacao.tobesoft.co.kr/nexus/content/repositories/egovFrame/egovframework/rte/egovframework.rte.ptl.mvc/3.5.0/

AbstractPaginationRenderer
DefaultPaginationManager
DefaultPaginationRenderer
PaginationInfo
PaginationManager
PaginationRenderer
PaginationTag

pagination.tld 파일 생성

include-header.jspf
<%@ taglib prefix="ui" uri="/WEB-INF/config/pagination.tld" %>


SQL- 완성된 쿼리에 최상단과 최하단에 각각 pagingPre, pagingPost 인클루드 해서 완성

<sql id="pagingPre">
	<![CDATA[
		select @i as TOTAL_COUNT, AAA.*
		from(
			select
				@i := @i + 1 as RNUM
				, AA.*
			from(
	]]>
</sql>

<sql id="pagingPost">
	<![CDATA[
				) AA, (select @i := 0, @j := 0) temp		
		) AAA		
		where AAA.RNUM limit #{START}, #{END}
	]]>
</sql>
// E:전자정부 프레임워크 사용 페이징 적용
// ================================================================

