package org.ca.kms.common.main;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.ca.ext.security.sm.TopSMProvider;

import java.security.Security;

/**
 * Created by ligson on 2016/5/4.
 */
public class Startup {
    public static final BouncyCastleProvider bcProvider = new BouncyCastleProvider();
    public static final TopSMProvider topSMProvider = new TopSMProvider();

    static {
        Security.addProvider(bcProvider);
        Security.addProvider(topSMProvider);
    }

    public static void main(String[] args) {
        com.alibaba.dubbo.container.Main.main(args);
    }
}
