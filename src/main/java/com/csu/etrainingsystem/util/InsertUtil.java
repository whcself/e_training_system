package com.csu.etrainingsystem.util;

import com.csu.etrainingsystem.experiment.repository.ExperimentRepository;
import com.csu.etrainingsystem.procedure.repository.ProcedureRepository;
import com.csu.etrainingsystem.score.repository.ScoreRepository;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.transaction.Transactional;

@Component
public class InsertUtil {

    private static ScoreRepository scoreRepository;
    private StudentRepository studentRepository;
    private ProcedureRepository procedureRepository;
    private ExperimentRepository experimentRepository;
    private static JdbcTemplate jdbcTemplate;


    @Autowired
    public InsertUtil(ScoreRepository scoreRepository, StudentRepository studentRepository, ProcedureRepository procedureRepository, ExperimentRepository experimentRepository) {
        this.scoreRepository = scoreRepository;
        this.studentRepository = studentRepository;
        this.procedureRepository = procedureRepository;
        this.experimentRepository = experimentRepository;
    }

    /**
     * insert score items including 1,2,5 credit ,
     * need to make sure you have the sid, suggest call it with insertStudent (the same parameters)
     * @param begin the begin of the no of sid
     * @param end the end
     *
     */
    private static void insertScore(int begin,int end) {
        for (int i = begin; i < end; i++) {
            String[] sqls = {
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('1" + i + "', '铸造', '89');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('1" + i + "', '数控线切割', '88');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('1" + i + "', '焊接', '89');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('1" + i + "', '逆向工程快速原型', '77');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('1" + i + "', '锻压', '77');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('1" + i + "', '铣削', '89');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('1" + i + "', '磨削', '89');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('1" + i + "', '车削', '48');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('1" + i + "', '钳工', '48');",
                    //2
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '铸造', '89');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '数控线切割', '88');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', 'Cimatron', '88');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '激光切割', '88');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '加工中心', '88');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '焊接', '89');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '逆向工程快速原型', '77');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '热处理', '77');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '车削', '48');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '锻造', '90');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '铣削', '48');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '磨削', '48');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('2" + i + "', '钳工', '89');",
                    // 5
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '铸造', '89');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '数控线切割', '88');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', 'Cimatron', '88');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '激光切割', '88');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '加工中心', '88');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '焊接', '89');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '激光焊接', '89');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '逆向工程快速原型', '77');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '热处理', '77');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '车削', '48');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '锻造', '90');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '铣削', '48');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '磨削', '48');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '钳工', '89');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '数控车', '89');",
                    "INSERT INTO `etc2`.`score` (`sid`, `pro_name`, `pro_score`) VALUES ('3" + i + "', '数控车仿真', '89');",

            };

            for (String sql : sqls) {
                jdbcTemplate.execute(sql);
            }
        }
    }

    /**
     *
     * @param begin 开始的编号尾数
     * @param end 结束的编号尾数
     */
    private static void insertStudent(int begin, int end) {
        System.out.println(begin+ " "+end);
        for (int i = begin; i < end; i++) {
            String[] sqls = {
                    "INSERT INTO `etc2`.`student` (`sid`, `batch_name`, `sname`, `clazz`) VALUES ('1"+i+"', '2018S101', '张一" + i + "', '自动化1601');",
                    "INSERT INTO `etc2`.`student` (`sid`, `batch_name`, `sname`, `clazz`) VALUES ('2"+i+"', '2018S201', '张二" + i + "', '自动化1601');",
                    "INSERT INTO `etc2`.`student` (`sid`, `batch_name`, `sname`, `clazz`) VALUES ('3"+i+"', '2018S501', '张五" + i + "', '自动化1601');",

            };

            for (String sql : sqls) {
                jdbcTemplate.execute(sql);
            }
        }
    }

    public static void main(String[] args) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://134.175.152.210:3306/etc2?characterEncoding=utf-8&useSSL=false");
        dataSource.setUsername("scjn");
        dataSource.setPassword("123456");
        jdbcTemplate = new JdbcTemplate(dataSource);
//        insertStudent(1,40);

        insertScore(1,40);

    }


}