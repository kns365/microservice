package com.kns.apps.msa.configservice.application.helper;

import com.google.common.base.CharMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StringHelper {

    public StringHelper() {

    }

    public static String getNumberFromString(String input) {
        String num = CharMatcher.inRange('0', '9').retainFrom(input);
        return num;
    }




}
