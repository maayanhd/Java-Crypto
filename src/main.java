import java.io.*;
import java.nio.file.NoSuchFileException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        startDecryptAndSignSystem();
    }

    public static void startDecryptAndSignSystem(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a keystore password:\n");
        char[] keyStorePass = sc.nextLine().toCharArray();
        String ksFileName = "maayanKS.JCEKS";
        KeyStore ksInstance = null;
        try {
            ksInstance = loadKeyStore(keyStorePass, ksFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String certAlias = "tomTrusted";
        PublicKey publicKey = null;
        try {
                publicKey = getPublicKeyByCertificate(ksInstance.getCertificate(certAlias));
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        System.out.println(publicKey);
    }

    public static PublicKey getPublicKeyByCertificate(Certificate trustedCert){
        return trustedCert.getPublicKey();
    }
    public static KeyStore loadKeyStore(char[] password, String fileNamesStr) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileNamesStr);
        File ksFile = new File(fileNamesStr);
        KeyStore keysStore= null;

        try {
            keysStore = KeyStore.getInstance("JCEKS");
            if(ksFile.exists())
                keysStore.load(new FileInputStream(ksFile), password);
            else{
                throw new NoSuchFileException(fileNamesStr);
            }
        } catch (KeyStoreException | NoSuchFileException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keysStore;
    }
}