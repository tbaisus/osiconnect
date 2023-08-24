package com.service.osiconnect.serviceimpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.service.osiconnect.model.AttachmentModel;
import com.service.osiconnect.model.EmployeeRequestModel;
import com.service.osiconnect.repository.AttachmentRepository;
import com.service.osiconnect.repository.EmployeeRequestRepository;
import com.service.osiconnect.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private EmployeeRequestServiceImpl employeeRequestService;

    @Autowired
    private EmployeeRequestRepository employeeRequestRepository;

    public String uploadFile(MultipartFile file,Long requestId) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = file.getOriginalFilename();
        String key = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        AttachmentModel attachment = new AttachmentModel();
        attachment.setFileName(fileName);
        attachment.setDate(LocalDate.now());
        attachment.setSharedBy("sharedBy");
        attachment.setDownloadKey(key);
        Optional<EmployeeRequestModel> employeeRequestModel=employeeRequestService.findEmployeeRequestById(requestId);
        attachment.setEmployeeRequest(employeeRequestModel.get());
        attachmentRepository.save(attachment);
        s3Client.putObject(new PutObjectRequest(bucketName, key, fileObj));
        fileObj.delete();
        return "File uploaded : " + fileName;
    }


    public byte[] downloadFile(String key) {
        S3Object s3Object = s3Client.getObject(bucketName, key);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}