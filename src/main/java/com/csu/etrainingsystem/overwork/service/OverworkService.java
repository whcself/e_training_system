package com.csu.etrainingsystem.overwork.service;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.overwork.entity.Overwork;
import com.csu.etrainingsystem.overwork.repository.OverworkRepository;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.repository.TeacherRepository;
import com.csu.etrainingsystem.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class OverworkService {
    private final OverworkRepository overworkRepository;
    private final TeacherRepository teacherRepository;
    @Autowired
    public OverworkService(OverworkRepository overworkRepository,TeacherRepository teacherRepository) {
        this.overworkRepository = overworkRepository;
        this.teacherRepository=teacherRepository;
    }

    @Transactional
    public void addOverwork(Overwork overwork) {
        this.overworkRepository.save(overwork);
    }
    @Transactional
    public Overwork getOverwork(int id) {
        Optional<Overwork> overwork=overworkRepository.findOverworkByOverwork_id(id);
        return overwork.get();
    }
    @Transactional
    public Iterable<Overwork> getAllOverwork() {
        return this.overworkRepository.findAllOverwork();
    }

    @Transactional
    public void  updateOverwork(Integer overworkId,
                                String begin,
                                String end,
                                String pro_name,
                                String reason,
                                String tname) {
        Timestamp beginTime=Timestamp.valueOf(begin);
        Timestamp endTime=Timestamp.valueOf(end);
        Overwork overwork=new Overwork();
        overwork.setOverwork_time_end(endTime);
        overwork.setOverwork_time(beginTime);
        overwork.setTname(tname);
        overwork.setOverwork_id(overworkId);
        overwork.setPro_name(pro_name);
        overwork.setReason(reason);
        overwork.setDel_status(false);

        overworkRepository.saveAndFlush(overwork);
    }
    @Transactional
    public CommonResponseForm deleteOverwork(Integer id) {
        Overwork overwork=getOverwork(id);
        if (overwork!=null){
            overwork.setDel_status(true);
            overworkRepository.save(overwork);
            return CommonResponseForm.of204("删除成功");
        }else{
            return CommonResponseForm.of400("未找到该加班记录");
        }

       /*
       todo:消除删除一个加班记录所带来的影响,似乎没有影响(常常会删除)
       即:似乎无影响
        */
    }

    /**
     * 功能：新增教师值班
     * @param beginTime 开始时间
     * @param proName   工序名
     * @param timeLen   时长
     */

    public boolean addTeacherOverwork(String beginTime,String proName,String timeLen,String tName){

        Teacher teacher=teacherRepository.findTeacherByTName(tName);
        if(teacher==null)return false;
        Timestamp begin=Timestamp.valueOf(beginTime);
        Timestamp end=TimeUtil.getEndTime(beginTime,timeLen);
        Overwork overwork=new Overwork();
        overwork.setOverwork_time(begin);
        overwork.setOverwork_time_end(end);
        overwork.setPro_name(proName);
        overwork.setTname(tName);
        overworkRepository.save(overwork);
        return true;
    }



    /**
     * 功能：教师值班记录
     * @param begin 开始时间
     * @param end 结束时间
     * @param proName 工序名
     * @return list
     */
    public List<Overwork> getOverworkByTimeOrProName(String begin,String end,String proName){
        if(begin==null)begin="1999-1-1";
        if(end==null)end="2999-1-1";
        if(proName==null)proName="%";
        System.out.println("*****");
        return overworkRepository.findOverworkByTimeOrProName(begin,end,proName);
    }



//    public static void main(String[] args){
//        List<Overwork> overworks=overworkService.getOverworkByTimeOrProName("2018-10-10","2018-12-12",null);
//        System.out.println(overworks.size());
//        for(Overwork overwork:overworks){
//            System.out.println(overwork.getOverwork_time()+" "+overwork.getOverwork_time_end());
//        }
//    }

}
