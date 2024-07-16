package fxDeals.processor.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileProcessorService {

     void processFiles(MultipartFile inputFile);
}
