package smtest;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.ca.common.utils.X500NameUtils;
import org.ca.ext.security.util.CertUtil;
import org.ca.ext.security.x509.AlgorithmId;
import org.ca.kms.common.main.Startup;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.Enumeration;

/**
 * Created by ligson on 2016/5/26.
 */
public class CSRTest {
    public static void main(String[] args) throws Exception {
        System.out.println(Startup.topSMProvider);
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("./kms_service/src/main/resources/keystore/key.jks"), "password".toCharArray());
        /*Enumeration<String> e = keyStore.aliases();
        while (e.hasMoreElements()) {
            String item = e.nextElement();
            Certificate cert = keyStore.getCertificate(item);
            System.out.println(cert.getPublicKey().getAlgorithm());
            if (cert.getPublicKey().getAlgorithm().equals("1.2.156.10197.1.301")) {
                keyStore.deleteEntry(item);
            }

        }
        keyStore.store(new FileOutputStream("./kms_service/src/main/resources/keystore/key.jks"), "password".toCharArray());
        System.out.println("----------------------delete");*/
        PublicKey publicKey = keyStore.getCertificate("2016052610335531299").getPublicKey();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("2016052610335531299", "password".toCharArray());
        System.out.println(privateKey);
        System.out.println(publicKey);
        System.out.println(privateKey.getAlgorithm());
        System.out.println(publicKey.getAlgorithm());
        System.out.println("-------------2016052610335531299-----------------------");

        /*KeyPairGenerator generator = KeyPairGenerator.getInstance("SM2", Startup.topSMProvider);
        KeyPair smKeyPair = generator.generateKeyPair();
        Certificate cert = CertUtil.wrapToCertContainer(smKeyPair.getPublic());
        System.out.println(cert.getPublicKey().getAlgorithm());
        System.out.println(generator.generateKeyPair().getPublic().getAlgorithm());
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);
        ks.setKeyEntry("1", smKeyPair.getPrivate(), "password".toCharArray(), new java.security.cert.Certificate[]{cert});
        System.out.println("---");
        System.out.println(ks.getCertificate("1").getPublicKey().getAlgorithm());
*/
        byte[] plain = "heeeeee".getBytes();
        Signature signature = Signature.getInstance("SM3withSM2", Startup.topSMProvider);
        System.out.println(AlgorithmId.SM3withSM2_oid.toString());
        signature.initSign(privateKey);
        signature.update(plain);
        byte[] signBuffer = signature.sign();
        signature.initVerify(publicKey);
        signature.update(plain);
        boolean ok = signature.verify(signBuffer);
        System.out.println(ok);
        X500Name subject = X500NameUtils.subjectToX500Name("O=org,OU=unit,CN=name");
        X500Principal principal = new X500Principal(subject.getEncoded());
        //PKCS10CertificationRequest.BER
        MyPKCS10CertificationRequest pkcs10CertificationRequest = new MyPKCS10CertificationRequest("SM3withSM2", principal, publicKey, null, privateKey, Startup.topSMProvider.getName());
        //System.out.println(pkcs10CertificationRequest.getEncoded());
        SubjectPublicKeyInfo publicKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(AlgorithmId.get(publicKey.getAlgorithm()).getOID().toString()), publicKey.getEncoded());
        System.out.println(publicKeyInfo);
        //PKCS10CertificationRequest.getInstance()

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PKCS10CertificationRequestBuilder builder = new PKCS10CertificationRequestBuilder(subject, publicKeyInfo);
        ContentSigner contentSigner = new ContentSigner() {
            @Override
            public AlgorithmIdentifier getAlgorithmIdentifier() {
                try {
                    return new AlgorithmIdentifier(AlgorithmId.get(publicKey.getAlgorithm()).getOID().toString());
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public OutputStream getOutputStream() {
                return bos;
            }

            @Override
            public byte[] getSignature() {
                try {
                    signature.initSign(privateKey);
                    signature.update(bos.toByteArray());
                    return signature.sign();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new byte[0];
            }
        };
        System.out.println( builder.build(contentSigner).getEncoded().length);

    }
}
