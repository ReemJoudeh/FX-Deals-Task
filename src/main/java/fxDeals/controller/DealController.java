package fxDeals.controller;

import fxDeals.Exceptions.DuplicateDealFileException;
import fxDeals.Exceptions.EmptyTransactionFileException;
import fxDeals.Exceptions.TransactionsFileException;
import fxDeals.processor.service.FileProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/deals")
public class DealController {

    private static final Logger logger = Logger.getLogger(DealController.class.getName());


    @Autowired
    private FileProcessorService fileProcessorService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }

        try {

            fileProcessorService.processFiles(file); //all file is accepted
            logger.info("File uploaded and deals saved successfully");
            return new ResponseEntity<>("File uploaded and deals saved successfully", HttpStatus.CREATED);

            //the file is empty or all the records are duplicated, then skip them
        } catch (EmptyTransactionFileException e) {

            logger.severe("There's no deals to insert., the file is empty");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

            //there's a violations, or a mix of success deals with rejected deals, or duplicate file request
        } catch (TransactionsFileException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.ACCEPTED);

            //there's a duplicate request with the same file
        } catch (DuplicateDealFileException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload and parse file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
