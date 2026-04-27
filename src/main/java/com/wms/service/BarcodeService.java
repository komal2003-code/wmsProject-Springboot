package com.wms.service;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@Service
public class BarcodeService {

    public String generateQR(String sku) {

        if (sku == null || sku.isEmpty()) {
            throw new RuntimeException("SKU is null or empty");
        }

        try {
            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix matrix = writer.encode(
                    sku,
                    BarcodeFormat.QR_CODE,
                    200,
                    200
            );

            File dir = new File("barcodes");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String path = "barcodes/" + sku + ".png";

            Path filePath = FileSystems.getDefault().getPath(path);

            // overwrite safe
            MatrixToImageWriter.writeToPath(matrix, "PNG", filePath);

            return path;

        } catch (Exception e) {
            throw new RuntimeException("QR generation failed for SKU: " + sku, e);
        }
    }
}