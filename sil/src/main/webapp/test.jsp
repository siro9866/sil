<authentication-manager>
<authentication-provider user-service-ref="customJdbcDaoImpl" />
</authentication-manager>
 
<beans:bean id="customJdbcDaoImpl" class="com.sangsil.sil.common.security.CustomJdbcDaoImpl">
<beans:property name="dataSource" ref="logDataSource_pos" />
<beans:property name="rolePrefix" value="" />
<beans:property name="usersByUsernameQuery" value="SELECT USER_ID, USER_PW, USER_NAME FROM S_MEMBERINFO WHERE USER_ID = ?" />
<beans:property name="authoritiesByUsernameQuery" value="SELECT ROLE_ID FROM S_MEMBER_ROLL WHERE USER_ID=?" />
<beans:property name="enableGroups" value="false" />
</beans:bean>	