package com.kitchen.market.common.security.encryption.aes;

import com.kitchen.market.common.file.KitZipFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

/**
 * 使用AES对文件进行加密和解密
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-07
 */
public class KitEncryptionFileByAES {

    /**
     * 把文件srcFile加密后存储为destFile
     *
     * @param srcFile    加密前的文件
     * @param goalFile   加密后的文件
     * @param keyStr  密钥
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static void encryptFile(String srcFile, String goalFile, String keyStr) throws GeneralSecurityException, IOException {
        AESAlgorithmImpl.encryptFile(srcFile, goalFile, keyStr, AESAlgorithmType.AES, AESWorkingType.CBC, AESPaddingType.PKCS5Padding);
    }

    /**
     * 把文件srcFile解密后存储为destFile
     *
     * @param srcFile    解密前的文件
     * @param goalFile   解密后的文件
     * @param keyStr  密钥
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static void decryptFile(String srcFile, String goalFile, String keyStr) throws GeneralSecurityException, IOException {
        byte[] byteKeyStr = keyStr.getBytes();
        AESAlgorithmImpl.decryptFile(srcFile, goalFile, keyStr, AESAlgorithmType.AES, AESWorkingType.CBC, AESPaddingType.PKCS5Padding);
    }

    public static byte[] encryptFile(byte[] data, String keyStr) throws GeneralSecurityException, IOException {
        return AESAlgorithmImpl.encryptFile(data, keyStr, AESAlgorithmType.AES, AESWorkingType.CBC, AESPaddingType.PKCS5Padding);
    }
    public static byte[] decryptFile(byte[] data, String keyStr) throws GeneralSecurityException, IOException {
        return AESAlgorithmImpl.decryptFile(data, keyStr, AESAlgorithmType.AES, AESWorkingType.CBC, AESPaddingType.PKCS5Padding);
    }

    /**
     * 对目录srcFile下的所有文件目录进行先压缩后加密,然后保存为goalFile
     *
     * @param srcFile  要操作的文件或文件夹
     * @param goalFile 压缩加密后存放的文件
     * @param keyStr   密钥
     */
    public static void encryptZip(String srcFile, String goalFile, String keyStr) throws Exception {
        File temp = new File(UUID.randomUUID().toString() + ".zip");
        temp.deleteOnExit();
        // 先压缩文件
        KitZipFile.zip(srcFile, temp.getAbsolutePath());
        // 对文件加密
        AESAlgorithmImpl.decryptFile(srcFile, goalFile, keyStr, AESAlgorithmType.AES, AESWorkingType.CBC, AESPaddingType.PKCS5Padding);

        temp.delete();
    }

    /**
     * 对文件srcfile进行先解密后解压缩,然后解压缩到目录goalFile下
     *
     * @param srcFile  要解密和解压缩的文件名
     * @param goalFile 解压缩后的目录
     * @param keyStr   密钥
     */
    public static void decryptUnzip(String srcFile, String goalFile, String keyStr) throws Exception {
        File temp = new File(UUID.randomUUID().toString() + ".zip");
        temp.deleteOnExit();
        // 先对文件解密
        AESAlgorithmImpl.decryptFile(srcFile, goalFile, keyStr, AESAlgorithmType.AES, AESWorkingType.CBC, AESPaddingType.PKCS5Padding);
        // 解压缩
        KitZipFile.unZip(temp.getAbsolutePath(), goalFile);
        temp.delete();
    }
}
