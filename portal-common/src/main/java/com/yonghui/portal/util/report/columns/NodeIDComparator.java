package com.yonghui.portal.util.report.columns;

import com.yonghui.portal.model.report.ReportMeasureRelation;

import java.util.Comparator;

/**
 * 报表标题-节点比较器
 * Created by zhanghai on 2017/5/24.
 */
public class NodeIDComparator implements Comparator<Object> {
    // 按照节点排序编号比较
    public int compare(Object o1, Object o2) {
        int j1 = ((ReportMeasureRelation) o1).getSortid();
        int j2 = ((ReportMeasureRelation) o2).getSortid();
        return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
    }
}
