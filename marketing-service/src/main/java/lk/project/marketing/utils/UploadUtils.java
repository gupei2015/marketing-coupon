package lk.project.marketing.utils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lk.project.marketing.config.StorageConfig;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.UUID;

/**
 * Created by alexlu
 * 2018/3/22.
 */
public class UploadUtils {
//    private final static String accessKey = "CDWR02D6F2Q2HNLI3MOY";
//    private final static String secretKey = "2GH7bIJye6ti34zinSEiCaK7ZB1amCiv9v8m9rgj";
//    private final static String endpoint = "10.19.248.200:30150";

    static AmazonS3 amazonS3;

    public static void setAmazonS3(StorageConfig StorageConfig) {
        AWSCredentials credentials = new BasicAWSCredentials(StorageConfig.getAccessKey(), StorageConfig.getSecretKey());
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        amazonS3 = new AmazonS3Client(credentials, clientConfig);
        amazonS3.setEndpoint(StorageConfig.getEndpointAddr());
    }

    private static AmazonS3 getAmazonS3() {
        return amazonS3;
    }

    /**
     * 下载文件
     * @param bucketName
     * @param fileName
     * @return
     */
    public static S3Object downloadFile(String bucketName, String fileName) {
        //AmazonS3 amazonS3 = getAmazonS3();
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, fileName));
        System.out.println("get S3Object :" + s3Object);
        return s3Object;
    }

    /**
     * 上传文件
     *
     * @param input
     * @param fileName
     * @return
     */
    public static String uploadFile(String baseUrl, InputStream input, String bucketName, String fileName) {
        //AmazonS3 amazonS3 = getAmazonS3();
        Bucket b = null;

        /** 检索是否已存在桶 **/
        if(!CollectionUtils.isEmpty(amazonS3.listBuckets())){
            for (Bucket bucket : amazonS3.listBuckets()) {
                if (bucket.getName().equals(bucketName)) {
                    b = bucket;
                    break;
                }
            }
        }

        /** 创建桶 **/
        if (b == null) {
            amazonS3.createBucket(bucketName);
        }

        String fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        fileName = fileName.replace(fileExt, "") + UUID.randomUUID() + fileExt;

        try {
            /** 上传文件 **/
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, input, new ObjectMetadata()));
            String downloadUri = baseUrl + bucketName + "/" + fileName;
            System.out.println("###############upload url : " + downloadUri);
            return downloadUri;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream(createFile());
        String url = uploadFile("http://localhost:8181/fileDownload/common/", inputStream, "upload-file", "abb.txt");
        System.out.println(url);
        //http://10.19.248.200:30150/laikang-app/7a103085-61a7-4b0c-8320-02bb0ee18fe6a.txt?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20180309T084022Z&X-Amz-SignedHeaders=host&X-Amz-Expires=899&X-Amz-Credential=CDWR02D6F2Q2HNLI3MOY%2F20180309%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=1563be61eace9af5b79dffe5ff4af33c5eb26da6f1fc37929e280c266b4258ad
    }

    private static File createFile() throws IOException {
        File file = File.createTempFile("test_file", ".txt");
        file.deleteOnExit();
        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("Hello, world! \n");
        writer.write(System.currentTimeMillis() + "");
        writer.close();
        return file;
    }
}
