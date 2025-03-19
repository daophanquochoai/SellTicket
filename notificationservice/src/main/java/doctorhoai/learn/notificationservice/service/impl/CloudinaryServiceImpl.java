package doctorhoai.learn.notificationservice.service.impl;

import com.cloudinary.Cloudinary;
import doctorhoai.learn.notificationservice.service.inter.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;


    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", UUID.randomUUID().toString())).get("url").toString();
    }
}
