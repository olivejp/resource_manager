package nc.deveo.resource_manager.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

@Log4j2
@Service
public class FirebaseService {

    private final String bucketName;
    private final String projectId;
    private final StorageOptions storageOptions;

    public FirebaseService(@Value("${firebase.bucket-name}") String bucketName,
                           @Value("${firebase.project-id}") String projectId) {
        this.bucketName = bucketName;
        this.projectId = projectId;
        try {
            final FileInputStream serviceAccount =
                    new FileInputStream("service-account.json");
            storageOptions = StorageOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
        } catch (IOException e) {
            throw new BeanCreationException("Impossible d'instancier le FirebaseService.", e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();
        String objectName = generateFileName(multipartFile);

        Storage storage = storageOptions.getService();

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(filePath));
        return objectName;
    }

    public byte[] downloadFile(String fileName) throws Exception {
        Storage storage = storageOptions.getService();

        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);

        byte[] content = null;
        log.info("File downloaded successfully.");

        content = IOUtils.toByteArray(inputStream);

        return new ByteArrayResource(content).getByteArray();

    }
}
