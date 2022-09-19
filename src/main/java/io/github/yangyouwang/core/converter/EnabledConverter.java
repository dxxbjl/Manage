package io.github.yangyouwang.core.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.core.util.SpringUtils;
import io.github.yangyouwang.crud.system.entity.SysDictValue;
import io.github.yangyouwang.crud.system.service.SysDictValueService;

import java.util.List;


/**
 * EnabledConverter 状态转换器
 * @author yangyouwang
 */
public class EnabledConverter implements Converter<String> {

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
        List<SysDictValue> dictValues = sysDictValueService.getDictValues(ConfigConsts.DICT_KEY_ENABLED);
        for (SysDictValue dictValue : dictValues) {
            if(dictValue.getDictValueName().equals(cellData.getStringValue())){
                return dictValue.getDictValueKey();
            }
        }
        return null;
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        List<SysDictValue> dictValues = sysDictValueService.getDictValues(ConfigConsts.DICT_KEY_ENABLED);
        for (SysDictValue dictValue : dictValues) {
            if(dictValue.getDictValueKey().equals(s)){
                return new CellData(dictValue.getDictValueName());
            }
        }
        return null;
    }
}
