package edu.scut.tinymail.controller;

import com.google.code.kaptcha.Producer;
import edu.scut.tinymail.domain.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Tag(name = "VerifyCodeController", description = "验证码接口")
@Controller
@RequestMapping("/api/verifyCode")
@RequiredArgsConstructor

public class VerifyCodeController {
    public static final String VERIFY_CODE_KEY = "vc";
    private final Producer producer;

    @GetMapping("/base64")
    @Operation(summary = "Base64格式")
    @ResponseBody
    public ResponseResult<String> base64(@Parameter(hidden = true) HttpSession httpSession) throws IOException {
        //生成验证码
        final BufferedImage image = createImage(httpSession);
        //响应图片
        final FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        ImageIO.write(image, "jpeg", os);
        //返回 base64
        return new ResponseResult<>(200, Base64.encodeBase64String(os.toByteArray()));
    }

    @GetMapping("/image")
    @Operation(summary = "图片格式")
    public void image(@Parameter(hidden = true) HttpServletResponse response, @Parameter(hidden = true) HttpSession httpSession) throws IOException {
        final BufferedImage image = createImage(httpSession);
        //响应图片
        response.setContentType(MimeTypeUtils.IMAGE_JPEG_VALUE);
        ImageIO.write(image, "jpeg", response.getOutputStream());
    }

    private BufferedImage createImage(HttpSession httpSession) {
        //生成验证码
        final String verifyCode = producer.createText();
        //保存到 session 中（或redis中）
        httpSession.setAttribute(VERIFY_CODE_KEY, verifyCode);
        //生成图片
        return producer.createImage(verifyCode);
    }
}
