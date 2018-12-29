package lk.project.marketing.utils;

import com.google.gson.Gson;
import lk.project.marketing.base.bo.QRCodeBo;
import lk.project.marketing.base.utils.MatrixToImage;
import org.junit.Before;
import org.junit.Test;


public class MatrixToImageTest {

    private QRCodeBo qrCodeBo;

    private String qrCodeText;

    private static final String TEST_QR_FILE = "testQR.jpg";

    private String fileURL;

    @Before
    public void setUp()throws Exception{

        fileURL = MatrixToImage.class.getResource("").getPath();
        fileURL = fileURL + TEST_QR_FILE;

        qrCodeBo = new QRCodeBo();
        qrCodeBo.setActivityId("1");
        qrCodeBo.setCouponNo("DXVDGEGBWEWRDDE");
        qrCodeBo.setUserId("20116");
        qrCodeBo.setReturnUrl("http://localhost://coupon/consume?activityId=1");
        Gson gson = new Gson();
        qrCodeText = gson.toJson(qrCodeBo);

    }

    @Test
    public void writeInfoToJpgFile() throws Exception {
        MatrixToImage.writeInfoToJpgFile(qrCodeText, fileURL);

    }
//
//    @Test
//    public void decodeQR() throws Exception {
//
//        File qrCodeFile = new File(fileURL);
//        if (qrCodeFile.exists()) {
//            String decodeResult = MatrixToImage.decodeQR(fileURL);
//            Assert.assertEquals(qrCodeText, decodeResult);
//            qrCodeFile.delete();
//        }
//    }

}