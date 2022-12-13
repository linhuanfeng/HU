package com.hu.health.consult.controller;

//import com.hu.health.sport.controller.R;
import com.hu.health.common.utils.R;
import com.hu.health.consult.service.impl.OssFileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/consult/upload")
@Slf4j
public class UploadController {

    @Autowired
    OssFileServiceImpl ossFileService;

    @PostMapping("all")
    public R uploadAll(@RequestParam("all") MultipartFile file,
                       @RequestParam("openId") String openId,
                       @RequestParam("fileName") String fileName) {
        log.info("file:"+file+" openId:"+openId+" fileName"+fileName);
        String returnFileUrl = ossFileService.upload(file,fileName);
        return R.ok().put("url",returnFileUrl);
    }

}
