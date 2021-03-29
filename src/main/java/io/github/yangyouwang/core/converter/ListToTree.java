package io.github.yangyouwang.core.converter;

import java.util.List;

/**
 * ListToTree 一个函数式接口
 */
public interface ListToTree<T extends Treeable> {

    /**
     * list转化tree结构
     */
    List<T> toTree(List<T> list);
}