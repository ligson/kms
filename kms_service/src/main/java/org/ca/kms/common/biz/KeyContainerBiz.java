package org.ca.kms.common.biz;

import org.ca.ext.security.util.CertUtil;
import org.ca.kms.common.model.KeyPairContainer;
import org.ca.kms.common.utils.KeyStoreUtil;
import org.ligson.fw.core.common.utils.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by ligson on 2016/5/12.
 */
@Component("keyContainerBiz")
public class KeyContainerBiz implements InitializingBean {

    @Value(value = "#{cfg['ks.keyJks']}")
    private String keyJks;
    @Value(value = "#{cfg['ks.keyPwd']}")
    private String keyPwd;
    @Value(value = "#{cfg['ks.certJks']}")
    private String certJks;
    @Value(value = "#{cfg['ks.certPwd']}")
    private static String certPwd;
    private static KeyStore keyStore;
    private static KeyStore certStore;
    private static Logger logger = LoggerFactory.getLogger(KeyContainerBiz.class);

    @Override
    public void afterPropertiesSet() throws Exception {

        keyStore = KeyStore.getInstance("JKS");
        File keyJksFile = new File(keyJks);
        if (keyJksFile.exists() && keyJksFile.length() != 0) {
            logger.info("开始加载已存在密钥库.........");
            keyStore.load(new FileInputStream(keyJks), keyPwd.toCharArray());
            logger.info("加载已存在密钥库完成.........");
        } else {
            keyJksFile.createNewFile();
            keyStore.load(null, null);
        }

        File certJksFile = new File(certJks);
        certStore = KeyStore.getInstance("JKS");
        if (certJksFile.exists() && certJksFile.length() != 0) {
            logger.info("开始加载已存在证书库.........");
            certStore.load(new FileInputStream(certJksFile), certPwd.toCharArray());
            logger.info("加载已存在证书库完成.........");
        } else {
            certJksFile.createNewFile();
            certStore.load(null, null);
        }

    }

    public void storeCert(String aliase, Certificate[] chain) {
        KeyPairContainer container = getKeyPair(aliase);
        try {
            certStore.setKeyEntry(aliase, container.getPrivateKey(), certPwd.toCharArray(), chain);
            certStore.store(new FileOutputStream(certJks), certPwd.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeKey(String aliase, PublicKey publicKey, PrivateKey privateKey) throws Exception {
        Certificate certificate = CertUtil.wrapToCertContainer(publicKey);
        keyStore.setKeyEntry(aliase, privateKey, keyPwd.toCharArray(), new Certificate[]{certificate});
        keyStore.store(new FileOutputStream(keyJks), keyPwd.toCharArray());
    }

    public byte[] sign(byte[] buffer, Certificate certificate) {
        try {
            String alias = keyStore.getCertificateAlias(certificate);
            KeyPairContainer container = getKeyPair(alias);
            String signAlg = "SHA1withRSA";
            if ("RSA".equals(container.getType())) {
                signAlg = "SHA1withRSA";
            } else if ("SM2".equals(container.getType())) {
                signAlg = "SM3withSM2";
            }
            PrivateKey privateKey = container.getPrivateKey();
            Signature signature = Signature.getInstance(signAlg);
            signature.initSign(privateKey);
            signature.update(buffer);
            return signature.sign();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verfiy(byte[] buffer, byte[] sourceBuffer, Certificate certificate) {
        try {
            String alias = keyStore.getCertificateAlias(certificate);
            KeyPairContainer container = getKeyPair(alias);
            String signAlg = "SHA1withRSA";
            if ("RSA".equals(container.getType())) {
                signAlg = "SHA1withRSA";
            } else if ("SM2".equals(container.getType())) {
                signAlg = "SM3withSM2";
            }
            Signature signature = Signature.getInstance(signAlg);
            signature.initVerify(certificate.getPublicKey());
            signature.update(sourceBuffer);
            return signature.verify(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public byte[] encrypt(byte[] buffer, PublicKey publicKey) {
        String alg = publicKey.getAlgorithm();
        try {
            Cipher cipher = Cipher.getInstance(alg);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipher.update(buffer);
            return cipher.doFinal();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decrypt(byte[] buffer, Certificate certificate) {
        try {
            String alias = certStore.getCertificateAlias(certificate);
            KeyPairContainer container = getKeyPair(alias);
            PrivateKey privateKey = container.getPrivateKey();
            Cipher cipher = Cipher.getInstance(container.getType());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            cipher.update(buffer);
            return cipher.doFinal();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> keyAliases() {
        List<String> aliases = new ArrayList<>();
        try {
            Enumeration<String> enumeration = keyStore.aliases();
            while (enumeration.hasMoreElements()) {
                aliases.add(enumeration.nextElement());
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return aliases;
    }

    public KeyPairContainer getKeyPair(PublicKey publicKey) {
        List<String> keys = keyAliases();
        for (String key : keys) {
            KeyPairContainer container = getKeyPair(key);
            if (container != null && container.getPublicKey() != null) {
                if (Arrays.equals(container.getPublicKey().getEncoded(), publicKey.getEncoded())) {
                    return container;
                }
            }
        }
        return null;
    }

    public KeyPairContainer getKeyPair(String aliase) {
        KeyPairContainer container = new KeyPairContainer();
        try {
            Certificate certificate = keyStore.getCertificate(aliase);
            PublicKey publicKey = certificate.getPublicKey();
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(aliase, keyPwd.toCharArray());
            String type = certificate.getType();
            container.setPrivateKey(privateKey);
            container.setPublicKey(publicKey);
            container.setType(type);
            if (type.equals("RSA")) {
                KeyFactory keyFact = KeyFactory.getInstance(type);
                RSAPublicKeySpec keySpec = keyFact.getKeySpec(publicKey, RSAPublicKeySpec.class);
                container.setKeySize(keySpec.getModulus().intValue());
            } else if (type.equals("SM2")) {
                container.setKeySize(256);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return container;
    }
}
