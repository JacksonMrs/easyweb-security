package com.egao.common.system.controller;

import com.egao.common.core.web.BaseController;
import com.egao.common.core.web.JsonResult;
import com.egao.common.system.entity.Menu;
import com.egao.common.system.service.MenuService;
import com.egao.common.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by wangfan on 2018-12-24 16:10
 */
@Api(tags = "登录认证")
@RestController
public class MainController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/api/login")
    public void login(String username, String password) {
        // 登录操作由JwtLoginFilter完成
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping("/api/main/user")
    public JsonResult userInfo() {
        return JsonResult.ok().setData(userService.getFullById(getLoginUserId()));
    }

    @ApiOperation("获取登录用户菜单")
    @GetMapping("/api/main/menu")
    public JsonResult userMenu() {
        List<Menu> userMenu = menuService.getUserMenu(getLoginUserId(), Menu.TYPE_MENU);
        return JsonResult.ok().setData(menuService.toMenuTree(userMenu, 0));
    }

}
