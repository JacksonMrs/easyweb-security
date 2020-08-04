package com.egao.common.system.controller;

import com.egao.common.core.annotation.OperLog;
import com.egao.common.core.web.JsonResult;
import com.egao.common.core.utils.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangfan on 2018-12-24 16:10
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @OperLog(value = "文件管理", desc = "上传文件", param = false, result = true)
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public JsonResult upload(@RequestParam MultipartFile file, HttpServletRequest request) {
        return FileUploadUtil.upload(file, request);
    }

    @OperLog(value = "文件管理", desc = "上传base64文件", param = false, result = true)
    @ApiOperation("上传base64文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "base64", value = "base64", required = true, dataType = "string")
    })
    @PostMapping("/upload/base64")
    public JsonResult uploadBase64(String base64, HttpServletRequest request) {
        return FileUploadUtil.upload(base64, request);
    }

    @ApiOperation("预览文件")
    @GetMapping("/{dir}/{name:.+}")
    public void file(@PathVariable("dir") String dir, @PathVariable("name") String name, HttpServletResponse response) {
        FileUploadUtil.preview(dir + "/" + name, response);
    }

    @ApiOperation("下载文件")
    @GetMapping("/download/{dir}/{name:.+}")
    public void downloadFile(@PathVariable("dir") String dir, @PathVariable("name") String name, HttpServletResponse response) {
        FileUploadUtil.download(dir + "/" + name, response);
    }

    @ApiOperation("查看缩略图")
    @GetMapping("/thumbnail/{dir}/{name:.+}")
    public void smFile(@PathVariable("dir") String dir, @PathVariable("name") String name, HttpServletResponse response) {
        FileUploadUtil.thumbnail(dir + "/" + name, response);
    }

    @PreAuthorize("hasAuthority('sys:file:view')")
    @OperLog(value = "文件管理", desc = "删除文件", result = true)
    @ApiOperation("删除文件")
    @DeleteMapping("/remove")
    public JsonResult del(String path) {
        if (path == null || path.trim().isEmpty()) {
            return JsonResult.error("参数不能为空");
        }
        if (FileUploadUtil.delete(path)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    @PreAuthorize("hasAuthority('sys:file:view')")
    @OperLog(value = "文件管理", desc = "查询全部")
    @ApiOperation("查询全部文件")
    @GetMapping("/list")
    public JsonResult list(String dir) {
        List<Map<String, Object>> list = FileUploadUtil.list(dir);
        list.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return ((Long) o2.get("updateTime")).compareTo((Long) o1.get("updateTime"));
            }
        });
        return JsonResult.ok().setData(list);
    }

}
