package com.csu.etrainingsystem.overwork.service;

import com.csu.etrainingsystem.overwork.entity.Overwork_apply;
import com.csu.etrainingsystem.overwork.repository.Overwork_applyRepository;
import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.util.TimeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class Overwork_applyService {
    private final Overwork_applyRepository overwork_applyRepository;

    @Autowired
    public Overwork_applyService(Overwork_applyRepository overwork_applyRepository) {
        this.overwork_applyRepository = overwork_applyRepository;
    }

    @Transactional
    public void addOverwork_apply(Overwork_apply overwork_apply) {
        this.overwork_applyRepository.save(overwork_apply);
    }

    @Transactional
    public Overwork_apply getOverwork_apply(int apply_id) {
        Optional<Overwork_apply> overwork_apply = overwork_applyRepository.findOverwork_applyByApply_id(apply_id);
        return overwork_apply.get();
    }

    @Transactional
    public Iterable<Overwork_apply> getAllOverwork_apply() {
        return this.overwork_applyRepository.findAllOverwork_apply();
    }

    @Transactional
    public void updateOverwork_apply(Overwork_apply Overwork_apply) {
        this.overwork_applyRepository.saveAndFlush(Overwork_apply);
    }

    @Transactional
    public void deleteOverwork_apply(int apply_id) {

        Overwork_apply overwork_apply = getOverwork_apply(apply_id);
        if (overwork_apply != null) {
            overwork_apply.setDel_status(true);
            updateOverwork_apply(overwork_apply);

        }
        /*
       todo:消除删除一个加班申请所带来的影响
       即:似乎无影响,需要有一个根据学号删除申请的记录,但是并没有多大作用,先留白
        */
    }

    /**
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 申请列表
     */
    public List<Overwork_apply> getOverworkApplyByBeginAndEndTime(String beginTime, String endTime, String proName) {
        if (beginTime == null) beginTime = "1999-1-1";
        if (endTime == null)
            endTime = "2999-1-1";
        if (proName == null) {
            proName = "%";
        }
        System.out.println(beginTime + "*" + endTime + "*" + proName);

        return overwork_applyRepository.findBetweenBeginAndEndTime(beginTime, endTime, proName);
    }

    /**
     * 学生端：新增加班申请
     *
     * @param beginTime 开始时间
     * @param proName   工序
     * @param timeLen   时长
     * @return bool
     */
    public boolean addOverworkApply(String beginTime, String proName, String timeLen,
                                    String reason, User user) {
        try {

            String sId=user.getAccount();
            Overwork_apply overworkApply = new Overwork_apply();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            overworkApply.setApply_time(now);
            overworkApply.setSid(sId);
            overworkApply.setPro_name(proName);
            Timestamp begin = Timestamp.valueOf(beginTime);
            overworkApply.setOverwork_time(begin);
            Timestamp end = TimeUtil.getEndTime(beginTime, timeLen);
            overworkApply.setOverwork_time_end(end);
            overworkApply.setReason(reason);
            overwork_applyRepository.save(overworkApply);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 学生端：我的申请
     */
    public List<Overwork_apply> getMyOverworkApply(String sId) {
        return overwork_applyRepository.findOverwork_applyBySId(sId);
    }
}
