package com.osiconnect.controller;

import com.osiconnect.model.AttachmentModel;
import com.osiconnect.model.EmployeeRequestModel;
import com.osiconnect.repository.EmployeeRequestRepository;
import com.osiconnect.serviceimpl.AttachmentServiceImpl;
import com.osiconnect.serviceimpl.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.osiconnect.constants.ApplicationConstants.CONTENT_DISPOSITION;
import static com.osiconnect.constants.ApplicationConstants.CONTENT_TYPE;

@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    private StorageServiceImpl service;


    @Autowired
    private AttachmentServiceImpl attachmentService;

    @Autowired
    private EmployeeRequestRepository employeeRequestRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file,@RequestParam(value = "requestId") Long requestId) {
        return new ResponseEntity<>(service.uploadFile(file,requestId), HttpStatus.OK);
    }


    @GetMapping ("/getattachments")
    public ResponseEntity<List<AttachmentModel>> getAttachments(@RequestParam(value = "requestId") Long id) {
        Optional<EmployeeRequestModel> employeeRequestModel = employeeRequestRepository.findById(id);
        return new ResponseEntity<>(attachmentService.getAttachments(employeeRequestModel), HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String key) {
        byte[] data = service.downloadFile(key);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
            .ok()
            .contentLength(data.length)
            .header(CONTENT_TYPE, "application/octet-stream")
            .header(CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
            .body(resource);
    }



    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
    }


}