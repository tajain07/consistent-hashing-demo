package common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FNVHash {


    //Using FNV1_32_HASH algorithm to calculate the Hash value of the server, there is no need to rewrite hashCode method, the final effect is no difference.
    public static int To32BitFnv1aHashInt(String toHash) throws NoSuchAlgorithmException {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < toHash.length(); i++)
            hash = (hash ^ toHash.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // If the calculated value is negative, take its absolute value.
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }


    public static String To32BitFnv1aHash(String toHash) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(toHash.getBytes());

        BigInteger no = new BigInteger(1, messageDigest);
        String hashText = no.toString(10);
        // while (hashText.length() < 32) {
        //     hashText = "0" + hashText;

        //}
        return hashText;

    }
}