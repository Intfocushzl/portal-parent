package com.yonghui.portal.mapper.horse;

import com.yonghui.portal.model.horse.HorseImportAreaMans;
import com.yonghui.portal.model.horse.HorseImportCash;
import com.yonghui.portal.model.horse.HorseOperateScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface HorseImportExcelMapper {

    // 查询该用户上月收银数据(用于导入数据前判断上月数据状态)
    public List<HorseImportCash> getlatelycashlist(@Param("user") String user, @Param("areamans") String areamans,
                                                   @Param("time") String time);

    // 查询该用户上月运营得分数据(用于导入数据前判断上月数据状态)
    public List<HorseOperateScore> getlatelyscorelist(@Param("user") String user, @Param("areamans") String areamans,
                                                      @Param("time") String time);

    // 查询该用户上月人才梯队得分数据(用于导入数据前判断上月数据状态)
    public List<HorseOperateScore> getlatelyscorelisthuman(@Param("user") String user,
                                                           @Param("areamans") String areamans, @Param("time") String time);

    // 查询该用户上月收银未确认的数据
    public List<HorseImportCash> getlatelycashlistState(@Param("user") String user, @Param("areamans") String areamans,
                                                        @Param("time") String time);

    // 查询该用户上月运营得分未确认的数据
    public List<HorseOperateScore> getlatelyscoreListState(@Param("user") String user,
                                                           @Param("areamans") String areamans, @Param("time") String time);

    // 查询该用户上月人才梯队得分未确认的数据
    public List<HorseOperateScore> getlatelyscoreListStatehuman(@Param("user") String user,
                                                                @Param("areamans") String areamans, @Param("time") String time);

    // 查询全部收银数据
    public List<HorseImportCash> getchashlist(@Param("user") String user, @Param("areamans") String areamans);

    // 查询所有运营得分数据
    public List<HorseOperateScore> getscorelist(@Param("user") String user, @Param("areamans") String areamans);

    // 查询所有人才梯队得分数据
    public List<HorseOperateScore> getscorelisthuman(@Param("user") String user, @Param("areamans") String areamans);

    // 批量导入收银数据
    public int insertcashlist(List<HorseImportCash> list);

    // 批量导入运营得分数据
    public int insertscorelist(List<HorseOperateScore> list);

    // 批量导入人才梯队得分数据
    public int insertscorelisthuman(List<HorseOperateScore> list);

    // 更新未确认状态的数据为已确认
    public int updatecashstate(@Param("user") String user, @Param("areamans") String areamans,
                               @Param("time") String time, @Param("updatetime") String updatetime);

    // 更新未确认状态的数据为已确认
    public int updatescorestate(@Param("user") String user, @Param("areamans") String areamans,
                                @Param("time") String time, @Param("updatetime") String updatetime);

    // 更新未确认状态的数据为已确认（人才梯队）
    public int updatescorestatehuman(@Param("user") String user, @Param("areamans") String areamans,
                                     @Param("time") String time, @Param("updatetime") String updatetime);

    // 根据sapshopid查询小区
    public List<HorseImportAreaMans> getareamans();

    // 删除收银数据未确认数据
    public int deleteUnconfirmedCash(@Param("user") String user, @Param("areamans") String areamans,
                                     @Param("time") String time);

    // 删除营运得分未确认数据
    public int deleteUnconfirmedScore(@Param("user") String user, @Param("areamans") String areamans,
                                      @Param("time") String time);

    // 删除营运得分未确认数据（人才梯队）
    public int deleteUnconfirmedScorehuman(@Param("user") String user, @Param("areamans") String areamans,
                                           @Param("time") String time);

    public Map<String, String> getshopname(@Param("sapshopid") String sapshopid);

    public Map<String, String> getgroupname(@Param("groupid") String groupid);

}
