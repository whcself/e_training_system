package com.csu.etrainingsystem.student.entity;

import com.csu.etrainingsystem.student.service.StudentService;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 特殊学生没有分组,没有批次,只有成绩
 * 在打分的时候,老师点击查询,结果为一下信息:
 * 1该批次以及分组下的所有学生,以及工种
 * 2根据传入的老师id和工种,选出specialScore表中的数据,
 * 如果是管理教师组打分,就不仅仅需要查询学生表,还需要查询特殊学生表来打分;
 */
@Data
@Entity
@Table(name = "sp_student")
public class SpecialStudent  implements Serializable {
        @Id
        @Column(length = 20)
        private String sid;
        private String  sname;
        @Column(name="clazz")
        private String clazz;
        //每个特殊学生都有一个打分模板
        private String template_name;
        private String sdept;
        private String depart;
        private float total_score;
        private boolean del_status;
        private boolean score_lock;
        private String degree;

        public SpecialStudent(String sid, String sname, String clazz, String template_name, String sdept, String depart, float total_score, boolean del_status, boolean score_lock, String degree) {
                this.sid = sid;
                this.sname = sname;
                this.clazz = clazz;
                this.template_name = template_name;
                this.sdept = sdept;
                this.depart = depart;
                this.total_score = total_score;
                this.del_status = del_status;
                this.score_lock = score_lock;
                this.degree = degree;
        }

        public SpecialStudent() {
        }
}
