package com.yonghui.portal.service.impl.horse;

import com.yonghui.portal.mapper.horse.HorseImportExcelMapper;
import com.yonghui.portal.model.horse.HorseImportAreaMans;
import com.yonghui.portal.model.horse.HorseImportCash;
import com.yonghui.portal.model.horse.HorseOperateScore;
import com.yonghui.portal.service.horse.HorseImportExcelService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 赛马数据导入
 * Created by liuwei on 2017/5/6.
 */
public class HorseImportExcelServiceImpl implements HorseImportExcelService {

    @Resource
    private HorseImportExcelMapper horseImportExcelMapper;

    public List<HorseImportCash> getlatelycashlist(String user, String areamans, String time) {
        return horseImportExcelMapper.getlatelycashlist(user, areamans, time);
    }

    public List<HorseOperateScore> getlatelyscorelist(String user, String areamans, String time) {
        return horseImportExcelMapper.getlatelyscorelist(user, areamans, time);
    }

    public List<HorseImportCash> getlatelycashlistState(String user, String areamans, String time) {
        return horseImportExcelMapper.getlatelycashlistState(user, areamans, time);
    }

    public List<HorseOperateScore> getlatelyscoreListState(String user, String areamans, String time) {
        return horseImportExcelMapper.getlatelyscoreListState(user, areamans, time);
    }

    public List<HorseImportCash> getchashlist(String user, String areamans ,String time) {
        return horseImportExcelMapper.getchashlist(user, areamans , time);
    }

    public List<HorseOperateScore> getscorelist(String user, String areamans ,String time) {
        return horseImportExcelMapper.getscorelist(user, areamans ,time);
    }

    public int insertcashlist(List<HorseImportCash> list) {
        return horseImportExcelMapper.insertcashlist(list);
    }

    public int insertscorelist(List<HorseOperateScore> list) {
        return horseImportExcelMapper.insertscorelist(list);
    }

    public int updatecashstate(String user, String areamans, String time, String updatetime) {
        return horseImportExcelMapper.updatecashstate(user, areamans, time, updatetime);
    }

    public int updatescorestate(String user, String areamans, String time, String updatetime) {
        return horseImportExcelMapper.updatescorestate(user, areamans, time, updatetime);
    }

    public List<HorseImportAreaMans> getareamans() {
        return horseImportExcelMapper.getareamans();
    }

    public int deleteUnconfirmedCash(String user, String areamans, String time) {
        return horseImportExcelMapper.deleteUnconfirmedCash(user, areamans, time);
    }

    public int deleteUnconfirmedScore(String user, String areamans, String time) {
        return horseImportExcelMapper.deleteUnconfirmedScore(user, areamans, time);
    }

    public Map<String, String> getshopname(String sapshopid) {
        return horseImportExcelMapper.getshopname(sapshopid);
    }

    public Map<String, String> getgroupname(String groupid) {
        return horseImportExcelMapper.getgroupname(groupid);
    }

    public List<HorseOperateScore> getlatelyscorelisthuman(String user, String areamans, String time) {
        return horseImportExcelMapper.getlatelyscorelisthuman(user, areamans, time);
    }

    public List<HorseOperateScore> getlatelyscoreListStatehuman(String user, String areamans, String time) {
        return horseImportExcelMapper.getlatelyscoreListStatehuman(user, areamans, time);
    }

    public List<HorseOperateScore> getscorelisthuman(String user, String areamans ,String time) {
        return horseImportExcelMapper.getscorelisthuman(user, areamans , time);
    }

    public int insertscorelisthuman(List<HorseOperateScore> list) {
        return horseImportExcelMapper.insertscorelisthuman(list);
    }

    public int updatescorestatehuman(String user, String areamans, String time, String updatetime) {
        return horseImportExcelMapper.updatescorestatehuman(user, areamans, time, updatetime);
    }

    public int deleteUnconfirmedScorehuman(String user, String areamans, String time) {
        return horseImportExcelMapper.deleteUnconfirmedScorehuman(user, areamans, time);
    }
}
