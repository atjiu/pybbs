package co.yiiu.pybbs.controller.admin;

import co.yiiu.pybbs.model.SensitiveWord;
import co.yiiu.pybbs.service.ISensitiveWordService;
import co.yiiu.pybbs.util.Result;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/sensitive_word")
public class SensitiveWordAdminController extends BaseAdminController {

    private Logger log = LoggerFactory.getLogger(SensitiveWordAdminController.class);

    @Autowired
    private ISensitiveWordService sensitiveWordService;

    @RequiresPermissions("sensitive_word:list")
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNo, String word, Model model) {
        model.addAttribute("page", sensitiveWordService.page(pageNo, word));
        model.addAttribute("word", word);
        return "admin/sensitive_word/list";
    }

    @RequiresPermissions("sensitive_word:add")
    @PostMapping("/add")
    @ResponseBody
    public Result add(String word) {
        SensitiveWord sensitiveWord = new SensitiveWord();
        sensitiveWord.setWord(word);
        sensitiveWordService.save(sensitiveWord);
        return success();
    }

    @RequiresPermissions("sensitive_word:edit")
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(Integer id, String word) {
        sensitiveWordService.updateWordById(id, word);
        return success();
    }

    @RequiresPermissions("sensitive_word:delete")
    @GetMapping("/delete")
    @ResponseBody
    public Result delete(Integer id) {
        sensitiveWordService.deleteById(id);
        return success();
    }

    @RequiresPermissions("sensitive_word:import")
    @PostMapping("import")
    @ResponseBody
    public Result _import(@RequestParam("file") MultipartFile file) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            HSSFSheet worksheet = workbook.getSheetAt(0);
            int i = 0;
            while (i <= worksheet.getLastRowNum()) {
                HSSFRow row = worksheet.getRow(i++);
                String word = row.getCell(0).getStringCellValue();
                SensitiveWord sensitiveWord = sensitiveWordService.selectByWord(word);
                if (sensitiveWord == null) {
                    sensitiveWord = new SensitiveWord();
                    sensitiveWord.setWord(word);
                    sensitiveWordService.save(sensitiveWord);
                }
            }
            return success();
        } catch (IOException e) {
            //      e.printStackTrace();
            log.error(e.getMessage());
            return error(e.getMessage());
        }
    }
}
