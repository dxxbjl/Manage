package io.github.yangyouwang.core.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.core.util.SpringUtils;
import io.github.yangyouwang.crud.system.model.dto.SysDictValueDto;
import io.github.yangyouwang.crud.system.service.SysDictService;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * SexConverter 性别转换器
 * @author yangyouwang
 */
public class SexConverter implements Converter<String> {

    private static SysDictService sysDictService;

    static {
        sysDictService = SpringUtils.getBean(SysDictService.class);
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
        List<SysDictValueDto> dictValues = sysDictService.getDictValues(Constants.DICT_KEY_SEX);
        for (SysDictValueDto sysDictService : dictValues) {
            if(sysDictService.getDictValueName().equals(cellData.getStringValue())){
                return sysDictService.getDictValueKey();
            }
        }
        return null;
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        List<SysDictValueDto> dictValues = sysDictService.getDictValues(Constants.DICT_KEY_SEX);
        for (SysDictValueDto sysDictService : dictValues) {
            if(sysDictService.getDictValueKey().equals(s)){
                return new CellData(sysDictService.getDictValueName());
            }
        }
        return null;
    }
}
