package com.first.demo.User.controller;
import com.first.demo.User.dto.UserDto;
import com.first.demo.User.entity.User;
import com.first.demo.User.service.userService;
import com.first.demo.common.constant.SysConstant;
import com.first.demo.util.AjaxRusult;
import com.first.demo.util.MD5Utils;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
/**
 * @Description:用户系统controller,包括用户登录等相关接口
 * @Company：众阳健康
 * @Author: shh
 * @Date: 2019/11/14 11:06
 * @Version 1.0
 */
@Controller
@RequestMapping("/sys")
public class LoginController {
    //注入Service
    @Resource
    private userService userService;
    //注入验证码对象
    @Resource
    private Producer producer;
    /**
     * 功能描述:
     * 〈查询并返回用户信息表数据json〉
     *
     * @param
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysUser>
     * @author : songhuanhao
     * @date : 2019/11/14 11:12
     */
    @RequestMapping("/selectsysuser")
    @ResponseBody
    public List<User> listSelectSysuser(){
        return userService.listSysuser();
    }
    /**
     * 功能描述:
     * 〈系统登录接口，请求方式post,如果value为空，写入null或""，禁止不传值>
     *
     * @param userDto 1
     * @return : AjaxRusult
     * @author : songhuanhao
     * @date : 2019/11/25 13:16
     */
    @RequestMapping("/login")
    @ResponseBody
    public AjaxRusult login(@RequestBody UserDto userDto){
        //先验证验证码，防止攻击
        //得到生成的验证码
        //shiro管理session
        Session session = SecurityUtils.getSubject().getSession();
        //HttpSession session = httpServletRequest.getSession();
        String captchaCode = (String) session.getAttribute(SysConstant.CAPTCHA_KEY);
        if (!captchaCode.equals(userDto.getCaptcha())){
            return com.first.demo.util.AjaxRusult.error("验证码错误!");
        }
        //将登录逻辑捕获异常
        try {
            //得到获取subject 注意不要导错包，导入shiro包下的,静态方法直接获取
            Subject subject = SecurityUtils.getSubject();
            //密码MD5加密
            userDto.setPassword(MD5Utils.MD5(userDto.getPassword(),userDto.getUsername() , 1024));
            //把用户名和密码封装成Token对象
            UsernamePasswordToken token=new UsernamePasswordToken(userDto.getUsername(),userDto.getPassword());
            //记住我,如果设置为记住我
            if (userDto.isRememberMe()){
                token.setRememberMe(true);
            }
            //调用subject的login方法，login方法底层又会调用自定义的realm，UserRealm
            subject.login(token);
            //判断权限
            if (subject.isPermitted("sys:role:info")){
                System.out.println("具有sys:role:info权限");
            }
            if (subject.hasRole("管理员")) {
                System.out.println("具有管理员权限");
            }
            return com.first.demo.util.AjaxRusult.ok();
        } catch (AuthenticationException e) {
            e.printStackTrace();
            //如果有错误返回错误信息
            return com.first.demo.util.AjaxRusult.error(e.getMessage());
        }
    }
    /**
     * 功能描述:
     * 〈生成验证码〉
     *
     * @param response 1
     * @return : void
     * @author : songhuanhao
     * @date : 2019/11/25 11:20
     */
    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response){
        try {
            String text = producer.createText();
            //将验证码设入session域中
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute(SysConstant.CAPTCHA_KEY, text);
            //图片验证码
            BufferedImage image = producer.createImage(text);
            //Java Image I/O API 的主要包。使用 ImageIO 类的静态方法可以执行许多常见的图像 I/O 操作。
            ImageIO.write(image, "jpg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}