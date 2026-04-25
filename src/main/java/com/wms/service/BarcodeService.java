package com.wms.service;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix; 
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

@Service
public class BarcodeService {

    public String generateQR(String sku) {

        try {
            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix matrix = writer.encode(sku, BarcodeFormat.QR_CODE, 200, 200);

            File dir = new File("barcodes");
            if (!dir.exists()) dir.mkdirs();

            String path = "barcodes/" + sku + ".png";

            Path filePath = FileSystems.getDefault().getPath(path);
            MatrixToImageWriter.writeToPath(matrix, "PNG", filePath);

            return path;

        } catch (Exception e) {
            throw new RuntimeException("QR failed");
        }
    }
}