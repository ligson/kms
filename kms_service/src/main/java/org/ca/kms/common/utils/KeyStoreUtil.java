package org.ca.kms.common.utils;

import org.apache.commons.codec.binary.Hex;
import org.ca.ext.security.util.CertUtil;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

public class KeyStoreUtil {
    public static KeyStore keyStore = null;
    public static String password = "changeme";
    public static String jkspwd = "changeme";
    public static String jksPath = System.getProperty("user.home")
            + "/.topca/keystore";

    public KeyStoreUtil() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        keyStore = KeyStore.getInstance("JKS");
        File filePath = new File(jksPath);
        if (!filePath.isDirectory()) {
            filePath.mkdir();
        }
        File file = new File(jksPath);
        if (!file.exists()) {
            file.createNewFile();
            keyStore.load(null, jkspwd.toCharArray());
        } else {
            if (file.length() == 0) {
                keyStore.load(null, jkspwd.toCharArray());
            } else {
                FileInputStream is = new FileInputStream(file);
                keyStore.load(is, jkspwd.toCharArray());
            }
        }
    }

    public void genKey(String algorithm, int keysize,
                       int count) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        File file = new File(jksPath);
        Certificate[] chain;
        for (int i = 0; i < count; i++) {
            KeyPair keyPair = genKeyPair(algorithm, keysize);
            Certificate certificate;
            certificate = CertUtil.wrapToCertContainer(keyPair.getPublic());
            chain = new Certificate[]{certificate};
            keyStore.setKeyEntry(sha1(keyPair.getPublic().getEncoded()),
                    keyPair.getPrivate(), password.toCharArray(), chain);
        }
        FileOutputStream out = new FileOutputStream(file);
        keyStore.store(out, jkspwd.toCharArray());

    }

    public static KeyPair genKeyPair(String algorithm, int keysize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator;
        keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keysize);
        return keyPairGenerator.generateKeyPair();
    }

    public List<String> aliases() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
        List<String> aliasList = new ArrayList<String>();
        Enumeration<String> en = keyStore.aliases();
        while (en.hasMoreElements()) {
            aliasList.add(en.nextElement());
        }
        return aliasList;
    }


    public Map showKey(String alias) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException {
        Map keyInfo = new HashMap();
        Key privateKey = keyStore.getKey(alias, password.toCharArray());
        keyInfo.put("privateKey", privateKey);
        PublicKey publicKey = keyStore.getCertificate(alias).getPublicKey();
        keyInfo.put("publicKey", publicKey);
        return keyInfo;
    }

    public void delkey(String alias) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
        File file = new File(jksPath);
        keyStore.deleteEntry(alias);
        FileOutputStream os = new FileOutputStream(file);
        keyStore.store(os, jkspwd.toCharArray());
    }

    /*   public void rename(String alias) {
        try {
            keyStore.containsAlias(alias);

        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }*/

    public void saveKey(File certFile, File keyFile, String pwd) {
        //TODO
    }

    public void saveKey(InputStream is, String pwd) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, UnrecoverableKeyException {
        KeyStore ks;
        try {
            ks = KeyStore.getInstance("JKS");
            ks.load(is, pwd.toCharArray());
        } catch (Exception e) {
            try {
                ks = KeyStore.getInstance("PKCS12");
                ks.load(is, pwd.toCharArray());
            } catch (Exception ksEx) {
                if (e instanceof IOException) {
                    throw (IOException) ksEx;
                }
                IOException ex = new IOException(ksEx.getMessage());
                ex.initCause(ksEx);
                throw ex;
            }
        }
        Enumeration<String> en = ks.aliases();
        String alias;
        while (en.hasMoreElements()) {
            alias = en.nextElement();
            Certificate cert = ks.getCertificate(alias);
            Certificate[] chain = ks.getCertificateChain(alias);
            Key key = ks.getKey(alias, pwd.toCharArray());
            try {
                keyStore.setKeyEntry(alias, key, password.toCharArray(), chain);
            } catch (Exception ksEx) {
                alias = sha1(cert.getPublicKey().getEncoded());
                IOException ex = new IOException(ksEx.getMessage());
                ex.initCause(ksEx);
                throw ex;
            }
        }
        File file = new File(jksPath);
        FileOutputStream out = new FileOutputStream(file);
        keyStore.store(out, jkspwd.toCharArray());
    }

    private String sha1(byte[] src) throws NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA1");
        byte[] publicKeyHash = md.digest(src);
        return new String(Hex.encodeHex(publicKeyHash));
    }

    public boolean isKeyEntry(String alias) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
        boolean flag;
        flag = keyStore.isKeyEntry(alias);
        return flag;
    }

    public Map getEntry(String alias) throws IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException {
        Map<String, Object> map = new HashMap<String, Object>();
        Key privateKey = keyStore.getKey(alias, password.toCharArray());
        map.put("privateKey", privateKey);
        PublicKey publicKey = keyStore.getCertificate(alias).getPublicKey();
        map.put("publicKey", publicKey);
        String keyType;
        int keySize;
        if (publicKey.getAlgorithm().contains("RSA")) {//RSA SM2
            keyType = "RSA";
            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            keySize = rsaPublicKey.getModulus().bitLength();
        } else {
            keyType = "SM2";
            keySize = 256;
        }
        map.put("alias", alias);
        map.put("keyType", keyType);
        map.put("keySize", keySize);
        return map;
    }

    public String getCertificateAlias(Certificate cert) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
        String certAlias = null;
        PublicKey certPublicKey = cert.getPublicKey();
        Enumeration<String> en = keyStore.aliases();
        while (en.hasMoreElements()) {
            String alias = en.nextElement();
            PublicKey publicKey = keyStore.getCertificate(alias).getPublicKey();
            if (certPublicKey.equals(publicKey)) {
                certAlias = alias;
                break;
            }
        }
        return certAlias;
    }

    public boolean hasPrivateKey(String alias) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException {
        boolean flag = false;
        Key privateKey = keyStore.getKey(alias, password.toCharArray());
        if (privateKey != null) {
            flag = true;
        }
        return flag;
    }
}
