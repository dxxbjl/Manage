package io.github.yangyouwang.core.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.core.util.SpringUtils;
import io.github.yangyouwang.crud.system.model.result.SysDictValueDTO;
import io.github.yangyouwang.crud.system.service.SysDictValueService;

import java.util.List;


/**
 * SexConverter 性别转换器
 * @author yangyouwang
 */
public class SexConverter implements Converter<String> {

    private static SysDictValueService sysDictValueService;

    static {
        sysDictValueService = SpringUtils.getBean(SysDictValueService.class);
    }
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        List<SysDictValueDTO> dictValues = sysDictValueService.getDictValues(Constants.DICT_KEY_SEX);
        for (SysDictValueDTO sysDictValueResp : dictValues) {
            if(sysDictValueResp.getDictValueName().equals(cellData.getStringValue())){
                return sysDictValueResp.getDictValueKey();
            }
        }
        return null;
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        List<SysDictValueDTO> dictValues = sysDictValueService.getDictValues(Constants.DICT_KEY_SEX);
        for (SysDictValueDTO sysDictValueResp : dictValues) {
            if(sysDictValueResp.getDictValueKey().equals(s)){
                return new CellData(sysDictValueResp.getDictValueName());
            }
        }
        return null;
    }
}
