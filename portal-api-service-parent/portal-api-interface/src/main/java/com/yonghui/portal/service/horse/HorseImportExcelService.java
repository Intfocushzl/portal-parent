package com.yonghui.portal.service.horse;

import com.yonghui.portal.model.horse.HorseImportAreaMans;
import com.yonghui.portal.model.horse.HorseImportCash;
import com.yonghui.portal.model.horse.HorseOperateScore;

import java.util.List;
import java.util.Map;


public interface HorseImportExcelService {

    // 查询该用户上月收银数据(用于导入数据前判断上月数据状态)
    public List<HorseImportCash> getlatelycashlist(String user, String areamans, String time);

    // 查询该用户上月运营得分数据(用于导入数据前判断上月数据状态)
    public List<HorseOperateScore> getlatelyscorelist(String user, String areamans, String time);

    // 查询该用户上月收银未确认的数据
    public List<HorseImportCash> getlatelycashlistState(String user, String areamans, String time);

    // 查询该用户上月运营得分未确认的数据
    public List<HorseOperateScore> getlatelyscoreListState(String user, String areamans, String time);

    // 查询全部收银数据
    public List<HorseImportCash> getchashlist(String user, String areamans);

    // 查询所有运营得分数据
    public List<HorseOperateScore> getscorelist(String user, String areamans);

    // 批量导入收银数据
    public int insertcashlist(List<HorseImportCash> list);

    // 批量导入运营得分数据
    public int insertscorelist(List<HorseOperateScore> list);

    // 更新未确认状态的数据为已确认
    public int updatecashstate(String user, String areamans, String time, String updatetime);

    // 更新未确认状态的数据为已确认
    public int updatescorestate(String user, String areamans, String time, String updatetime);

    // 根据sapshopid查询小区
    public List<HorseImportAreaMans> getareamans();

    // 删除收银数据未确认数据
    public int deleteUnconfirmedCash(String user, String areamans, String time);

    // 删除营运得分未确认数据
    public int deleteUnconfirmedScore(String user, String areamans, String time);

    public Map<String, String> getshopname(String sapshopid);

    public Map<String, String> getgroupname(String groupid);

    // 查询该用户上月人才梯队得分数据(用于导入数据前判断上月数据状态)
    public List<HorseOperateScore> getlatelyscorelisthuman(String user, String areamans, String time);


    // 查询该用户上月人才梯队得分未确认的数据
    public List<HorseOperateScore> getlatelyscoreListStatehuman(String user, String areamans, String time);

    // 查询所有人才梯队得分数据
    public List<HorseOperateScore> getscorelisthuman(String user, String areamans);

    // 批量导入人才梯队得分数据
    public int insertscorelisthuman(List<HorseOperateScore> list);

    // 更新未确认状态的数据为已确认（人才梯队）
    public int updatescorestatehuman(String user, String areamans, String time, String updatetime);

    // 删除营运得分未确认数据（人才梯队）
    public int deleteUnconfirmedScorehuman(String user, String areamans, String time);
}
