package com.yonghui.portal.util.report.columns;

import com.yonghui.portal.model.report.ReportMeasureRelation;

import java.util.*;

/**
 * 报表标题-子列表类
 * Created by zhanghai on 2017/5/24.
 */
public class Children {
    private List list = new ArrayList();
    // 标题行数(层级数)
    private int lineCount = 1;

    public int getSize() {
        return list.size();
    }

    public void addChild(ReportMeasureRelation node) {
        list.add(node);
    }

    // 拼接孩子节点的JSON字符串
    public String toString() {
        String result = "[";
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            result += ((ReportMeasureRelation) it.next()).toString();
            result += ",";
        }
        result = result.substring(0, result.length() - 1);
        result += "]";
        return result;
    }

    // 孩子节点排序,同时记录最深层次
    public int sortChildren(int treecodeLength) {
        if (treecodeLength > lineCount) {
            lineCount = treecodeLength;
        }
        // 对本层节点进行排序
        // 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
        Collections.sort(list, new NodeIDComparator());
        // 对每个节点的下一层节点进行排序
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            ((ReportMeasureRelation) it.next()).sortChildren();
        }
        return lineCount;
    }
}
