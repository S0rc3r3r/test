package utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public final class AESEncrypt {

	private static final String ALGO = "AES";
	// Encryption key must be a 16 characters string
	private static final String KEY = "AwsJik912xqwOrty";

	private final byte[] keyValueInBytes;

	private final static AESEncrypt INSTANCE = new AESEncrypt();

	private AESEncrypt() {
		keyValueInBytes = KEY.getBytes();
	}

	public static AESEncrypt getInstance() {
		return INSTANCE;
	}

	public String encrypt(final String Data)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {

		final Key key = generateKey();
		final Cipher cipher = Cipher.getInstance(ALGO);
		cipher.init(Cipher.ENCRYPT_MODE, key);

		final byte[] encrypted = cipher.doFinal(Data.getBytes());
		final byte[] encodedPassword = Base64.encodeBase64(encrypted);

		return new String(encodedPassword);
	}

	public String decrypt(final String encodedEncryptedData)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {

		final Key key = generateKey();
		final Cipher cipher = Cipher.getInstance(ALGO);
		cipher.init(Cipher.DECRYPT_MODE, key);

		final byte[] encryptedData = Base64.decodeBase64(encodedEncryptedData.getBytes());
		final byte[] original = cipher.doFinal(encryptedData);

		return new String(original);

	}

	private Key generateKey() {
		return new SecretKeySpec(keyValueInBytes, ALGO);
	}
}
