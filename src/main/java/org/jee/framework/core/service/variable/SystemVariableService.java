package org.jee.framework.core.service.variable;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 系统变量业务逻辑
 *
 * @author maurice
 */
@Service
@Transactional
public interface SystemVariableService {
    //----------------------------------- 数据字典管理 ----------------------------------------//
    /**
     * 获取数据字典
     *
     * @param id 数据字典主键 ID
     *
     * @return 数据字典 Map 实体
     */
    public Map<String, Object> getDataDictionary(Long id);
    /**
     * 获取数据字典
     *
     * @param code 字典类别代码
     *
     * @return 数据字典 Map 实体集合
     */
    @Cacheable(value="dataDictionaryCache",key="#code")
    public List<Map<String, Object>> getDataDictionaries(String code);
    /**
     * 删除数据字典
     *
     * @param ids 数据字典主键 ID
     */
    @CacheEvict(value="dataDictionaryCache",allEntries=true)
    public void deleteDataDictionaries(List<Long> ids);
    /**
     * 保存数据字典
     *
     * @param entity 数据字典 Map 实体
     */
    @CacheEvict(value="dataDictionaryCache",allEntries=true)
    public void saveDataDictionary(Map<String, Object> entity);
    /**
     * 查询数据字典
     *
     * @param filter 查询条件
     *
     * @return 数据字典 Map 实体集合
     */
    public List<Map<String, Object>> findDataDictionaries(Map<String, Object> filter);
    //----------------------------------- 字典类别管理 ----------------------------------------//
    /**
     * 获取字典类别
     *
     * @param id 字典类别主键ID
     *
     * @return 字典类别 Map 实体
     */
    public Map<String, Object> getDictionaryCategory(Long id);
    /**
     * 获取所有字典类别
     *
     * @param ignore 忽略的 ID 值
     *
     * @return 字典类别 Map 实体集合
     */
    public List<Map<String, Object>> getAllDictionaryCategories(Long... ignore);
    /**
     * 保存字典类别
     *
     * @param entity 字典类别 Map 实体
     */
    public void saveDictionaryCategory(Map<String, Object> entity);
    /**
     * 判断字典类别代码是否存在
     *
     * @param code 字典类别代码
     *
     * @return ture 表示存在，否则 false。
     */
    public boolean dictionaryCategoryCodeExists(String code);
    /**
     * 删除字典类别
     *
     * @param ids 字典类别主键 ID 集合
     */
    @CacheEvict(value="dataDictionaryCache",allEntries=true)
    public void deleteDictionaryCategories(List<Long> ids);
}
