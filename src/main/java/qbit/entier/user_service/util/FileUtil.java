package qbit.entier.user_service.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUtil {

    private static final String UPLOAD_DIR = "uploads"; // Thư mục lưu file

    /**
     * Lưu file vào thư mục chỉ định
     *
     * @param file MultipartFile (file tải lên)
     * @return Đường dẫn lưu file
     * @throws IOException
     */
    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.copy(file.getInputStream(), filePath);
        return filePath.toString();
    }

    /**
     * Đọc nội dung của file
     *
     * @param filePath Đường dẫn file
     * @return Nội dung file dưới dạng chuỗi
     * @throws IOException
     */
    public String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File does not exist");
        }
        return Files.readString(path);
    }

    /**
     * Xóa file
     *
     * @param filePath Đường dẫn file
     * @return `true` nếu xóa thành công, ngược lại `false`
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }

    /**
     * Kiểm tra file có tồn tại không
     *
     * @param filePath Đường dẫn file
     * @return `true` nếu tồn tại, ngược lại `false`
     */
    public boolean fileExists(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }
}
