package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.Dictionary;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.DictionaryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.DesktopIconUI;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/4 18:19
 * @Description: 字典表Controller
 */
@Slf4j
@RestController
@RequestMapping("dict")
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    /**
     * 保存字典表
     *
     * @param dictionary
     * @return
     */
    @PostMapping
    public ApiResult save(@RequestBody Dictionary dictionary) {
        dictionaryService.save(dictionary);
        return ApiResult.ok();
    }

    /**
     * 删除字典表
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        Dictionary dictionary = dictionaryService.find(id);
        dictionary.setDataDelete(ConstantUtils.DELETE);
        dictionaryService.save(dictionary);
        return ApiResult.ok();
    }

    /**
     * 查询单个字典表
     *
     * @param id
     * @return
     */
    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        Dictionary dictionary = dictionaryService.find(id);
        return ApiResult.ok(dictionary);
    }

    /**
     * 列表查询字典表
     *
     * @param currentPage
     * @param pageSize
     * @param dictionary
     * @param orderQuery
     * @return
     */
    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          Dictionary dictionary, OrderQuery orderQuery) {
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        // 记录类型
        if (StringUtils.isNotBlank(dictionary.getKeyWord())) {
            queryParam.setFiled("keyWord").setMatchType(MatchType.equal).setValue(dictionary.getKeyWord());
            params.add(queryParam);
        }
        Page<Dictionary> result = dictionaryService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        return ApiResult.ok(result);
    }

    /**
     * 按照keyWord 查询字典表
     *
     * @param keyWord
     * @return
     */
    @GetMapping("all")
    public ApiResult all(@RequestParam String keyWord) {
        Dictionary dictionary = new Dictionary();
        dictionary.setKeyWord(keyWord);
        dictionary.setDataDelete(ConstantUtils.UN_DELETE);
        Example<Dictionary> dictionaryExample = Example.of(dictionary);
        List<Dictionary> dictionaryList = dictionaryService.findAll(dictionaryExample, Sort.by("id"));
        return ApiResult.ok(dictionaryList);
    }

}
