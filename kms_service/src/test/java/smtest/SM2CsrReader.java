package smtest;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.ca.ext.security.x509.AlgorithmId;
import org.ca.kms.common.main.Startup;

/**
 * Created by ligson on 2016/5/26.
 */
public class SM2CsrReader {
    public static void main(String[] args) throws Exception{
        System.out.println(Startup.topSMProvider);
        String csr = "MIHbMIGFAgEAMC4xDjAMBgNVBAoMBWxlY3hlMQ4wDAYDVQQLDAVsZWN4ZTEMMAoGA1UEAwwDc20xMFAwCgYIKoEcz1UBgi0DQgAEIM5GemB45TeoZEPq+FTB8e9gkZc4T3OfuXqJpNax9QGX08ASsOFaa2/phgcBF0L4eF/Q3D/NqpyBoAGhakIDHjAKBggqgRzPVQGCLQNFAAIgxzB2JxAepVkOBYxiK37cAbJgMPvOvuQ+MXsY7v8zy1gCICBJ4/HBY1X3iLLQroE82dXD/m0hc7+VmaCJJ3tOMclD";
        PKCS10CertificationRequest request = new PKCS10CertificationRequest(Base64.decodeBase64(csr));
        System.out.println(request.getCertificationRequestInfo().getSubject());
        System.out.println(request.getPublicKey(Startup.topSMProvider.getName()));
        AlgorithmIdentifier signAlg = new AlgorithmIdentifier(AlgorithmId.SM3withSM2_oid.toString());
        System.out.println(signAlg.getAlgorithm());

    }
}
