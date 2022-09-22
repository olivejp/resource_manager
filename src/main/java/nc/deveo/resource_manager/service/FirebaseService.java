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
    private final StorageOptions storageOptions;

    public FirebaseService(@Value("${firebase.bucket-name}") String bucketName,
                           @Value("${firebase.project-id}") String projectId) {
        this.bucketName = bucketName;
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

    public String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }

    public Blob uploadFile(final String objectName, final MultipartFile multipartFile) throws IOException {
        final File file = convertMultiPartToFile(multipartFile);
        final Path filePath = file.toPath();

        final Storage storage = storageOptions.getService();

        final BlobId blobId = BlobId.of(bucketName, objectName);
        final BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        return storage.create(blobInfo, Files.readAllBytes(filePath));
    }

    public byte[] downloadFile(String fileName) throws IOException {
        final Storage storage = storageOptions.getService();

        final Blob blob = storage.get(BlobId.of(bucketName, fileName));
        final ReadChannel reader = blob.reader();
        final InputStream inputStream = Channels.newInputStream(reader);

        byte[] content;
        log.info("File downloaded successfully.");

        content = IOUtils.toByteArray(inputStream);

        return new ByteArrayResource(content).getByteArray();

    }
}
