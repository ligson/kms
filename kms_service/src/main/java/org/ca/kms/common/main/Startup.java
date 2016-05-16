package org.ca.kms.common.main;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * Created by ligson on 2016/5/4.
 */
public class Startup {
    static BouncyCastleProvider provider = new BouncyCastleProvider();

    static {
        Security.addProvider(provider);
    }

    public static void main(String[] args) {
        com.alibaba.dubbo.container.Main.main(args);
    }
}
