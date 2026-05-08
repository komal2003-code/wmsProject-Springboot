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

	        // ✅ folder create
	        String dirPath = "src/main/resources/static/barcodes/";

	        File dir = new File(dirPath);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }

	        // ✅ full file path
	        String filePath = dirPath + sku + ".png";

	        Path path = FileSystems.getDefault().getPath(filePath);

	        // ✅ write image
	        MatrixToImageWriter.writeToPath(matrix, "PNG", path);

	        // ✅ return URL path (frontend use)
	        return "barcodes/" + sku + ".png";

	    } catch (Exception e) {
	        throw new RuntimeException("QR generation failed for SKU: " + sku, e);
	    }
	}
}
	
    