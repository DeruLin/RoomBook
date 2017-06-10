package com.roombook.controller.base;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.framework.util.FileUtil;

import com.roombook.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping(value = "/fileController")
public class FileController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    private String uploadFile(@RequestParam("image") CommonsMultipartFile file){
        String picPath="";
        try {
            picPath= FileUtil.saveFile(getHttpRequest(),file,"/web/image/activityPic");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picPath;
    }


}

