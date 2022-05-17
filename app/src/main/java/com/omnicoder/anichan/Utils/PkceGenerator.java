package com.omnicoder.anichan.Utils;

import org.apache.commons.lang3.RandomStringUtils;

public class PkceGenerator {
    public static final String CODE_VERIFIER_STRING="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-._~";

    public static String generateVerifier(){
        return RandomStringUtils.random(128,CODE_VERIFIER_STRING);
    }


}
