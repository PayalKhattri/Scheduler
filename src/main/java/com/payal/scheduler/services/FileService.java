package com.payal.scheduler.services;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.payal.scheduler.entities.LoadFile;

import com.payal.scheduler.entities.PreferencesForm;
import com.payal.scheduler.entities.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;



    @Autowired
    private PreferencesFormService preferencesFormService;

    @Autowired
    private CustomUserDetailsService userService;

    public String addFile(MultipartFile upload) throws IOException {

        //define additional metadata
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());

        //store in database which returns the objectID
        Object fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        PreferencesForm preferencesForm=preferencesFormService.findUserByEmail(user.getEmail());
       preferencesFormService.deletePreferencesForm(preferencesForm);
       preferencesForm.setCertificate(fileID.toString());
       preferencesFormService.savePreferencesForm(preferencesForm);




        //return as a string
        return fileID.toString();
    }

    public LoadFile downloadFile(String id) throws IOException {

        //search file
        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );


        //convert uri to byteArray
        //save data to LoadFile class
        LoadFile loadFile = new LoadFile();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename( gridFSFile.getFilename() );

            loadFile.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );

            loadFile.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );

            loadFile.setFile( IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );
        }

        return loadFile;
    }

}
