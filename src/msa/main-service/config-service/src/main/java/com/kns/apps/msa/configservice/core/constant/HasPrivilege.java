package com.kns.apps.msa.configservice.core.constant;

public class HasPrivilege {

    //ADMINISTRATION
    public static final String ADMINISTRATION = "hasAuthority('ADMINISTRATION')";

    public static final String USER = "hasAuthority('USER')";
    public static final String USER_CREATE = "hasAuthority('USER_CREATE')";
    public static final String USER_EDIT = "hasAuthority('USER_EDIT')";
    public static final String USER_DELETE = "hasAuthority('USER_DELETE')";

    public static final String ROLE = "hasAuthority('ROLE')";
    public static final String ROLE_CREATE = "hasAuthority('ROLE_CREATE')";
    public static final String ROLE_EDIT = "hasAuthority('ROLE_EDIT')";
    public static final String ROLE_DELETE = "hasAuthority('ROLE_DELETE')";

    public static final String PRIVILEGE = "hasAuthority('PRIVILEGE')";
    public static final String PRIVILEGE_CREATE = "hasAuthority('PRIVILEGE_CREATE')";
    public static final String PRIVILEGE_EDIT = "hasAuthority('PRIVILEGE_EDIT')";
    public static final String PRIVILEGE_DELETE = "hasAuthority('PRIVILEGE_DELETE')";

    public static final String AUDITLOG = "hasAuthority('AUDITLOG')";
    public static final String AUDITLOG_CREATE = "hasAuthority('AUDITLOG_CREATE')";
    public static final String AUDITLOG_EDIT = "hasAuthority('AUDITLOG_EDIT')";
    public static final String AUDITLOG_DELETE = "hasAuthority('AUDITLOG_DELETE')";


}
