package com.garry.biscuit.${module}.controller.admin;

import com.garry.biscuit.common.util.HostHolder;
import com.garry.biscuit.common.vo.PageVo;
import com.garry.biscuit.common.vo.ResponseVo;
import com.garry.biscuit.${module}.form.${Domain}QueryForm;
import com.garry.biscuit.${module}.form.${Domain}SaveForm;
import com.garry.biscuit.${module}.service.${Domain}Service;
import com.garry.biscuit.${module}.vo.${Domain}QueryVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Garry
 * ${DateTime}
 */
@RestController
@RequestMapping(value = "/admin/${do_main}")
public class ${Domain}AdminController {
    @Resource
    private ${Domain}Service ${domain}Service;

    @Resource
    private HostHolder hostHolder;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@Valid @RequestBody ${Domain}SaveForm form) {
        ${domain}Service.save(form);
        return ResponseVo.success();
    }

    @RequestMapping(value = "/query-list", method = RequestMethod.GET)
    public ResponseVo<PageVo<${Domain}QueryVo>> queryList(@Valid ${Domain}QueryForm form) {
        PageVo<${Domain}QueryVo> vo = ${domain}Service.queryList(form);
        return ResponseVo.success(vo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo delete(@PathVariable Long id) {
        ${domain}Service.delete(id);
        return ResponseVo.success();
    }
}
