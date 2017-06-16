package com.yonghui.portal.util.report.columns;

import com.yonghui.portal.model.report.ReportMeasureRelation;

import java.util.*;

/**
 * 获取报表标题-多叉树json字符串
 * Created by zhanghai on 2017/5/24.
 */
public class MultipleTree {
    /**
     * 获取多叉树json字符串
     *
     * @param dataList 读取层次数据结果集列表
     * @return 多叉树json字符串
     */
    public static String getTreeJsonString(List<ReportMeasureRelation> dataList) throws Exception {
        // 节点列表（散列表，用于临时存储节点对象）
        HashMap nodeList = new HashMap();
        // 根节点
        ReportMeasureRelation root = new ReportMeasureRelation();
        // 节点最大层级数
        int zindex = 1;
        int thisZindex = 1;
        root.setId(0L);
        root.setParentid(-1L);
        root.setTreecode("0");
        root.setLineCount(zindex);
        nodeList.put(root.getId(), root);
        // 根据结果集构造节点列表（存入散列表）
        for (ReportMeasureRelation node : dataList) {
            nodeList.put(node.getId(), node);
        }
        // 构造无序的多叉树
        Set entrySet = nodeList.entrySet();
        for (Iterator it = entrySet.iterator(); it.hasNext(); ) {
            ReportMeasureRelation node = (ReportMeasureRelation) ((Map.Entry) it.next()).getValue();
            if (node.getId() != -0L && node.getParentid() != -1L) {
                ReportMeasureRelation everyLast = ((ReportMeasureRelation) nodeList.get(node.getParentid()));
                // 添加子节点
                everyLast.addChild(node);
                // 子节点个数计数
                everyLast.setChildrenCount(everyLast.getChildrenCount() + 1);
            }
            // 设置层级数
            thisZindex = node.getTreecode().split("\\.").length;
            node.setLineCount(thisZindex);
            // 设置最大层级数
            if (thisZindex > zindex) {
                zindex = thisZindex;
                root.setLineCount(thisZindex);
            }
        }
        //System.out.println("输出无序的树形菜单的JSON字符串" + root.toString());
        // 对多叉树进行横向排序
        root.sortChildren();
        //System.out.println("输出有序的树形菜单的JSON字符串" + root.toString());
        return root.toString();
    }

}