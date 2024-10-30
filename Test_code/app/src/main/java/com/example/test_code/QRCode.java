package com.example.test_code;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class QRCode {
    private String qrData;

    public QRCode(String qrData) {
        this.qrData = qrData;
    }

    // Method to hash the QR code data using SHA-256
    public void hashData() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(qrData.getBytes());
            this.qrData = bytesToHex(encodedHash);  // Update qrData with hashed value
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Hashing algorithm not found.");
        }
    }

    // Helper method to convert byte array to hex string
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Method to remove QR code data
    public void removeData() {
        this.qrData = null;  // Clear QR code data
    }

    public String getQrData() {
        return qrData;
    }
}
