package com.tristeza.controller.frontend;

import com.tristeza.model.dto.MainPageInfoDTO;
import com.tristeza.model.dto.Result;
import com.tristeza.service.combine.HeadLineShopCategoryCombineService;
import com.tristeza.springframework.core.annotation.Controller;
import com.tristeza.springframework.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainPageController {
    @Autowired(value = "HeadLineShopCategoryCombineServiceImpl2")
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp) {
        return headLineShopCategoryCombineService.getMainPageInfo();
    }

    public void throwException() {
        throw new RuntimeException("抛出异常测试");
    }
}
