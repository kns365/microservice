package com.kns.apps.microservice.configserver.core.constant;

import java.text.SimpleDateFormat;

public class PagingConst {

    public static final SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static final Integer DEFAULT_PAGE_NUMBER = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "desc";
    public static final Integer DEFAULT_PAST_DAY = 30;
}
