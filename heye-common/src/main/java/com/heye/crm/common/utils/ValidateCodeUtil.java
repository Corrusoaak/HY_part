package com.heye.crm.common.utils;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author : lishuming
 */
public class ValidateCodeUtil {
    //图片的宽度
    private Integer width = 160;
    //图片的高度
    private Integer height = 40;
    //验证码字符个数
    private Integer codeCount = 4;
    //验证码干扰线的数目
    private Integer lineCount = 150;
    //验证码
    private String code = null;
    //验证码图片Buffer
    private BufferedImage bufferedImage = null;
    // 验证码范围,去掉0(数字)和O(拼音)容易混淆的
    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 默认构造函数，设置默认参数
     */
    public ValidateCodeUtil() {
        this.createCode();
    }

    /**
     * 构造函数，设置图片的宽度和高度
     *
     * @param width  图片的宽度
     * @param height 图片的高度
     */
    public ValidateCodeUtil(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        this.createCode();
    }

    /**
     * 构造函数，设置图片的宽度、高度、字符个数和干扰线的数目
     *
     * @param width     图片的宽度
     * @param height    图片的高度
     * @param codeCount 字符个数
     * @param lineCount 干扰线的数目
     */
    public ValidateCodeUtil(Integer width, Integer height, Integer codeCount, Integer lineCount) {
        super();
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.createCode();
    }

    /**
     * 创建验证码
     */
    public void createCode() {
        int red = 0;
        int green = 0;
        int blue = 0;

        //每个字符的宽度（左右各留出两个字符的宽度）
        int codeWidth = width / (codeCount + 2);
        //字体的高度
        int fontHeight = height - 2;
        //每个字符的高度
        int codeHeight = height - 4;
        //创建2D制图
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        //设置图像填充为白色
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);
        //创建字体
        Font font = new Font("宋体", Font.PLAIN, fontHeight);
        //设置字体
        graphics2D.setFont(font);
        //创建随机数生成器
        Random random = new Random();
        //设置干扰线
        for (int i = 0; i < lineCount; i++) {
            //设置随机开始和结束坐标
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = random.nextInt(width / 8) + xs;  //这里的除以8表示密度，除的数越大，干扰线的密度越小
            int ye = random.nextInt(height / 8) + ys;  //这里的除以8表示密度，除的数越大，干扰线的密度越小
            //产生随机的颜色值，让产生的干扰线的颜色都不同
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            //设置干扰线颜色值
            graphics2D.setColor(new Color(red, green, blue));
            //绘制干扰线
            graphics2D.drawLine(xs, ys, xe, ye);
        }
        //用codes记录随机产生的验证码
        StringBuffer randomCodes = new StringBuffer();
        for (int i = 0; i < codeCount; i++) {
            //在验证码范围内随机产生一个验证码
            String randomCode = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            //产生随机的颜色值
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            //设置当前验证码的颜色值
            graphics2D.setColor(new Color(red, green, blue));
            //绘制验证码
            graphics2D.drawString(randomCode, (i + 1) * codeWidth, codeHeight);
            //将当前验证码添加到codes中
            randomCodes.append(randomCode);
        }
        code = randomCodes.toString();
    }

    public String getCode() {
        return code;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void write(String path) throws IOException {
        OutputStream sos = new FileOutputStream(path);
        this.write(sos);
    }

    public void write(OutputStream sos) throws IOException {
        ImageIO.write(bufferedImage, "png", sos);
        sos.close();
    }
}
